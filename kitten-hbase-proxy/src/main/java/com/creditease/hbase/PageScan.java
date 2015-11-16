package com.creditease.hbase;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * author: GaoYS
 * CreateData: 2015/7/28 18:36
 */
public class PageScan {

    private String sKey;
    private String eKey;
    private int pageSize;
    private int total;
    private long startTime;

    private Map<Integer, String> pageRowKey;

    public PageScan(String startKey, String endKey, int pageSize) {
        this.sKey = startKey;
        this.eKey = endKey;
        this.pageSize = pageSize;
        this.startTime = System.currentTimeMillis();
    }

    public synchronized void setPageNoStartKey(int pageNo, String startKey) {
        Preconditions.checkArgument(pageNo >= 0, "must be positive %s", pageNo);
        if (pageRowKey == null) {
            pageRowKey = new HashMap<Integer, String>();
        }
        String prePageRowKey = pageRowKey.get(pageNo);
        if (prePageRowKey != null) {
            pageRowKey.remove(pageNo);
        }
        pageRowKey.put(pageNo, startKey);
    }

    /**
     * 通过页码数获得起始key
     *
     * @param pageNo
     * @return
     * @throws Exception
     */
    public String getPageStartKey(int pageNo) throws Exception {
        //如果pageRowKey==null,即为分页初始化,直接返回startKey
        if (this.pageRowKey == null) {
            return this.sKey;
        }
        if (pageNo <= 1) {
            return this.sKey;
        }
        this.startTime = System.currentTimeMillis();
        String nextK = this.pageRowKey.get(pageNo);
        if (nextK == null) {
            return this.sKey;
        }
        return nextK;
    }

    public String getKEY() {
        return this.sKey + this.eKey + this.pageSize;
    }

    public String getsKey() {
        return sKey;
    }

    public String geteKey() {
        return eKey;
    }

    public long getStartTime() {
        return startTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        PageScan pre = (PageScan) obj;
        if (this.sKey.equals(pre.getsKey()) && this.eKey.equals(pre.geteKey())
                && this.pageSize == pre.pageSize)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return new StringBuilder(this.sKey).append(this.eKey)
                .append(this.pageSize).toString().hashCode();
    }

    public Map<Integer, String> getPageRowKey() {
        return pageRowKey;
    }

    public void setsKey(String sKey) {
        this.sKey = sKey;
    }

    public void seteKey(String eKey) {
        this.eKey = eKey;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setPageRowKey(Map<Integer, String> pageRowKey) {
        this.pageRowKey = pageRowKey;
    }
}
