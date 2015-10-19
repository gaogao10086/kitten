package com.gys.kitten.core.util;

import com.gys.kitten.core.vo.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Author: kitten
 * Date: 2014/4/18
 * Time: 18:08
 * Des:用于构建数据分页容器
 */
public class PageUtil {
    public static Page bulidPage(HttpServletRequest request) {
        Set<Entry<String, String[]>> st = request.getParameterMap().entrySet();
        int iColumns = ServletRequestUtils.getIntParameter(request, "iColumns", 5);
        int iDisplayLength = ServletRequestUtils.getIntParameter(request, "iDisplayLength", 10);
        int iDisplayStart = ServletRequestUtils.getIntParameter(request, "iDisplayStart", 0);
        String[] mDataPropList = new String[iColumns];
        String[] iSortCol = new String[iColumns];
        String[] sSortDir = new String[iColumns];
        Iterator<Entry<String, String[]>> it = st.iterator();
        while (it.hasNext()) {
            Entry<String, String[]> entry = it.next();
            String key = entry.getKey().toString();
            String obj = entry.getValue()[0];
            if (key.startsWith("mDataProp")) {
                int num = Integer.parseInt(key.substring(10));
                mDataPropList[num] = obj;
            }
            if (key.startsWith("iSortCol")) {
                int num = Integer.parseInt(key.substring(9));
                iSortCol[num] = obj;
            }
            if (key.startsWith("sSortDir")) {
                int num = Integer.parseInt(key.substring(9));
                sSortDir[num] = obj;
            }
        }

        Page page = new Page();
        page.setiDisplayStart(iDisplayStart);
        page.setiDisplayLength(iDisplayLength);
        page.setiSortCol(iSortCol);
        page.setmDataPropList(mDataPropList);
        page.setsSortDir(sSortDir);
        return page;
    }

    /**
     * 为sql增加 order
     *
     * @param page
     * @param sql
     * @return
     */
    public static StringBuilder orderAppend(Page page, StringBuilder sql) {
        sql.append(" ORDER BY ");
        String[] mDataPropList = page.getmDataPropList();
        String[] iSortCol = page.getiSortCol();
        String[] sSortDir = page.getsSortDir();
        for (String sortCol : iSortCol) {
            if (StringUtils.isNotEmpty(sortCol)) {
                sql.append(mDataPropList[Integer.parseInt(sortCol)]).append(" ").append(sSortDir[0]);
            }
        }
        return sql;
    }

    /**
     * 为sql增加limit
     *
     * @param page
     * @param sql
     * @return
     */
    public static StringBuilder limitAppend(Page page, StringBuilder sql) {
        sql.append(" LIMIT ").append(page.getiDisplayStart()).append(",").append(page.getiDisplayLength());
        return sql;
    }
}
