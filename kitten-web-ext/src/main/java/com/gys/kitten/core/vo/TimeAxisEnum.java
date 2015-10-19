package com.gys.kitten.core.vo;

/**
 * Author: kitten
 * Date: 13-12-15
 * Time: 上午11:55
 * Des: 标准时间戳格式化工具
 */
public enum TimeAxisEnum {

    HOUR(1111, "year,month,day,hour", "year,month,day,hour"),
    DATE(1110, "year,month,day", "year,month,day"),
    WEEK(1999, "year,week", "year,week"),
    WEEK_FROM_DATE(2000, "year,date_format(concat(cast(year as char(4)),'-',cast(month as char(2)),'-',cast(day as char(2))),'%v') as week", "year,week"),
    MONTH(1100, "year,month", "year,month"),
    YEAR(1000, "year", "year");
    private Integer id;
    private String select;
    private String groupBy;

    private TimeAxisEnum(Integer id, String select, String groupBy) {
        this.id = id;
        this.select = select;
        this.groupBy = groupBy;
    }

    public static TimeAxisEnum valueOf(Integer id) {
        if (id.intValue() == 1110) {
            return DATE;
        } else if (id.intValue() == 2000) {
            return WEEK;
        } else if (id.intValue() == 1100) {
            return MONTH;
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public String getSelect() {
        return select;
    }

    public String getGroupBy() {
        return groupBy;
    }
}