package com.yx.common.jpush.service;

import java.util.List;
import java.util.Map;

/**
 * 极光推送服务接口
 *
 * @author TangHuaLiang
 * @date 2019-02-23
 **/
public interface JpushService {

    /**
     * 发送通知(所有用户)
     * 即指在手机的通知栏（状态栏）上会显示的一条通知信息
     * @param title  标题
     * @param alert 内容
     * @param extrasMap  参数  可以为null
     */
    void sendNotificationToAll(String title, String alert, Map<String, String> extrasMap) throws Exception;

    /**
     * 发送通知
     * 即指在手机的通知栏（状态栏）上会显示的一条通知信息
     * @param title  标题
     * @param alert  内容
     * @param extrasMap 参数  可以为null
     * @param phones 手机号码集合      可以为null
     * @param tag 标签        可以为null
     *
     * @return
     */
    void sendNotification(String title, String alert, Map<String, String> extrasMap, List<String> phones, List<String> tag) throws Exception;

    /**
     * 给指定用户发送通知
     * @param title
     * @param alert
     * @param extrasMap
     * @param phone 手机号
     */
    void sendNotification(String title, String alert, Map<String, String> extrasMap, Long phone) throws Exception;


    /**
     * 发送自定义消息(所有用户)
     * 自定义消息不是通知，所以不会被 SDK 展示到通知栏上。其内容完全由开发者自己定义。 自定义消息主要用于应用的内部业务逻辑。一条自定义消息推送过来，有可能没有任何界面显示。
     * @param title 标题
     * @param msgContent 内容
     * @param extrasMap 参数 可以为null
     */
    void sendMessageToAll(String title, String msgContent, Map<String, String> extrasMap) throws Exception;

    /**
     * 发送自定义消息
     * 自定义消息不是通知，所以不会被 SDK 展示到通知栏上。其内容完全由开发者自己定义。 自定义消息主要用于应用的内部业务逻辑。一条自定义消息推送过来，有可能没有任何界面显示。
     * @param title 标题
     * @param msgContent 内容
     * @param extrasMap 参数 可以为null
     * @param phones 手机号码集合   可以为null
     * @param tag  标签   可以为null
     *
     * @return
     */
    void sendMessage(String title, String msgContent, Map<String, String> extrasMap, List<String> phones,List<String> tag) throws Exception;

    /**
     * 给指定用户发送自定义消息
     * @param title
     * @param msgContent
     * @param extrasMap
     * @param phone 手机号码
     */
    void sendMessage(String title, String msgContent, Map<String, String> extrasMap, Long phone) throws Exception;


    /**
     * 异步发送通知
     * @param title
     * @param alert
     * @param extrasMap
     */
    void sendPushNotificationWithCallback(String title, String alert, Map<String, String> extrasMap) throws Exception;

    /**
     * 异步发送 自定义消息
     * @param title
     * @param msgCsontent
     * @param extrasMap
     */
    void sendPushMessageWithCallback(String title, String msgCsontent, Map<String, String> extrasMap) throws Exception;





}
