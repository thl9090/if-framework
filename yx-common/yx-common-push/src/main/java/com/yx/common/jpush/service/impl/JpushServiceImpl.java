package com.yx.common.jpush.service.impl;

import cn.afterturn.easypoi.excel.ExcelXorHtmlUtil;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.jpush.MsgTypeEnum;
import com.yx.common.jpush.properties.JpushProperties;
import com.yx.common.jpush.service.JpushService;
import com.yx.common.jpush.util.JpushUtil;
import com.yx.common.utils.Collections3;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 极光推送服务类
 *
 * @author TangHuaLiang
 * @date 2019-02-23
 */
public class JpushServiceImpl implements JpushService {

    protected static final Logger LOG = LoggerFactory.getLogger(JpushServiceImpl.class);
    public static long sendCount = 0;

    private JpushProperties jpushProperties;
    private JPushClient jpushClient;

    public JpushServiceImpl(JpushProperties jpushProperties) {
        this.jpushProperties = jpushProperties;
        // 初始化
        init();
    }

    private void init() {
//        jpushClient = new JPushClient(jpushProperties.getMasterSecret(),jpushProperties.getAppKey());
    }


    @Override
    public void sendNotificationToAll(String title, String alert, Map<String, String> extrasMap) throws Exception{
        if(StringUtils.isBlank(title)){
            throw new BusinessException("请求参数title为空！");
        }
        if(StringUtils.isBlank(alert)){
            throw new BusinessException("请求参数alert为空！");
        }
        if(extrasMap!=null&&extrasMap.get("msgType")==null){
            extrasMap.put("msgType", MsgTypeEnum.MSG_TYPE2.getStatus());
        }

        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(jpushProperties.getMasterSecret(), jpushProperties.getAppKey(), null, clientConfig);
        String authCode = ServiceHelper.getBasicAuthorization(jpushProperties.getAppKey(), jpushProperties.getMasterSecret());
        // Here you can use NativeHttpClient or NettyHttpClient.
        NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
        // Call setHttpClient to set httpClient,
        // If you don't invoke this method, default httpClient will use
        // NativeHttpClient.
        jpushClient.getPushClient().setHttpClient(httpClient);

        PushPayload payload = JpushUtil.buildPushObject_android_and_ios_alert(alert,title,extrasMap);

        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
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

    @Override
    public void sendNotification(String title, String alert, Map<String, String> extrasMap, List<String> phones, List<String> tags) throws Exception{
        if(StringUtils.isBlank(title)){
            throw new BusinessException("请求参数title为空！");
        }
        if(StringUtils.isBlank(alert)){
            throw new BusinessException("请求参数alert为空！");
        }
        if(extrasMap!=null&&extrasMap.get("msgType")==null){
            extrasMap.put("msgType", MsgTypeEnum.MSG_TYPE2.getStatus());
        }


       if(!Collections3.isEmpty(phones)&&phones.size()>1000){
            //别名一次最多推送1000个
           sendPushesByAliasForNotifaction(alert,title,extrasMap,phones);

       }else if(!Collections3.isEmpty(tags)&&tags.size()>20){
            //标签一次最多20个
           sendPushesByTagsForNotifaction(alert,title,extrasMap,tags);
       }else{
           ClientConfig clientConfig = ClientConfig.getInstance();
           JPushClient jpushClient = new JPushClient(jpushProperties.getMasterSecret(), jpushProperties.getAppKey(), null, clientConfig);
           String authCode = ServiceHelper.getBasicAuthorization(jpushProperties.getAppKey(), jpushProperties.getMasterSecret());
           // Here you can use NativeHttpClient or NettyHttpClient.
           NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
           // Call setHttpClient to set httpClient,
           // If you don't invoke this method, default httpClient will use
           // NativeHttpClient.
           jpushClient.getPushClient().setHttpClient(httpClient);

           PushPayload payload = JpushUtil.buildPushObject_android_and_ios_alert_alias_tag(alert,title,extrasMap,phones,tags);

           try {
               PushResult result = jpushClient.sendPush(payload);
               LOG.info("Got result - " + result);
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


    }


    @Override
    public void sendNotification(String title, String alert, Map<String, String> extrasMap, Long phone) throws Exception{
        if(StringUtils.isBlank(title)){
            throw new BusinessException("请求参数title为空！");
        }
        if(StringUtils.isBlank(alert)){
            throw new BusinessException("请求参数alert为空！");
        }
        if(extrasMap!=null&&extrasMap.get("msgType")==null){
            extrasMap.put("msgType", MsgTypeEnum.MSG_TYPE2.getStatus());
        }


        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(jpushProperties.getMasterSecret(), jpushProperties.getAppKey(), null, clientConfig);
        String authCode = ServiceHelper.getBasicAuthorization(jpushProperties.getAppKey(), jpushProperties.getMasterSecret());
        // Here you can use NativeHttpClient or NettyHttpClient.
        NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
        // Call setHttpClient to set httpClient,
        // If you don't invoke this method, default httpClient will use
        // NativeHttpClient.
        jpushClient.getPushClient().setHttpClient(httpClient);
        List<String> phones=new ArrayList<>();
        phones.add(phone.toString());

        PushPayload payload = JpushUtil.buildPushObject_android_and_ios_alert_alias_tag(alert,title,extrasMap,phones,null);

        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
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

    @Override
    public void sendMessageToAll(String title, String msgContent, Map<String, String> extrasMap)throws Exception {
        if(StringUtils.isBlank(title)){
            throw new BusinessException("请求参数title为空！");
        }
        if(StringUtils.isBlank(msgContent)){
            throw new BusinessException("请求参数msgContent为空！");
        }


        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(jpushProperties.getMasterSecret(), jpushProperties.getAppKey(), null, clientConfig);
        String authCode = ServiceHelper.getBasicAuthorization(jpushProperties.getAppKey(), jpushProperties.getMasterSecret());
        // Here you can use NativeHttpClient or NettyHttpClient.
        NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
        // Call setHttpClient to set httpClient,
        // If you don't invoke this method, default httpClient will use
        // NativeHttpClient.
        jpushClient.getPushClient().setHttpClient(httpClient);

        PushPayload payload = JpushUtil.buildPushObject_android_and_ios_messageWithExtras(title,msgContent,extrasMap);

        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
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

    @Override
    public void sendMessage(String title, String msgContent, Map<String, String> extrasMap, List<String> phones, List<String> tags) throws Exception{
        if(StringUtils.isBlank(title)){
            throw new BusinessException("请求参数title为空！");
        }
        if(StringUtils.isBlank(msgContent)){
            throw new BusinessException("请求参数msgContent为空！");
        }
        if(extrasMap!=null&&extrasMap.get("msgType")==null){
            extrasMap.put("msgType", MsgTypeEnum.MSG_TYPE2.getStatus());
        }

        if(!Collections3.isEmpty(phones)&&phones.size()>1000){
            //别名一次最多推送1000个
            sendPushesByAliasForMessage(title,msgContent,extrasMap,phones);

        }else if(!Collections3.isEmpty(tags)&&tags.size()>20){
            //标签一次最多20个
            sendPushesByTagsForNotifaction(title,msgContent,extrasMap,tags);
        }else{
            ClientConfig clientConfig = ClientConfig.getInstance();
            JPushClient jpushClient = new JPushClient(jpushProperties.getMasterSecret(), jpushProperties.getAppKey(), null, clientConfig);
            String authCode = ServiceHelper.getBasicAuthorization(jpushProperties.getAppKey(), jpushProperties.getMasterSecret());
            // Here you can use NativeHttpClient or NettyHttpClient.
            NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
            // Call setHttpClient to set httpClient,
            // If you don't invoke this method, default httpClient will use
            // NativeHttpClient.
            jpushClient.getPushClient().setHttpClient(httpClient);

            PushPayload payload = JpushUtil.buildPushObject_android_and_ios_messageWithExtras_alias_tag(title,msgContent,extrasMap,phones,tags);

            try {
                PushResult result = jpushClient.sendPush(payload);
                LOG.info("Got result - " + result);
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

    }

    @Override
    public void sendMessage(String title, String msgContent, Map<String, String> extrasMap, Long phone) throws Exception{
        if(StringUtils.isBlank(title)){
            throw new BusinessException("请求参数title为空！");
        }
        if(StringUtils.isBlank(msgContent)){
            throw new BusinessException("请求参数msgContent为空！");
        }
        if(phone==null){
            throw new BusinessException("请求参数phone为空！");
        }

        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(jpushProperties.getMasterSecret(), jpushProperties.getAppKey(), null, clientConfig);
        String authCode = ServiceHelper.getBasicAuthorization(jpushProperties.getAppKey(), jpushProperties.getMasterSecret());
        // Here you can use NativeHttpClient or NettyHttpClient.
        NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
        // Call setHttpClient to set httpClient,
        // If you don't invoke this method, default httpClient will use
        // NativeHttpClient.
        jpushClient.getPushClient().setHttpClient(httpClient);

        List<String> phones=new ArrayList<>();
        phones.add(String.valueOf(phone));
        PushPayload payload = JpushUtil.buildPushObject_android_and_ios_messageWithExtras_alias_tag(title,msgContent,extrasMap,phones,null);

        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
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

    @Override
    public void sendPushNotificationWithCallback(String title, String alert, Map<String, String> extrasMap) throws Exception{
        if(StringUtils.isBlank(title)){
            throw new BusinessException("请求参数title为空！");
        }
        if(StringUtils.isBlank(alert)){
            throw new BusinessException("请求参数alert为空！");
        }

        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(
                jpushProperties.getAppKey(), jpushProperties.getMasterSecret()), null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload = JpushUtil.buildPushObject_android_and_ios_alert(title,alert,extrasMap);
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

    /**
     * 异步发送自定义消息
     * @param title
     * @param msgContent
     * @param extrasMap
     */
    @Override
    public void sendPushMessageWithCallback(String title, String msgContent, Map<String, String> extrasMap) throws Exception{
        if(StringUtils.isBlank(title)){
            throw new BusinessException("请求参数title为空！");
        }
        if(StringUtils.isBlank(msgContent)){
            throw new BusinessException("请求参数msgContent为空！");
        }

        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(
                jpushProperties.getAppKey(), jpushProperties.getMasterSecret()), null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload = JpushUtil.buildPushObject_android_and_ios_messageWithExtras(title,msgContent,extrasMap);
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


    //===================批量发送方法start====================================
    /**
     * * 根据别名分批次发送通知
     * 别名。一次推送最多 1000 个
     * @param alert
     * @param title
     * @param extrasMap
     * @param alias
     */
    private void sendPushesByAliasForNotifaction(String alert,String title,Map<String,String> extrasMap,List<String> alias){
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(jpushProperties.getMasterSecret(), jpushProperties.getAppKey(), null, clientConfig);
        String authCode = ServiceHelper.getBasicAuthorization(jpushProperties.getAppKey(), jpushProperties.getMasterSecret());
        // Here you can use NativeHttpClient or NettyHttpClient or ApacheHttpClient.
        NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
        // Call setHttpClient to set httpClient,
        jpushClient.getPushClient().setHttpClient(httpClient);
        List<String> newAlias=new ArrayList<>();
        int batchIndex=1;
        for(int i=0;i<alias.size();i++) {
            newAlias.add(alias.get(i));
            if(newAlias.size()==1000||i==alias.size()-1){
                batchIndex++;
                PushPayload payload = JpushUtil.buildPushObject_android_and_ios_alert_alias_tag(alert,title,extrasMap,newAlias,null);

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                            long start = System.currentTimeMillis();
                            try {
                                PushResult result = jpushClient.sendPush(payload);
                                LOG.info("Got result - " + result);

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
                            }

                            System.out.println("耗时" + (System.currentTimeMillis() - start) + "毫秒 sendCount:" + (++sendCount));
                        }
                };
                thread.start();
                newAlias.clear();

            }

        }
    }


    /**
     * * 根据别名分批次发送自定义消息
     * 别名。一次推送最多 1000 个
     *
     * @param title
     * @param msgContent
     * @param extrasMap
     * @param alias
     */
    private void sendPushesByAliasForMessage(String title,String msgContent,Map<String,String> extrasMap,List<String> alias){
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(jpushProperties.getMasterSecret(), jpushProperties.getAppKey(), null, clientConfig);
        String authCode = ServiceHelper.getBasicAuthorization(jpushProperties.getAppKey(), jpushProperties.getMasterSecret());
        // Here you can use NativeHttpClient or NettyHttpClient or ApacheHttpClient.
        NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
        // Call setHttpClient to set httpClient,
        jpushClient.getPushClient().setHttpClient(httpClient);
        List<String> newAlias=new ArrayList<>();
        int batchIndex=1;
        for(int i=0;i<alias.size();i++) {
            newAlias.add(alias.get(i));
            if(newAlias.size()==1000||i==alias.size()-1){
                batchIndex++;
                PushPayload payload = JpushUtil.buildPushObject_android_and_ios_messageWithExtras_alias_tag(title,msgContent,extrasMap,newAlias,null);

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        long start = System.currentTimeMillis();
                        try {
                            PushResult result = jpushClient.sendPush(payload);
                            LOG.info("Got result - " + result);

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
                        }

                        System.out.println("耗时" + (System.currentTimeMillis() - start) + "毫秒 sendCount:" + (++sendCount));
                    }
                };
                thread.start();
                newAlias.clear();

            }

        }
    }


    /**
     * 根据标签分批次发送通知
     * 标签一次最多推送20个
     * @param alert
     * @param title
     * @param extrasMap
     * @param alias
     */
    private void sendPushesByTagsForNotifaction(String alert,String title,Map<String,String> extrasMap,List<String> alias){
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(jpushProperties.getMasterSecret(), jpushProperties.getAppKey(), null, clientConfig);
        String authCode = ServiceHelper.getBasicAuthorization(jpushProperties.getAppKey(), jpushProperties.getMasterSecret());
        // Here you can use NativeHttpClient or NettyHttpClient or ApacheHttpClient.
        NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
        // Call setHttpClient to set httpClient,
        jpushClient.getPushClient().setHttpClient(httpClient);
        List<String> newTags=new ArrayList<>();

        int batchIndex=1;
        for(int i=0;i<alias.size();i++) {
            newTags.add(alias.get(i));
            if(newTags.size()==20||i==alias.size()-1){
                batchIndex++;
                PushPayload payload = JpushUtil.buildPushObject_android_and_ios_alert_alias_tag(alert,title,extrasMap,null,newTags);

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                            long start = System.currentTimeMillis();
                            try {
                                PushResult result = jpushClient.sendPush(payload);
                                LOG.info("Got result - " + result);

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
                            }

                            System.out.println("耗时" + (System.currentTimeMillis() - start) + "毫秒 sendCount:" + (++sendCount));
                    }
                };
                thread.start();
                newTags.clear();
            }

        }
    }


    /**
     * 根据标签分批次发送自定义消息
     * 标签一次最多推送20个
     * @param title
     * @param msgContent
     * @param extrasMap
     * @param alias
     */
    private void sendPushesByTagsForMessage(String title,String msgContent,Map<String,String> extrasMap,List<String> alias){
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(jpushProperties.getMasterSecret(), jpushProperties.getAppKey(), null, clientConfig);
        String authCode = ServiceHelper.getBasicAuthorization(jpushProperties.getAppKey(), jpushProperties.getMasterSecret());
        // Here you can use NativeHttpClient or NettyHttpClient or ApacheHttpClient.
        NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
        // Call setHttpClient to set httpClient,
        jpushClient.getPushClient().setHttpClient(httpClient);
        List<String> newTags=new ArrayList<>();

        int batchIndex=1;
        for(int i=0;i<alias.size();i++) {
            newTags.add(alias.get(i));
            if(newTags.size()==20||i==alias.size()-1){
                batchIndex++;
                PushPayload payload = JpushUtil.buildPushObject_android_and_ios_messageWithExtras_alias_tag(title,msgContent,extrasMap,null,newTags);

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        long start = System.currentTimeMillis();
                        try {
                            PushResult result = jpushClient.sendPush(payload);
                            LOG.info("Got result - " + result);

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
                        }

                        System.out.println("耗时" + (System.currentTimeMillis() - start) + "毫秒 sendCount:" + (++sendCount));
                    }
                };
                thread.start();
                newTags.clear();
            }

        }
    }


    //===================批量发送方法end====================================


    public static void main(String[] args) {
        int count=4000;
        System.out.println(count%1000);

    }


}
