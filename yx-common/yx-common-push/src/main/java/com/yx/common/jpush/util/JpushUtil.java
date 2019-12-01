package com.yx.common.jpush.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSON;
import com.yx.common.jpush.MsgTypeEnum;
import com.yx.common.jpush.configuration.JpushFactory;
import com.yx.common.jpush.properties.JpushProperties;
import com.yx.common.utils.Collections3;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class JpushUtil {

    protected static final Logger LOG = LoggerFactory.getLogger(JpushUtil.class);

    //----------------------推送对象构建start------------------------------


    /**
     * 构建推送对象: 通知
     * @param alert  通知内容
     * @param title  通知标题
     * @param extrasMap 参数
     * @return
     */
    public static PushPayload buildPushObject_android_and_ios_alert(String alert,String title,Map<String, String> extrasMap) {
        if(extrasMap==null){
            extrasMap=new HashMap<>();
        }
        return PushPayload
                .newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(
                        Notification
                                .newBuilder()
                                .setAlert(alert)
                                .addPlatformNotification(
                                        AndroidNotification.newBuilder() .setTitle(title).addExtras(extrasMap).build()
                                )
                                .addPlatformNotification(
                                        IosNotification.newBuilder().incrBadge(1).addExtras(extrasMap).build()
                                )
                         .build()
                ).build();
    }

    /**
     * 构建推送对象: 通知 （可以指定别名和标签）
     * @param alert 通知内容
     * @param title 通知标题
     * @param extrasMap 参数
     * @param alias  别名
     * @param tag  标签
     * @return
     */
    public static PushPayload buildPushObject_android_and_ios_alert_alias_tag(String alert, String title, Map<String, String> extrasMap, Collection<String> alias, Collection<String> tag) {
        if(extrasMap==null){
            extrasMap=new HashMap<>();
        }
        return PushPayload
                .newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(!Collections3.isEmpty(alias)?Audience.alias(alias):Audience.all())
                //.setAudience(!Collections3.isEmpty(tag)?Audience.tag(tag):Audience.all())
                .setNotification(
                        Notification
                                .newBuilder()
                                .setAlert(alert)
                                .addPlatformNotification(
                                        AndroidNotification.newBuilder() .setTitle(title).addExtras(extrasMap).build())
                                .addPlatformNotification(
                                        IosNotification.newBuilder().incrBadge(1).addExtras(extrasMap).build())
                                .build()).build();
    }

    /**
     * 给指定用户发送通知
     * @param alert
     * @param title
     * @param extrasMap
     * @param registrationId
     * @return
     */
    public static PushPayload buildPushObject_android_and_ios_alert_registrationid(String alert,String title,Map<String, String> extrasMap,String registrationId) {
        if(extrasMap==null){
            extrasMap=new HashMap<>();
        }
        return PushPayload
                .newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.registrationId(registrationId))
                .setNotification(
                        Notification
                                .newBuilder()
                                .setAlert(alert)
                                .addPlatformNotification(
                                        AndroidNotification.newBuilder() .setTitle(title).addExtras(extrasMap).build())
                                .addPlatformNotification(
                                        IosNotification.newBuilder().incrBadge(1).addExtras(extrasMap).build())
                                .build()).build();
    }

    /**
     * 构建推送对象：通知+消息
     * 平台是 ios+android，推送内容同时包括通知与消息 - 通知信息是 ALERT，角标数字为 5，通知声音为 "happy"，并且附加字段 from = "JPush"；消息内容是 MSG_CONTENT。通知是 APNs 推送通道的，消息是 JPush 应用内消息通道的。APNs 的推送环境是“生产”（如果不显式设置的话，Library 会默认指定为开发）
     * @return
     */
    public static PushPayload buildPushObject_android_and_ios_alertWithExtrasAndMessage(String alert,String title,String msgContent,Map<String, String> extrasMap ) {
        if(extrasMap==null){
            extrasMap=new HashMap<>();
        }
        return PushPayload
                .newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(
                        Notification
                                .newBuilder()
                                .setAlert(alert)
                                .addPlatformNotification(
                                        AndroidNotification.newBuilder() .setTitle(title).addExtras(extrasMap).build())
                                .addPlatformNotification(
                                        IosNotification.newBuilder().setBadge(5).setSound("happy").addExtras(extrasMap).build())

                                .build())
                .setMessage(Message.content(msgContent))
                .setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
    }


    /**
     * 构建推送对象：消息
     * 平台是 Andorid 与 iOS，推送内容是 - 内容为 MSG_CONTENT 的消息，并且附加参数。
     * @param title 标题
     * @param msgContent  消息内容
     * @param extrasMap  参数
     * @return
     */
    public static PushPayload buildPushObject_android_and_ios_messageWithExtras(String title,String msgContent,Map<String, String> extrasMap ) {
        if(extrasMap==null){
            extrasMap=new HashMap<>();
        }
        return PushPayload
                .newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setMessage(
                        Message.newBuilder().setTitle(title).setMsgContent(msgContent).addExtras(extrasMap)
                                .build()).build();
    }

    /**
     * 给指定用户发送自定义消息
     * @param title
     * @param msgContent
     * @param extrasMap
     * @return
     */
    public static PushPayload buildPushObject_android_and_ios_messageWithExtras_registrationId(String title,String msgContent,Map<String, String> extrasMap,String  registrationId) {
        if(extrasMap==null){
            extrasMap=new HashMap<>();
        }
        return PushPayload
                .newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.registrationId(registrationId))
                .setMessage(
                        Message.newBuilder().setTitle(title).setMsgContent(msgContent).addExtras(extrasMap)
                                .build()).build();
    }


    /**
     * 构建推送对象：消息
     * 平台是 Andorid 与 iOS，推送内容是 - 内容为 MSG_CONTENT 的消息，并且附加参数。
     * @param title 标题
     * @param msgContent  内容
     * @param extrasMap   参数
     * @param alias 别名
     * @param tag   标签
     * @return
     */
    public static PushPayload buildPushObject_android_and_ios_messageWithExtras_alias_tag(String title,String msgContent,Map<String, String> extrasMap,Collection<String> alias,Collection<String> tag ) {
        if(extrasMap==null){
            extrasMap=new HashMap<>();
        }
        return PushPayload
                .newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(!Collections3.isEmpty(alias)?Audience.alias(alias):Audience.all())
                .setAudience(!Collections3.isEmpty(tag)?Audience.tag(tag):Audience.all())
                .setMessage(
                        Message.newBuilder().setTitle(title).setMsgContent(msgContent).addExtras(extrasMap)
                                .build()).build();
    }
    //-----------------构建推送对象end-----------------------


    //-----------------以下为测试----------------------------
    // 使用 NettyHttpClient 异步接口发送请求
    public static void sendPushWithCallback(String alias,String alert) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        JpushProperties properties = JpushFactory.getProperties();
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(
                properties.getAppKey(), properties.getMasterSecret()), null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload = buildPushObject_android_and_ios_alert("通知内容","通知标题",null);
            client.sendRequest(HttpMethod.POST, payload.toString(), uri,
                    new NettyHttpClient.BaseCallback() {
                        @Override
                        public void onSucceed(ResponseWrapper responseWrapper) {
                            LOG.info("Got result: " + responseWrapper.responseContent);
                        }
                    });

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }finally {
            client.close();
        }
    }



    public static void SendPush(String alert,String title,Map<String, String> extrasMap,String alias) {
        if(extrasMap==null){
            extrasMap=new HashMap<>();
        }

        ClientConfig clientConfig = ClientConfig.getInstance();
        JpushProperties properties = JpushFactory.getProperties();
        System.out.println("jsonStr:"+JSON.toJSONString(properties));
        JPushClient jpushClient = new JPushClient(properties.getMasterSecret(), properties.getAppKey(), null, clientConfig);
        String authCode = ServiceHelper.getBasicAuthorization(properties.getAppKey(), properties.getMasterSecret());
        // Here you can use NativeHttpClient or NettyHttpClient.
        NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
        // Call setHttpClient to set httpClient,
        // If you don't invoke this method, default httpClient will use
        // NativeHttpClient.
        jpushClient.getPushClient().setHttpClient(httpClient);

        // For push, all you need do is to build PushPayload object.
//        PushPayload payload = buildPushObject_android_and_ios_alert(alert,title,extrasMap);
//        PushPayload payload = buildPushObject_android_and_ios_alert_registrationid(alert,title,extrasMap,registrationId);
        List<String> aliasList=new ArrayList<>();
        aliasList.add(alias);
        PushPayload payload = buildPushObject_android_and_ios_alert_alias_tag(alert, title, extrasMap, aliasList, null);

//       PushPayload payload = buildPushObject_android_and_ios_alertWithExtrasAndMessage("通知内容", "通知标题", "消息内容",null);

//        PushPayload payload = buildPushObject_android_and_ios_messageWithExtras("消息标题", "消息内容", null);

//        PushPayload payload = buildPushObject_android_and_ios_messageWithExtras_alias_tag("消息标题", "消息内容", null, null, null);

        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
            // 如果使用 NettyHttpClient，需要手动调用 close 方法退出进程
            // If uses NettyHttpClient, call close when finished sending
            // request, otherwise process will not exit.
            // jpushClient.close();
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        }finally {
            jpushClient.close();
        }
    }





    public static void main(String[] args) {
        Map<String, String> extrasMap=new HashMap<>();
        extrasMap.put("msgType", MsgTypeEnum.MSG_TYPE6.getStatus());
        extrasMap.put("url","http://wx-test.yinxinsirencaihang.com/platinum?activityId=2");
        SendPush("你有一份情人节礼物","通知",extrasMap,"18811537937");
    }


}
