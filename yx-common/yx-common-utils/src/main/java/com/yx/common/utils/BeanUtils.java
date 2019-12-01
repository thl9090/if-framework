package com.yx.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 对象工具类
 *
 * @author YanBingHao
 * @since 2018/7/30
 */
public class BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * @Title: 转换bean信息
     * @MethodName: convertBean
     * @Author: YanBingHao
     * @param: [bean, clazz]
     * @return: T
     */
    public static <T> T convertBean(Object bean, Class<T> clazz) {
        try {
            T result = clazz.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(bean, result);
            return result;
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    /**
     * @Title: 转换list信息
     * @MethodName: convertList
     * @Author: YanBingHao
     * @param: [list, clazz]
     * @return: java.util.List<T>
     */
    public static <T> List<T> convertList(List<?> list, Class<? extends T> clazz) {
        try {
            List<T> result = new ArrayList<T>(list.size());
            for (Object bean : list) {
                T obj = clazz.newInstance();
                org.springframework.beans.BeanUtils.copyProperties(bean, obj);
                result.add(obj);
            }
            return result;
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    /**
     * @Title: 将Map中存放的键值对转成对象的属性值
     * @MethodName: MapToBean
     * @Author: YanBingHao
     * @param: [map, t]
     * @return: T
     */
    public static <T> T MapToBean(Map<String, Object> map, T t) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());
            for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {// 得到该对象的属性
                String key = property.getName();// 得到属性名称
                if (map.containsKey(key)) {// 对比Map的主键名称是否是对象的属性
                    Object value = map.get(key);// 得到键值对的值
                    Method setter = property.getWriteMethod();// 得到property对应的setter方法
                    setter.invoke(t, value);// 对对象赋值
                }
            }
        } catch (Exception e) {
            logger.error("MapToBean Error：", e.getMessage());
            e.printStackTrace();
        }
        return t;
    }

    /**
     * @Title: 将对象转成对应的Map键值对
     * @MethodName: BeanToMap
     * @Author: YanBingHao
     * @param: [obj]
     * @return: java.util.Map<java.lang.String   ,   java.lang.Object>
     */
    public static Map<String, Object> BeanToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {// 得到该对象的属性
                String key = property.getName();// 得到属性名称
                if (!key.equals("class")) {// 过滤class属性
                    Method getter = property.getReadMethod();// 得到property对应的getter方法
                    Object value = getter.invoke(obj);// 得到属性值
                    map.put(key, value);// map键值对赋值
                }
            }
        } catch (Exception e) {
            logger.error("BeanToMap Error ", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @Title: 将对象转成对应的Map键值对（键的前面加上adapter（适配器））
     * @MethodName: BeanToMsgMap
     * @Author: YanBingHao
     * @param: [obj, adapter]
     * @return: java.util.Map<java.lang.String   ,   java.lang.String>
     */
    public static Map<String, String> BeanToMsgMap(Object obj, String adapter) {
        if (obj == null) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {// 得到该对象的属性
                String key = property.getName();// 得到属性名称
                if (!key.equals("class")) {// 过滤class属性
                    Method getter = property.getReadMethod();// 得到property对应的getter方法
                    Object value = getter.invoke(obj);// 得到属性值
                    if (value instanceof String) {
                        if (StringUtils.isNotBlank(value.toString())) {
                            map.put(adapter + "{" + key + "}", value.toString());// map键值对赋值
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("BeanToMsgMap Error ", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @Title: 得到对象的所有属性
     * @MethodName: BeanGetProperty
     * @Author: YanBingHao
     * @param: [objClass]
     * @return: java.lang.String[]
     */
    public static String[] BeanGetProperty(Class<?> objClass) {
        if (objClass == null) {
            return null;
        }
        String[] propertys = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(objClass);
            propertys = new String[beanInfo.getPropertyDescriptors().length];
            for (int i = 0; i < beanInfo.getPropertyDescriptors().length; i++) {
                if (!beanInfo.getPropertyDescriptors()[i].getName().equals("class")
                        && !beanInfo.getPropertyDescriptors()[i].getName().equals("null")) {// 过滤class属性
                    propertys[i] = beanInfo.getPropertyDescriptors()[i].getName();
                }
            }
        } catch (Exception e) {
            logger.error("BeanGetProperty Error ", e.getMessage());
            e.printStackTrace();
        }
        return propertys;
    }

    /**
     * @Title: 获取class的属性（排序的）
     * @MethodName: BeanGetPropertySort
     * @Author: YanBingHao
     * @param: [objClass]
     * @return: java.util.Set<java.lang.String>
     */
    public static Set<String> BeanGetPropertySort(Class<?> objClass) {
        if (objClass == null) {
            return null;
        }
        Set<String> propertys = new TreeSet<String>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(objClass);
            for (int i = 0; i < beanInfo.getPropertyDescriptors().length; i++) {
                if (!beanInfo.getPropertyDescriptors()[i].getName().equals("class")
                        && !beanInfo.getPropertyDescriptors()[i].getName().equals("null")) {// 过滤class属性
                    propertys.add(beanInfo.getPropertyDescriptors()[i].getName());
                }
            }
        } catch (Exception e) {
            logger.error("BeanGetPropertySort Error ", e.getMessage());
            e.printStackTrace();
        }
        return propertys;
    }

    /**
     * @Title: 得到对象的所有属性和类别
     * @MethodName: BeanGetPropertyAndClass
     * @Author: YanBingHao
     * @param: [objClass]
     * @return: java.util.Map<java.lang.String   ,   java.lang.String>
     */
    public static Map<String, String> BeanGetPropertyAndClass(Class<?> objClass) {
        if (objClass == null) {
            return null;
        }
        Map<String, String> paramMap = new HashMap<String, String>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(objClass);
            for (int i = 0; i < beanInfo.getPropertyDescriptors().length; i++) {
                if (!beanInfo.getPropertyDescriptors()[i].getName().equals("class")
                        && beanInfo.getPropertyDescriptors()[i].getName() != null) {// 过滤class属性
                    String className = beanInfo.getPropertyDescriptors()[i].getPropertyType().getName();
                    className = className.substring(className.lastIndexOf(".") + 1);
                    paramMap.put(beanInfo.getPropertyDescriptors()[i].getName(), className);
                }
            }
        } catch (Exception e) {
            logger.error("BeanGetPropertyAndClass Error ", e.getMessage());
            e.printStackTrace();
        }
        return paramMap;
    }

    /**
     * @Title: 得到对象的属性个数
     * @MethodName: BeanGetPropertySize
     * @Author: YanBingHao
     * @param: [objClass]
     * @return: java.lang.Integer
     */
    public static Integer BeanGetPropertySize(Class<?> objClass) {
        if (objClass == null) {
            return null;
        }
        Integer size = 0;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(objClass);
            for (int i = 0; i < beanInfo.getPropertyDescriptors().length; i++) {
                if (!beanInfo.getPropertyDescriptors()[i].getName().equals("class")) {// 过滤class属性
                    size++;
                }
            }
        } catch (Exception e) {
            logger.error("BeanGetPropertySize Error ", e.getMessage());
            e.printStackTrace();
        }
        return size;
    }

    /**
     * @Title: 将对象转成string
     * @MethodName: BeanToString
     * @Author: YanBingHao
     * @param: [obj]
     * @return: java.lang.String
     */
    public static String BeanToString(Object obj) {
        if (obj == null) {
            return null;
        }
        ToStringBuilder builder = new ToStringBuilder(obj, ToStringStyle.MULTI_LINE_STYLE);
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {// 得到该对象的属性
                String key = property.getName();// 得到属性名称
                if (!key.equals("class")) {// 过滤class属性
                    Method getter = property.getReadMethod();// 得到property对应的getter方法
                    Object value = getter.invoke(obj);// 得到属性值
                    builder.append(key, value);// map键值对赋值
                }
            }
        } catch (Exception e) {
            logger.error("BeanToString Error ", e.getMessage());
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * @Title: 输出类的set方法
     * @MethodName: getBeanWriteMethod
     * @Author: YanBingHao
     * @param: [clazz]
     * @return: void
     */
    public static String getBeanWriteMethod(Class<?> clazz) {
        if (clazz == null) {
            logger.error("clazz is null");
        }
        String className = clazz.getCanonicalName().substring(clazz.getCanonicalName().lastIndexOf(".") + 1).toLowerCase();
        StringBuffer stringBuffer = new StringBuffer();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {// 得到该对象的属性
                String key = property.getName();// 得到属性名称
                if (!"class".equals(key)
                        && !"SCHEMA".equals(key)
                        && !"dir".equals(key)
                        && !"page".equals(key)
                        && !"size".equals(key)
                        && !"sort".equals(key)) {// 过滤class属性
                    if (property.getWriteMethod() != null) {
                        String setName = property.getWriteMethod().getName();// 得到property对应的setter方法名称
                        stringBuffer.append(className + "." + setName + "(" + key + ");\n");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("getBeanWriteMethod Error ", e.getMessage());
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

}
