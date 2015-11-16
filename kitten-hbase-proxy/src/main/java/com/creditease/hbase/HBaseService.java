package com.creditease.hbase;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * author: GaoYS
 * CreateData: 2015/7/28 18:36
 */
@Service
public class HBaseService {
    private static final Logger logger = LoggerFactory.getLogger(HBaseService.class);
    private static Configuration configuration;
    private static Connection connection;
    private final String SPACE_CHAR = "\u0001";
    private static Map<String, HTable> hBaseMap = new HashMap<String, HTable>();

    static {
        configuration = HBaseConfiguration.create();
        try {
            connection = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拿表
     *
     * @param tableName
     * @return
     * @throws IOException
     */
    public HTable getTable(String tableName) throws IOException {
        HTable table = hBaseMap.get(tableName);
        if (table != null) {
            return table;
        } else {
            table = (HTable) connection.getTable(TableName.valueOf(tableName));
            hBaseMap.put(tableName, table);
            return table;
        }
    }

    /**
     * 插入一个
     *
     * @param tableName
     * @param rowKey
     * @param family
     * @param qualifiers
     * @param values
     * @return
     * @throws Exception
     */
    public boolean insert(String tableName, byte[] rowKey, String family, String[] qualifiers, String[] values) throws Exception {
        if (qualifiers.length == 0 || values.length == 0) {
            throw new Exception("array length is not right!,please check");
        }
        if (qualifiers.length != values.length) {
            logger.info("qualifiers=" + qualifiers);
            logger.info("values=" + values);
            throw new Exception("two array length is not equal!,please check");
        }
        HTable table = getTable(tableName);
        Put put = new Put(rowKey);
        for (int i = 0; i < qualifiers.length; i++) {
            if (StringUtils.isNotBlank(values[i]) && !"null".equals(values[i])) {
                put.addColumn(family.getBytes(), qualifiers[i].getBytes(), values[i].getBytes());
            }
        }
        table.put(put);
        return true;
    }

    /**
     * 插入一堆
     *
     * @return
     */
    public boolean inserts(List<HBaseEntity> entityList) throws Exception {
        List<Put> hBaseCachedRows = new LinkedList<Put>();
        String tableName = "";
        for (HBaseEntity hBaseEntity : entityList) {
            String qualifiers = hBaseEntity.getQualifiers();
            String values = hBaseEntity.getValues();
            String[] qualifiersArray = qualifiers.split(SPACE_CHAR);
            String[] valuesArray = values.split(SPACE_CHAR);
            if (qualifiersArray.length == 0 || valuesArray.length == 0) {
                logger.error("array length is not right!,please check");
                continue;
            }
            if (qualifiersArray.length != valuesArray.length) {
                logger.info("qualifiers=" + qualifiers);
                logger.info("values=" + values);
                logger.error("two array length is not equal!,please check");
                continue;
            }
            Put put = new Put(hBaseEntity.getKey());
            String family = hBaseEntity.getFamily();
            for (int i = 0; i < valuesArray.length; i++) {
                if (StringUtils.isNotBlank(valuesArray[i]) && !"null".equals(valuesArray[i])) {
                    put.addColumn(family.getBytes(), qualifiersArray[i].getBytes(), valuesArray[i].getBytes());
                }
            }
            hBaseCachedRows.add(put);
            tableName = hBaseEntity.getTableName();
        }
        if (StringUtils.isNotEmpty(tableName)) {
            HTable table = getTable(tableName);
            table.put(hBaseCachedRows);
        }
        return true;
    }

    /**
     * 删除一个
     *
     * @param tableName
     * @param rowKey
     * @return
     * @throws IOException
     */
    public boolean delete(String tableName, byte[] rowKey) throws IOException {
        HTable table = getTable(tableName);
        Delete delete = new Delete(rowKey);
        List<Delete> deletes = new ArrayList<Delete>();
        deletes.add(delete);
        table.delete(delete);
        return true;
    }

    /**
     * 删除一堆
     *
     * @param tableName
     * @param rowKeys
     * @return
     * @throws IOException
     */
    public boolean deletes(String tableName, byte[][] rowKeys) throws IOException {
        HTable table = getTable(tableName);
        List<Delete> deletes = new ArrayList<Delete>();
        for (int i = 0; i < rowKeys.length; i++) {
            Delete delete = new Delete(rowKeys[i]);
            deletes.add(delete);
        }
        table.delete(deletes);
        return true;
    }

    /**
     * 获取单条记录
     *
     * @param tableName
     * @param rowKey
     * @return
     * @throws IOException
     */
    public HashMap<String, Object> get(String tableName, byte[] rowKey) throws IOException {
        HashMap<String, Object> returnObj = new HashMap<String, Object>();
        HTable table = getTable(tableName);
        Get get = new Get(rowKey);
        Result result = table.get(get);
        if (result != null) {
            for (Cell c : result.listCells()) {
                String qualifier = new String(c.getQualifier());
                String value = new String(c.getValue());
                returnObj.put(qualifier, value);
            }
        }
        return returnObj;
    }

    /**
     * 通过 rowKey 区间进行scan,scan是前包含后不包含的
     *
     * @param tableName
     * @param startKey
     * @param endKey
     * @return
     * @throws IOException
     */
    public List<HashMap<String, Object>> scan(String tableName, byte[] startKey, byte[] endKey) throws IOException {
        List<HashMap<String, Object>> returnList = new ArrayList<HashMap<String, Object>>();
        HTable table = getTable(tableName);
        Scan scan = new Scan();
        scan.setStartRow(startKey);
        scan.setStopRow(endKey);
        ResultScanner rs = table.getScanner(scan);
        for (Result r : rs) {
            returnList.add(getMapFromRow(r));
        }
        return returnList;
    }


    /**
     * 通过 rowKey 区间进行scan,scan是前包含后不包含的,同时提供分页功能
     *
     * @param tableName
     * @param startKey
     * @param endKey
     * @return
     * @throws IOException
     */
    public Map<String, Object> scan(String tableName, byte[] startKey, byte[] endKey, Integer pageNo, Integer pageSize) throws Exception {
        Map<String, Object> pageMap = new HashMap();
        List<HashMap<String, Object>> returnList = new ArrayList<HashMap<String, Object>>();
        PageScanManager pageScanManager = PageScanManager.getInstance();
        PageScan pageScan = pageScanManager.getPageScan(new String(startKey), new String(endKey), pageSize);
        startKey = pageScan.getPageStartKey(pageNo).getBytes();
        HTable table = getTable(tableName);
        Scan scan = new Scan();
        scan.setStartRow(startKey);
        scan.setStopRow(endKey);
        //当缓存分页已经生成时,设置查询记录数
        if (pageScan.getPageRowKey() != null) {
            scan.setFilter(new PageFilter(pageSize));
        }
        ResultScanner rs = table.getScanner(scan);
        //生成缓存分页 pageNo->startKey
        if (pageScan.getPageRowKey() == null) {
            int rowNum = 0;
            for (Result r : rs) {
                ++rowNum;
                if (rowNum % pageSize == 1) {
                    pageScan.setPageNoStartKey(rowNum / pageSize + 1, new String(r.getRow()));
                }
                if ((rowNum / pageSize + 1) == pageNo || (rowNum % pageSize == 0 && rowNum / pageSize == pageNo)) {
                    returnList.add(getMapFromRow(r));
                }
            }
            pageScan.setTotal(rowNum);
        } else {
            for (Result r : rs) {
                returnList.add(getMapFromRow(r));
            }
        }
        pageMap.put("result", returnList);
        pageMap.put("pageSize", pageSize);
        pageMap.put("pageNo", pageNo);
        pageMap.put("total", pageScan.getTotal());
        return pageMap;
    }

    /**
     * 将Hbase result转化为Map
     *
     * @param result
     * @return
     */
    private HashMap<String, Object> getMapFromRow(Result result) {
        HashMap<String, Object> row = new HashMap();
        String key = new String(result.getRow());
        row.put("key", key);
        for (Cell c : result.listCells()) {
            String qualifier = new String(c.getQualifier());
            String value = new String(c.getValue());
            row.put(qualifier, value);
        }
        return row;
    }
}
