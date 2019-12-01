package com.yx.common.mq.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * MQ服务类
 *
 * @author TangHuaLiang
 * @date 17/11/24 15:16:50
 */
@Service("mqService")
@Slf4j
public class MqService {
    @Autowired()
    /**
     * 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
     *
     * @author TangHuaLiang
     * @date 18/1/1 15:19:22
     */
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 发送消息，destination是发送到的队列，message是待发送的消息
     *
     * @author TangHuaLiang
     * @date 18/1/1 15:19:41
     */
    public void send(String queueName, final Object object) {
        send(new ActiveMQQueue(queueName), object);
    }

    public void send(Destination destination, final Object object) {
        String message = object instanceof String ? object.toString() : JSONObject.toJSONString(object);
        try {
            jmsMessagingTemplate.convertAndSend(destination, message);
        } catch (MessagingException e) {
            log.error(e.toString());
        }
    }
}
