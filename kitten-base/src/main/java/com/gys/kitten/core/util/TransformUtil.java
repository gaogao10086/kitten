/***********************************************************************
 * FileName：TransformUtil.java 
 * Date：2013-4-23   
 * Copyright 版权所有：GaoYuanSheng
 ***********************************************************************/

package com.gys.kitten.core.util;


//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//import org.dom4j.io.OutputFormat;
//import org.dom4j.io.XMLWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该类描述的是：转化工具类
 *
 * @Author: kitten
 * @version: 2013-4-23 上午11:54:19
 */
@SuppressWarnings("unchecked")
public class TransformUtil {

    /**
     * @param val
     * @return String
     * @Describe:转化为String
     * @Author: kitten
     * @CreateData: 2013-4-23 上午11:56:34
     */
    public static String toString(Object val) {
        return val == null ? null : String.valueOf(val);
    }

    /**
     * @param val
     * @return Integer
     * @Describe:转化为Integer
     * @Author: kitten
     * @CreateData: 2013-4-23 上午11:58:15
     */
    public static int toInteger(Object val) {
        return val == null ? -1 : Integer
                .parseInt((toString(val)));
    }


    /**
     * Description Map转换成XML
     *
     * @param map
     * @return XML格式：<ROW>map</ROW>
     * @author GaoYS
     * @CreateData 20120306
     */
//    public static String mapToXml(Map<String, Object> map) {
//        Document document = DocumentHelper.createDocument();
//        Element nodeElement = document.addElement("ROW");
//        for (Object o : map.keySet()) {
//            Element keyElement = nodeElement.addElement(String.valueOf(o));
//            Object obj = map.get(o);
//            keyElement.setText(String.valueOf(obj));
//        }
//        return doc2String(document);
//    }
//
//    /**
//     * Description XML转换成Map
//     *
//     * @param xml XML格式：<ROW>map</ROW>
//     * @return
//     * @author GaoYS
//     * @CreateData 20120306
//     */
//    public static HashMap<String, String> xmlToMap(String xml) {
//        HashMap<String, String> map = new HashMap<String, String>();
//        try {
//            Document document = DocumentHelper.parseText(xml);
//            Element rootElement = document.getRootElement();
//            List<Element> nodes = rootElement.elements();
//            for (Iterator<Element> it = nodes.iterator(); it.hasNext(); ) {
//                Element element = it.next();
//                map.put(element.getName(), element.getText());
//            }
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        return map;
//    }
//
//    /**
//     * Description List<Map>转换成XML
//     *
//     * @param list
//     * @return XML格式：<ROWLIST><ROW>map1</ROW><ROW>map2</ROW>...</ROWLIST>
//     * @author GaoYS
//     * @CreateData 20120306
//     */
//    public static String listToXml(List<HashMap<String, Object>> list) {
//        Document document = DocumentHelper.createDocument();
//        Element rootElement = document.addElement("ROWLIST");
//        for (HashMap<String, Object> m : list) {
//            Element nodeElement = rootElement.addElement("ROW");
//            for (Object o : m.keySet()) {
//                Element keyElement = nodeElement.addElement(String.valueOf(o));
//                keyElement.setText(String.valueOf(m.get(o)));
//            }
//        }
//        return doc2String(document);
//    }

    /**
     * @param jsonStr
     * @return Map<String, Object>
     * @Description:将JSON转化为Map<String, Object>
     * @author: GaoYuansheng
     * @CreateData: 2013-4-23 下午2:57:59
     */
    public static Map<String, Object> parseJSON2Map(String jsonStr) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 最外层解析
        JSONObject json = new JSONObject(jsonStr);
        for (Object k : json.keySet()) {
            Object v = json.get(k.toString());
            // 如果内层还是数组的话，继续解析
            if (v instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < ((JSONArray) v).length(); i++) {
                    JSONObject json2 = (JSONObject) ((JSONArray) v).get(i);
                    list.add(parseJSON2Map(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    /**
     * Description XML转换成List<Map>
     *
     * @param xml XML格式：<ROWLIST><ROW>map1</ROW><ROW>map2</ROW>...</ROWLIST>
     * @return
     * @author GaoYS
     * @CreateData 20120306
     */
//    public static List<HashMap<String, String>> xmlToList(String xml) {
//        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//        try {
//            Document document = DocumentHelper.parseText(xml);
//            Element rootElement = document.getRootElement();
//            List<Element> nodes = rootElement.elements();
//            for (Iterator<Element> it = nodes.iterator(); it.hasNext(); ) {
//                Element nodeElement = it.next();
//                HashMap<String, String> map = xmlToMap(nodeElement.asXML());
//                list.add(map);
//            }
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//
//    private static String doc2String(Document document) {
//        String s = "";
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        OutputFormat format = new OutputFormat("", true, "UTF-8");
//        try {
//            XMLWriter writer = new XMLWriter(out, format);
//            writer.write(document);
//            s = out.toString("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return s;
//    }


}
