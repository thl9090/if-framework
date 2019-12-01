package com.yx.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnumUtils {

    /**
     * 获取枚举类
     *
     * @param enumClass
     */
    public static List<Map<String, Object>> convertValues(Class enumClass) {
        if (!enumClass.isEnum()) {
            return null;
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            Object[] enumConstants = enumClass.getEnumConstants();
            for (Object enumConstant : enumConstants) {
                result.add(BeanUtils.BeanToMap(enumConstant));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * id转具体枚举类的某个枚举
     *
     * @param key
     * @param sourceVal
     * @param enumClass
     * @return java.lang.Object
     */
    public static <T> T resolve(String key, Integer sourceVal, Class<T> enumClass) {
        if (!enumClass.isEnum()) {
            return null;
        }
        Object object = null;
        try {
            Object[] enumConstants = enumClass.getEnumConstants();
            for (Object enumConstant : enumConstants) {
                Map<String, Object> tmpMap = BeanUtils.BeanToMap(enumConstant);
                if (Integer.valueOf(String.valueOf(tmpMap.get(key))).equals(sourceVal)) {
                    object = enumConstant;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) object;
    }

    /**
     * id转具体枚举类的某个枚举
     *
     * @param key
     * @param sourceVal
     * @param enumClass
     * @return java.lang.Object
     */
    public static <T> T resolve(String key, String sourceVal, Class<T> enumClass) {
        if (!enumClass.isEnum()) {
            return null;
        }
        Object object = null;
        try {
            Object[] enumConstants = enumClass.getEnumConstants();
            for (Object enumConstant : enumConstants) {
                Map<String, Object> tmpMap = BeanUtils.BeanToMap(enumConstant);
                if (String.valueOf(tmpMap.get(key)).equals(sourceVal)) {
                    object = enumConstant;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) object;
    }

    /**
     * 是否包含
     *
     * @param id
     * @param enumClass
     * @return boolean
     */
    public static boolean isCover(Integer id, Class enumClass) {
        if (!enumClass.isEnum()) {
            return false;
        }
        boolean isCover = false;
        try {
            Object[] enumConstants = enumClass.getEnumConstants();
            for (Object enumConstant : enumConstants) {
                Map<String, Object> tmpMap = BeanUtils.BeanToMap(enumConstant);
                if (Integer.valueOf(String.valueOf(tmpMap.get("id"))).equals(id)) {
                    isCover = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isCover;
    }

}
