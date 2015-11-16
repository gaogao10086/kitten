package com.creditease.hbase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: GaoYS
 * CreateData: 2015/7/28 18:36
 */
@Controller
@RequestMapping("/proxy")
public class HBaseProxy {
    private static final Logger logger = LoggerFactory.getLogger(HBaseProxy.class);
    private final String BLANK_CHAR = "\u0001";
    @Resource
    HBaseService hBaseService;

    @RequestMapping("/get")
    @ResponseBody
    public Map<String, Object> getByRow(@RequestParam(value = "rowKey", required = true) byte[] rowKey,
                                        @RequestParam(value = "tableName", required = true) String tableName) {
        try {
            return hBaseService.get(tableName, rowKey);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public boolean deleteByRow(@RequestParam(value = "rowKey", required = true) byte[] rowKey,
                               @RequestParam(value = "tableName", required = true) String tableName) {
        try {
            return hBaseService.delete(tableName, rowKey);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    @RequestMapping("/insert")
    @ResponseBody
    public boolean insert(@RequestParam(value = "tableName", required = true) String tableName,
                          @RequestParam(value = "rowKey", required = true) byte[] rowKey,
                          @RequestParam(value = "family", required = true) String family,
                          @RequestParam(value = "qualifiers[]", required = true) String[] qualifiers,
                          @RequestParam(value = "values[]", required = true) String[] values) {
        try {
            return hBaseService.insert(tableName, rowKey, family, qualifiers, values);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("/inserts")
    @ResponseBody
    public boolean inserts(@RequestParam(value = "entityList", required = true) List<HBaseEntity> entityList) {
        try {
            return hBaseService.inserts(entityList);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @RequestMapping("/scan")
    @ResponseBody
    public List<HashMap<String, Object>> scan(@RequestParam(value = "tableName", required = true) String tableName,
                                              @RequestParam(value = "startKey", required = true) byte[] startKey,
                                              @RequestParam(value = "endKey", required = true) byte[] endKey) {
        try {
            return hBaseService.scan(tableName, startKey, endKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("handler")
    @ResponseBody
    public Map<String, Object> handler(HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        HashMap<String, Object> paramMap;
        try {
            String params = request.getParameter("params");
            if (StringUtils.isEmpty(params)) {
                logger.error("参数为空");
                returnMap.put("result", "参数为空");
                return returnMap;
            }
            Gson gson = new GsonBuilder().serializeNulls().create();
            try {
                paramMap = (HashMap<String, Object>) gson.fromJson(params, HashMap.class);
                logger.info("参数：" + paramMap);
            } catch (Exception ex) {
                logger.error("非法JSON参数:" + ex);
                returnMap.put("result", "非法JSON参数");
                return returnMap;
            }
            String method = String.valueOf(paramMap.get("method"));
            if (!("get".equals(method) || "scan".equals(method) || "insert".equals(method) || "inserts".equals(method))) {
                logger.error("非法的method");
                returnMap.put("result", "非法的method");
                return returnMap;
            }
            //验证参数列表
            String[] checkArray = new String[]{};
            if ("get".equals(method)) {
                checkArray = new String[]{"tableName", "rowKey"};
            } else if ("scan".equals(method)) {
                checkArray = new String[]{"tableName", "startKey", "endKey"};
            } else if ("insert".equals(method)) {
                checkArray = new String[]{"tableName", "family", "key", "qualifiers", "values"};
            } else if ("inserts".equals(method)) {
                checkArray = new String[]{"entityList"};
            }
            String checkResult = validateRequired(paramMap, checkArray);
            if (StringUtils.isNotEmpty(checkResult)) {
                logger.error("参数校验未通过：" + checkResult);
                returnMap.put("result", checkResult);
                return returnMap;
            }
            if ("get".equals(method)) {
                String tableName = String.valueOf(paramMap.get("tableName"));
                byte[] key = getRowKey((ArrayList<Double>) paramMap.get("rowKey"));
                returnMap.put("result", hBaseService.get(tableName, key));
            } else if ("scan".equals(method)) {
                String tableName = String.valueOf(paramMap.get("tableName"));
                byte[] startKey = getRowKey((ArrayList<Double>) paramMap.get("startKey"));
                byte[] endKey = getRowKey((ArrayList<Double>) paramMap.get("endKey"));
                Integer pageNo = paramMap.containsKey("pageNo") ? ((Double) paramMap.get("pageNo")).intValue() : null;
                Integer pageSize = paramMap.containsKey("pageSize") ? ((Double) paramMap.get("pageSize")).intValue() : null;
                if (pageNo != null && pageSize != null) {
                    Map<String, Object> pageMap = hBaseService.scan(tableName, startKey, endKey, pageNo, pageSize);
                    returnMap.putAll(pageMap);
                } else {
                    returnMap.put("result", hBaseService.scan(tableName, startKey, endKey));
                }
            } else if ("insert".equals(method)) {
                String qualifiers = String.valueOf(paramMap.get("qualifiers"));
                String values = String.valueOf(paramMap.get("values"));
                String tableName = String.valueOf(paramMap.get("tableName"));
                String family = String.valueOf(paramMap.get("family"));
                byte[] key = getRowKey((ArrayList<Double>) paramMap.get("key"));
                Boolean result = hBaseService.insert(tableName, key, family, qualifiers.split(BLANK_CHAR), values.split(BLANK_CHAR));
                returnMap.put("result", result);
            } else if ("inserts".equals(method)) {
                List<LinkedTreeMap> StringMapList = (ArrayList<LinkedTreeMap>) paramMap.get("entityList");
                List<HBaseEntity> entityList = new ArrayList<HBaseEntity>();
                for (LinkedTreeMap stringMap : StringMapList) {
                    HBaseEntity hBaseEntity = new HBaseEntity();
                    hBaseEntity.setTableName((String) stringMap.get("tableName"));
                    hBaseEntity.setFamily((String) stringMap.get("family"));
                    hBaseEntity.setQualifiers((String) stringMap.get("qualifiers"));
                    hBaseEntity.setValues((String) stringMap.get("values"));
                    hBaseEntity.setKey(getRowKey((ArrayList<Double>) stringMap.get("key")));
                    entityList.add(hBaseEntity);
                }
                Boolean result = hBaseService.inserts(entityList);
                returnMap.put("result", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("result", e.getMessage());
        }
        return returnMap;
    }

    /**
     * 参数验证
     *
     * @param param
     * @param requiredField
     * @return
     */
    private String validateRequired(Map<String, Object> param,
                                    String... requiredField) {
        StringBuilder strRes = new StringBuilder();
        for (String str : requiredField) {
            if (StringUtils.isBlank(String.valueOf(param.get(str))) || "null".equals(String.valueOf(param.get(str)).toLowerCase())) {
                strRes.append(str).append(",");
            }
        }
        if (strRes.length() != 0) {
            return strRes.deleteCharAt(strRes.length() - 1).toString() + "不能为空！";
        }
        return null;
    }

    /**
     * 转换获取rowkey
     *
     * @param key
     * @return
     */
    private byte[] getRowKey(ArrayList<Double> key) {
        byte[] rowKey = new byte[key.size()];
        for (int i = 0; i < key.size(); i++) {
            rowKey[i] = (byte) key.get(i).intValue();
        }
        return rowKey;
    }
}
