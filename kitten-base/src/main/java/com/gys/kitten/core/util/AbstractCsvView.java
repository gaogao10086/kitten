package com.gys.kitten.core.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Author: kitten
 * Date: 14-6-9
 * Time: 下午4:17
 * Des:抽象CSV视图，提供CSV下载功能的父抽象类
 * <b>使用方法：</b>
 * <ul>
 * <li>每种不同的格式的导出CSV文件对应一个该类的子类，由子类定义文件列头和文件详细</li>
 * <li>子类通过重写{@link AbstractCsvView#getFileName()} ()}抽象方法提供scv文件的文件名</li>
 * <li>子类通过重写{@link AbstractCsvView#getHeaders()}抽象方法提供scv文件内容列头名</li>
 * <li>子类通过重写{@link  AbstractCsvView#writeContents()}抽象方法组织scv内容详细的顺序</li>
 * </ul>
 * <ul>
 * <li>本类实现了对基本数据写出并追加CSV分隔符的封装，如果有必要可以继续提供对其他常用类型的封装</li>
 * <li>写出String类型时要根据CSV规则</li>
 */

public abstract class AbstractCsvView extends AbstractView {

    /**
     * CSV使用的字符集（仅考虑支持Excel）
     */
    private static final String CSV_CHARSET = "GB2312";

    /**
     * 分隔符
     */
    private static final char DELIMITER = ',';

    /**
     * 单个双引号
     */
    public static final char QUOTE = '"';
    public static final String QUOTE_STR = "\"";

    /**
     * 双个双引号
     */
    public static final String DOUBLE_QUOTE = "\"\"";

    /**
     * 换行符
     */
    public static final char LF = '\n';

    /**
     * 回车符
     */
    public static final char CR = '\r';

    /**
     * 向浏览器写数据的缓冲writer
     */
    protected BufferedWriter writer;

    /**
     * CSV数据详细的列表
     */
    protected List details;

    protected AbstractCsvView(List details) {
        this.details = details;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> modelMap, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        // 指定字符集，仅支持Excel
        response.setCharacterEncoding(CSV_CHARSET);
        writer = new BufferedWriter(response.getWriter());

        String filename = getFileName();
        //　根据浏览器的不同，对文件名做不同的编码
        String agent = request.getHeader("USER-AGENT");
        if (StringUtils.isNotEmpty(agent)) {
            // 统一转成小写字母再检索
            agent = agent.toLowerCase();
            if (agent.indexOf("firefox") > -1) {
                // firefox 火狐浏览器
                filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
            } else if (agent.indexOf("applewebkit") != -1) {
                //chrome
                filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
            } else if (agent.indexOf("safari") != -1) {
                //safari
                filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
            } else {
                // IE 浏览器及其他
                filename = URLEncoder.encode(filename, CSV_CHARSET);
            }
            // TODO  根据产品需求，对更多浏览器做测试，提供支持
        } else {
            // 其他浏览器及其他
            filename = URLEncoder.encode(filename, CSV_CHARSET);
        }

        // 根据子类设置文件名
        response.addHeader("Content-Disposition", String.format("attachment; filename=\"%s.csv\"", filename));
        response.setContentType("application/csv;charset=" + CSV_CHARSET);

        // 迭代输出csv的列头
        for (String head : getHeaders()) {
            write(head);
        }
        // 列头结束换行
        newLine();
        // 由子类输出csv详细
        writeContents();

        writer.flush();
        writer.close();
    }

    /**
     * 取得SCV文件的文件名
     *
     * @return
     */
    protected abstract String getFileName();

    /**
     * 取得SCV文件每列的列头名字
     *
     * @return
     */
    protected abstract String[] getHeaders();

    /**
     * 向浏览器输出CSV详细内容
     * <p/>
     * 由子类实现转换细节，例如组织顺序或者计算汇总等
     */
    protected abstract void writeContents() throws IOException;

    /**
     * 输出回车换行
     *
     * @throws IOException
     */
    protected void newLine() throws IOException {
        writer.newLine();
    }

    /**
     * 输出byte类型数据和分隔符
     *
     * @throws IOException
     */
    protected void write(byte data) throws IOException {
        writer.write(String.valueOf(data));
        writer.write(DELIMITER);
    }

    /**
     * 输出char类型数据和分隔符
     *
     * @throws IOException
     */
    protected void write(char data) throws IOException {
        writer.write(String.valueOf(data));
        writer.write(DELIMITER);
    }

    /**
     * 输出int类型数据和分隔符
     *
     * @throws IOException
     */
    protected void write(int data) throws IOException {
        writer.write(String.valueOf(data));
        writer.write(DELIMITER);
    }

    /**
     * 输出long类型数据和分隔符
     *
     * @throws IOException
     */
    protected void write(long data) throws IOException {
        writer.write(String.valueOf(data));
        writer.write(DELIMITER);
    }

    /**
     * 输出float类型数据和分隔符
     *
     * @throws IOException
     */
    protected void write(float data) throws IOException {
        writer.write(String.valueOf(data));
        writer.write(DELIMITER);
    }

    /**
     * 输出double类型数据和分隔符
     *
     * @throws IOException
     */
    protected void write(double data) throws IOException {
        writer.write(String.valueOf(data));
        writer.write(DELIMITER);
    }

    /**
     * 输出boolean类型数据和分隔符
     *
     * @throws IOException
     */
    protected void write(boolean data) throws IOException {
        writer.write(String.valueOf(data));
        writer.write(DELIMITER);
    }

    /**
     * 输出字符串类型数据和分隔符
     * <p/>
     * <b>注意：</b></br>
     * 对于被CSV规则限制的文本内容，需要进行如下文本改造：
     * <ul>
     * <li>文本前后各加入一双引号</li>
     * <li>如果文本中包含双引号，将每个双引号替换成连续两个双引号</li>
     * </ul>
     *
     * @throws IOException
     */
    protected void write(String data) throws IOException {
        // 文本被限制：双引号包裹
        if (StringUtils.isEmpty(data)) {
            writer.write("");
        } else if (checkTextQualify(data)) {
            writer.write(QUOTE);
            // 将单个双引号替换成两个双引号
            data = replace(data, QUOTE_STR, DOUBLE_QUOTE);
            writer.write(data);
            writer.write(QUOTE);
        } else {
            // 不被限制：直接输出
            writer.write(data);
        }
        writer.write(DELIMITER);
    }

    /**
     * 检查文本内容是否被限制
     * <p/>
     * CSV的文本内容被限制的规则：
     * <ul>
     * <li>包含分隔符: {@link AbstractCsvView#DELIMITER}</li>
     * <li>包含双引号符: {@link AbstractCsvView#QUOTE}</li>
     * <li>包含换行符或者回车符: {@link AbstractCsvView#LF}、{@link AbstractCsvView#CR}</li>
     * </ul>
     *
     * @param csvText 文本内容
     * @return 如果内容被限制返回true，否则返回false
     */
    private boolean checkTextQualify(String csvText) {
        if (csvText.indexOf(DELIMITER) > -1
                || csvText.indexOf(QUOTE) > -1
                || csvText.indexOf(LF) > -1
                || csvText.indexOf(CR) > -1) {
            return true;
        }
        return false;
    }

    /**
     * 检索字符串内容中是否包含指定子串，并以指定子串全部替换
     * <p/>
     * 该方法在功能上等同于{@link String#replaceAll(String, String)},但节约了创建正则的开销
     *
     * @param original 原始字符串
     * @param pattern  需要检索的子串
     * @param replace  用来替换的子串
     * @return 如果没有需要替换的子串返回原始串，需要需要替换返回替换后的字符串
     */
    private static String replace(String original, String pattern, String replace) {
        final int len = pattern.length();
        int found = original.indexOf(pattern);
        if (found > -1) {
            StringBuffer sb = new StringBuffer();
            int start = 0;
            while (found != -1) {
                sb.append(original.substring(start, found));
                sb.append(replace);
                start = found + len;
                found = original.indexOf(pattern, start);
            }
            sb.append(original.substring(start));
            return sb.toString();
        } else {
            return original;
        }
    }
}