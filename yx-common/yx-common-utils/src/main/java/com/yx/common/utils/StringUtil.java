package com.yx.common.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringUtil {
	
	/**
	 * 常用正则表达式：匹配非负整数（正整数 + 0）
	 */
	public final static String regExp_integer_1 = "^\\d+$";

	/**
	 * 常用正则表达式：匹配正整数
	 */
	public final static String regExp_integer_2 = "^[0-9]*[1-9][0-9]*$";

	/**
	 * 常用正则表达式：匹配非正整数（负整数 + 0）
	 */
	public final static String regExp_integer_3 = "^((-\\d+) ?(0+))$";

	/**
	 * 常用正则表达式：匹配负整数
	 */
	public final static String regExp_integer_4 = "^-[0-9]*[1-9][0-9]*$";

	/**
	 * 常用正则表达式：匹配整数
	 */
	public final static String regExp_integer_5 = "^-?\\d+$";

	/**
	 * 常用正则表达式：匹配非负浮点数（正浮点数 + 0）
	 */
	public final static String regExp_float_1 = "^\\d+(\\.\\d+)?$";

	/**
	 * 常用正则表达式：匹配正浮点数
	 */
	public final static String regExp_float_2 = "^(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*))$";

	/**
	 * 常用正则表达式：匹配非正浮点数（负浮点数 + 0）
	 */
	public final static String regExp_float_3 = "^((-\\d+(\\.\\d+)?) ?(0+(\\.0+)?))$";

	/**
	 * 常用正则表达式：匹配负浮点数
	 */
	public final static String regExp_float_4 = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*)))$";

	/**
	 * 常用正则表达式：匹配浮点数
	 */
	public final static String regExp_float_5 = "^(-?\\d+)(\\.\\d+)?$";

	/**
	 * 常用正则表达式：匹配由26个英文字母组成的字符串
	 */
	public final static String regExp_letter_1 = "^[A-Za-z]+$";

	/**
	 * 常用正则表达式：匹配由26个英文字母的大写组成的字符串
	 */
	public final static String regExp_letter_2 = "^[A-Z]+$";

	/**
	 * 常用正则表达式：匹配由26个英文字母的小写组成的字符串
	 */
	public final static String regExp_letter_3 = "^[a-z]+$";

	/**
	 * 常用正则表达式：匹配由数字和26个英文字母组成的字符串
	 */
	public final static String regExp_letter_4 = "^[A-Za-z0-9]+$";

	/**
	 * 常用正则表达式：匹配由数字、26个英文字母或者下划线组成的字符串
	 */
	public final static String regExp_letter_5 = "^\\w+$";

	/**
	 * 常用正则表达式：匹配email地址
	 */
	public final static String regExp_email = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

	/**
	 * 常用正则表达式：匹配url
	 */
	public final static String regExp_url_1 = "^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$";

	/**
	 * 常用正则表达式：匹配url
	 */
	public final static String regExp_url_2 = "[a-zA-z]+://[^\\s]*";

	/**
	 * 常用正则表达式：匹配中文字符
	 */
	public final static String regExp_chinese_1 = "[\\u4e00-\\u9fa5]";

	/**
	 * 常用正则表达式：匹配双字节字符(包括汉字在内)
	 */
	public final static String regExp_chinese_2 = "[^\\x00-\\xff]";

	/**
	 * 常用正则表达式：匹配空行
	 */
	public final static String regExp_line = "\\n[\\s ? ]*\\r";

	/**
	 * 常用正则表达式：匹配HTML标记
	 */
	public final static String regExp_html_1 = "/ <(.*)>.* <\\/\\1> ? <(.*) \\/>/";

	/**
	 * 常用正则表达式：匹配首尾空格
	 */
	public final static String regExp_startEndEmpty = "(^\\s*) ?(\\s*$)";

	/**
	 * 常用正则表达式：匹配帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)
	 */
	public final static String regExp_accountNumber = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";

	/**
	 * 常用正则表达式：匹配国内电话号码，匹配形式如 0511-4405222 或 021-87888822
	 */
	public final static String regExp_telephone = "\\d{3}-\\d{8} ?\\d{4}-\\d{7}";

	/**
	 * 常用正则表达式：腾讯QQ号, 腾讯QQ号从10000开始
	 */
	public final static String regExp_qq = "[1-9][0-9]{4,}";

	/**
	 * 常用正则表达式：匹配中国邮政编码
	 */
	public final static String regExp_postbody = "[1-9]\\d{5}(?!\\d)";

	/**
	 * 常用正则表达式：匹配身份证, 中国的身份证为15位或18位
	 */
	public final static String regExp_idCard = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";

	/**
	 * 常用正则表达式：IP
	 */
	public final static String regExp_ip = "\\d+\\.\\d+\\.\\d+\\.\\d+";

	/**
	 * 常用正则表达式：手机号码
	 */
	public final static String regExp_phone = "^1[34578]{1}[0-9]{9}$";
	
	/**
	 * 字符编码
	 */
	public final static String encoding = "UTF-8";
	
	/**
	 * 常用正则表达式：密码包含字母和数字，长度为6-20位
	 */
	public final static String passwordMatch = "^(?![^0-9]+$)(?![^a-zA-Z]+$).+$";

	/**
	 * 验证字符串是否匹配指定正则表达式
	 * 
	 * @param content 内容
	 * @param regExp 参数
	 * @return boolean 是否匹配指定正则表达式
	 */
	public static boolean regExpVali(String content, String regExp) {
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(content);
		return matcher.matches();
	}
	
	/**
	 * 验证字符串是否匹配指定正则表达式
	 * 验证中文字符必须使用该方法
	 * @param content 内容
	 * @param regExp 参数
	 * @return boolean 是否匹配指定正则表达式
	 */
	public static boolean regExpValiEx(String content, String regExp) {
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(content);
		return matcher.find();
	}

	/**
	 * double精度调整
	 * 
	 * @param doubleValue 需要调整的值123.454
	 * @param format 目标样式".##"
	 * @return String double精度调整
	 */
	public static String decimalFormat(double doubleValue, String format) {
		DecimalFormat myFormatter = new DecimalFormat(format);
		String formatValue = myFormatter.format(doubleValue);
		return formatValue;
	}


	/**
	 * Url Base64编码
	 *
	 * @param data 待编码数据
	 * @return String 编码数据
	 * @throws Exception 异常
	 */
	public static String encode(String data) throws Exception {
		// 执行编码
		byte[] b = Base64.encodeBase64URLSafe(data.getBytes(encoding));
		return new String(b, encoding);
	}

	/**
	 * Url Base64解码
	 *
	 * @param data 待解码数据
	 * @return String 解码数据
	 * @throws Exception 异常
	 */
	public static String decode(String data) throws Exception {
		// 执行解码
		byte[] b = Base64.decodeBase64(data.getBytes(encoding));
		return new String(b, encoding);
	}


	/**
	 * URL编码（utf-8）
	 * @param source 参数
	 * @return String URL
	 */
	public static String urlEncode(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据内容类型判断文件扩展名
	 * 
	 * @param contentType  内容类型
	 * @return String  文件扩展名
	 */
	public static String getFileExt(String contentType) {
		String fileExt = "";
		if ("image/jpeg".equals(contentType)) {
			fileExt = ".jpg";

		} else if ("audio/mpeg".equals(contentType)) {
			fileExt = ".mp3";

		} else if ("audio/amr".equals(contentType)) {
			fileExt = ".amr";

		} else if ("video/mp4".equals(contentType)) {
			fileExt = ".mp4";

		} else if ("video/mpeg4".equals(contentType)) {
			fileExt = ".mp4";
		}

		return fileExt;
	}

	/**
	 * 获取bean名称
	 * @param bean bean对象
	 * @return String bean名称
	 */
	public static String beanName(Object bean) {
		String fullClassName = bean.getClass().getName();
		String classNameTemp = fullClassName.substring(fullClassName.lastIndexOf(".") + 1, fullClassName.length());
		return classNameTemp.substring(0, 1) + classNameTemp.substring(1);
	}

	public final static Pattern referer_pattern = Pattern.compile("@([^@^\\s^:]{1,})([\\s\\:\\,\\;]{0,1})");// @.+?[\\s:]


	/**
	 * 首字母转小写
	 * @param s 参数
	 * @return String 首字母转小写
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0))) {
			return s;
		} else {
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
		}
	}

	/**
	 * 首字母转大写
	 * 
	 * @param s 参数
	 * @return String 首字母转大写
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0))) {
			return s;
		} else {
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
		}
	}
	
	/**
	 * 判断长度是否在限定范围内（包括边界）
	 * @param str 校验的字符串
	 * @param minlen 最小长度
	 * @param maxlen 最大长度
	 * @return boolean 是否在限定范围内
	 */
	public static boolean isLength(String str,int minlen,int maxlen){
		try {
			if(str == null || minlen < 0 || maxlen < 0 || minlen > maxlen){
				return false;
			}
			byte[] ss = str.getBytes("GBK");
			return (ss.length >= minlen) && (ss.length <= maxlen);
		} catch (UnsupportedEncodingException e) {
			return false;
		}
	}
	
	/**
	 * 判断是否为非法的请求：
	 * 1、SQL注入
	 * 2、跨站脚本
	 * @param str 参数
	 * @return boolean 是否为非法的请求
	 */
	public static boolean isValid(String str) {  
	    return isValidSqlInput(str) && isValidScriptInput(str);
	}  
	
	/**
	 * 判断是否为合法的SQL输入，使用正则表达式防止SQL注入
	 * @param str 参数
	 * @return boolean 是否为合法的SQL输入
	 */
	public static boolean isValidSqlInput(String str) {  
		String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"  
	            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";  
		Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);  
	    if (sqlPattern.matcher(str).find()) {  
	        return false;  
	    }  
	    return true;  
	}  
	
	/**
	 * 判断是否为合法的脚本输入，使用正则表达式防止跨站脚本
	 * @param str 参数
	 * @return boolean 合法的脚本输入
	 */
	public static boolean isValidScriptInput(String str) {  
		String regEx_xml = "<xml[^>]*?>[\\s\\S]*?<\\/xml>"; //定义xml的正则表达式 
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式 
		String reg = regEx_xml + regEx_script + regEx_style +regEx_html;  
		Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);  
	    if (sqlPattern.matcher(str).find()) {  
	        return false;  
	    }  
	    return true;  
	}  
	
	/**
     * 格式化文本
     * 
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values 参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... values) {
        if (values == null || values.length <= 0 || isBlank(template)) {
            return template;
        }

        final StringBuilder sb = new StringBuilder();
        final int length = template.length();

        int valueIndex = 0;
        char currentChar;
        for (int i = 0; i < length; i++) {
            if (valueIndex >= values.length) {
                sb.append(sub(template, i, length));
                break;
            }

            currentChar = template.charAt(i);
            if (currentChar == '{') {
                final char nextChar = template.charAt(++i);
                if (nextChar == '}') {
                    sb.append(values[valueIndex++]);
                } else {
                    sb.append('{').append(nextChar);
                }
            } else {
                sb.append(currentChar);
            }

        }

        return sb.toString();
    }
    /**
     * 改进JDK subString
     * index从0开始计算，最后一个字符为-1
     * @param string String
     * @param fromIndex 开始的index（包括）
     * @param toIndex 结束的index（不包括）
     * @return 字串
     */
    public static String sub(String string, int fromIndex, int toIndex) {
        int len = string.length();

        if (fromIndex < 0) {
            fromIndex = len + fromIndex;

            if (toIndex == 0) {
                toIndex = len;
            }
        }

        if (toIndex < 0) {
            toIndex = len + toIndex;
        }

        if (toIndex < fromIndex) {
            int tmp = fromIndex;
            fromIndex = toIndex;
            toIndex = tmp;
        }

        if (fromIndex == toIndex) {
            return "";
        }

        char[] strArray = string.toCharArray();
        char[] newStrArray = Arrays.copyOfRange(strArray, fromIndex, toIndex);
        return new String(newStrArray);
    }

    /**
     * 格式化文本
     * 
     * @param template 文本模板，被替换的部分用 {key} 表示
     * @param map 参数值对
     * @return 格式化后的文本
     */
    public static String format(String template, Map<?, ?> map) {
        if (null == map || map.isEmpty()) {
            return template;
        }

        for (Entry<?, ?> entry : map.entrySet()) {
            template = template.replace("{" + entry.getKey() + "}", entry.getValue().toString());
        }
        return template;
    }
    
    /**
     * 字符串是否为空白 空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""<br>
     * 
     * @param str 被检测的字符串
     * @return 是否为空
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            // 只要有一个非空字符即为非空字符串
            if (false == Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

	public static String getRandom6Number() {
		Random random = new Random();
		int code = 0;
		while (code == 0 || code == 999999) {
			code = random.nextInt(999999);
		}
		
		if(code < 10) {
            code = code * 100000;
        } else if(code < 100) {
            code = code * 10000;
        } else if(code < 1000) {
            code = code * 1000;
        } else if(code < 10000) {
            code = code * 100;
        } else if(code < 100000) {
            code = code * 10;
        }
		 
		return String.valueOf(code);
	}

	/**
	 * 判断参数是否可以转为数字类型
	 * @param str
	 * @return
	 */
	public static boolean isDigit(String str){
		if( null == str || str.length() == 0 ){
			return false;
		}
		for(int i = str.length(); --i >= 0;){
			int c = str.charAt(i);
			if( c < 48 || c > 57 ){
				return false;
			}
		}
		return true;
	}

	/**
	 * 手机发送列表中，所有的手机号格式都必须正确
	 * @param phone 手机号码集合s
	 */
	public static boolean checkPhone(String phone) {
		return StringUtil.regExpVali(phone, StringUtil.regExp_phone);
	}

	/**
	 * 用户名：支持中英文，数字，下划线
	 * @param userName
	 * @return
	 */
	public static boolean validateUserName(String userName){
		String validateStr = "^[\\w\\-－＿[０-９]\u4e00-\u9fa5\uFF21-\uFF3A\uFF41-\uFF5A]+$";
		Pattern pattern = Pattern.compile(validateStr);
		Matcher matcher = pattern.matcher(userName);
		return matcher.matches();
	}
	
}
