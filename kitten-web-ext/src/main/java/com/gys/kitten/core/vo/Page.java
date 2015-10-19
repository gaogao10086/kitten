package com.gys.kitten.core.vo;

import java.io.Serializable;
import java.util.List;

/**
 * User: GaoYS
 * Date: 14-1-12
 * Time: 下午3:35
 * Des：该VO用于给FlexGrid提供数据
 */
public class Page<T> implements Serializable {

    private int iDisplayStart;
    private int iDisplayLength;
    private int iTotalDisplayRecords;
    private String[] mDataPropList;
    private String[] iSortCol;
    private String[] sSortDir;
    private List<T> data;

    public Page() {
    }
    public Page(List<T> data) {
        this.data = data;
    }

    public int getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public int getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String[] getmDataPropList() {
        return mDataPropList;
    }

    public void setmDataPropList(String[] mDataPropList) {
        this.mDataPropList = mDataPropList;
    }

    public String[] getiSortCol() {
        return iSortCol;
    }

    public void setiSortCol(String[] iSortCol) {
        this.iSortCol = iSortCol;
    }

    public String[] getsSortDir() {
        return sSortDir;
    }

    public void setsSortDir(String[] sSortDir) {
        this.sSortDir = sSortDir;
    }
}
