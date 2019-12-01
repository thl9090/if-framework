package com.yx.common.utils;

import org.springframework.util.StringUtils;

/**
 * 转型操作工具类
 * @author zhangxiaowen
 */
public final class CastUtil {
	/**
	 * obj转为 String 型
	 * @param obj obj对象
	 * @return String String对象
	 */
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    /**
     * obj 转为 String 型（提供默认值）
     * @param obj Object 对象
     * @param defaultValue 默认值
     * @return String string值
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }
    /**
     * Object 转为 double 型
     * @param obj Object对象
     * @return double double类型
     */
    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    /**
     * 转为 double 型（提供默认值）
     * @param obj Object对象
     * @param defaultValue 默认值
     * @return double值
     */
    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtils.hasText(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /**
     * 转为 long 型（提供默认值）
     * @param obj Object对象
     * @return long long类型值
     */
    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    /**
     * 转为 long 型（提供默认值）
     * @param obj Object对象
     * @param defaultValue 默认值
     * @return long long类型值
     */
    public static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtils.hasText(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    /**
     * 转为 int 型
     * @param obj Object对象
     * @return int int类型值
     */
    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    /**
     * 转为 int 型（提供默认值）
     * @param obj Object对象
     * @param defaultValue 默认值
     * @return int int类型值
     */
    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtils.hasText(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    /**
     * 转为 boolean 型
     * @param obj Object对象
     * @return boolean boolean类型值
     */
    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    /**
     * 转为 boolean 型（提供默认值）
     * @param obj Object对象
     * @param defaultValue 默认值
     * @return boolean boolean类型值
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }
}
