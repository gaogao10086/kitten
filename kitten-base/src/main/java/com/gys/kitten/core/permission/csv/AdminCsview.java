package com.gys.kitten.core.permission.csv;

import com.gys.kitten.core.permission.entity.AdminEntity;
import com.gys.kitten.core.util.AbstractCsvView;

import java.io.IOException;
import java.util.List;

/**
 * Author: kitten
 * Date: 2014/6/12
 * Time: 10:35
 * Des:导出Admin Excel
 */
public class AdminCsview extends AbstractCsvView {

    private static final String FILE_NAME = "用户明细列表";

    private static final String[] HEADERS = {"数据库ID", "用户姓名", "用户账号", "用户密码", "状态", "创建时间", "最后更新时间", "创建者", "最后更新者"};

    public AdminCsview(List details) {
        super(details);
    }

    @Override
    protected String getFileName() {
        return FILE_NAME;
    }

    @Override
    protected String[] getHeaders() {
        return HEADERS;
    }

    @Override
    protected void writeContents() throws IOException {
        // 强制转换成对应列表
        List<AdminEntity> beans = (List<AdminEntity>) details;
        for (AdminEntity bean : beans) {
            write(bean.getAdminId());
            write(bean.getName());
            write(bean.getLegacyId());
            write(bean.getPassword());
            write(bean.getStatus());
            write(bean.getCreateTime().toString());
            write(bean.getUpdateTime().toString());
            write(bean.getCreatedBy());
            write(bean.getUpdatedBy());
            newLine();
        }
    }
}
