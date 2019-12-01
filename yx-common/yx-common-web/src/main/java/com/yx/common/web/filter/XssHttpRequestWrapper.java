package com.yx.common.web.filter;

import cn.hutool.core.util.ArrayUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

/**
 * 防XSS漏洞Request包装器
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public class XssHttpRequestWrapper extends HttpServletRequestWrapper {


    private static Pattern scriptPattern_between = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
    private static Pattern scriptPattern_src = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern scriptPattern_src_2 = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern scriptPattern_end = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
    private static Pattern scriptPattern_begin = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern scriptPattern_eval = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern scriptPattern_expression = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern scriptPattern_javascript = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
    private static Pattern scriptPattern_vbscript = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
    private static Pattern scriptPattern_onload = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    private HttpServletRequest orgRequest = null;

    public XssHttpRequestWrapper(HttpServletRequest request) {
        super(request);
        this.orgRequest = request;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value != null) {
            value = this.xssEncode(value);
        }
        return value;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value != null) {
            value = this.xssEncode(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (ArrayUtil.isNotEmpty(values)) {
            for (int i = 0; i < values.length; i++) {
                values[i] = this.xssEncode(values[i]);
            }
        }

        return values;
    }

    /**
     * 将可能引起xss漏洞的半角字符直接替换成全角字符
     *
     * @param value
     * @return java.lang.String
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    private String xssEncode(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        value = stripXSS(value);

        StringBuilder sb = new StringBuilder(value.length() + 16);
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '>':
                    // 全角大于号
                    sb.append('＞');
                    break;
                case '<':
                    // 全角小于号
                    sb.append('＜');
                    break;
                case '\'':
                    // 全角单引号
                    sb.append('‘');
                    break;
                case '\"':
                    // 全角双引号
                    sb.append('“');
                    break;
                case '&':
                    // 全角
                    sb.append('＆');
                    break;
                case '\\':
                    // 全角斜线
                    sb.append('＼');
                    break;
                case '#':
                    // 全角井号
                    sb.append('＃');
                    break;
                case '(':
                    sb.append('（');
                    break;
                case ')':
                    sb.append('）');
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 将可能产生XSS漏洞的字符串替换空串
     *
     * @param value
     * @return java.lang.String
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    private String stripXSS(String value) {
        if (value != null) {
            // Avoid anything between script tags
            value = scriptPattern_between.matcher(value).replaceAll("");

            // Avoid anything in a src='...' type of expression
            value = scriptPattern_src.matcher(value).replaceAll("");
            value = scriptPattern_src_2.matcher(value).replaceAll("");

            // Remove any lonesome </script> tag
            value = scriptPattern_end.matcher(value).replaceAll("");

            // Remove any lonesome <script ...> tag
            value = scriptPattern_begin.matcher(value).replaceAll("");

            // Avoid eval(...) expressions
            value = scriptPattern_eval.matcher(value).replaceAll("");

            // Avoid expression(...) expressions
            value = scriptPattern_expression.matcher(value).replaceAll("");

            // Avoid javascript:... expressions
            value = scriptPattern_javascript.matcher(value).replaceAll("");

            // Avoid vbscript:... expressions
            value = scriptPattern_vbscript.matcher(value).replaceAll("");

            // Avoid onload= expressions
            value = scriptPattern_onload.matcher(value).replaceAll("");
        }
        return value;
    }
}
