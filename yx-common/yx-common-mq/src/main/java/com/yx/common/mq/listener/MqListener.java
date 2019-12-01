package com.yx.common.mq.listener;

import org.springframework.stereotype.Component;

/**
 * MQ接收队列监听
 *
 * @author TangHuaLiang
 * @date 18/1/1 15:12:13
 */
@Component
public class MqListener {
    private String text;
//    private int num = 1;

    //监听队列
//    @JmsListener(destination = "${spring.activemq.defaultQueueName}")//监听指定消息队列
//    @Async
//    public void receiveQueue(String text) throws Exception {
//        this.text = text;
//        System.out.println(text);
//    }

    //监听【缺省死信队列(Dead Letter Queue)】
//    @JmsListener(destination = "ActiveMQ.DLQ")//监听指定消息队列
//    @Async
//    public void receiveDLQ(String text) throws Exception {
//        this.text = text;
//        System.out.println(text);
//    }

//    //异常测试
//    @JmsListener(destination = "${spring.activemq.defaultQueueName}")
//    public void receiveQueue(TextMessage text) throws Exception {
//        System.out.println("重试次数"+num++);
//        System.out.println(Thread.currentThread().getName()+":Consumer收到的报文为:"+text.getText());
//        throw new Exception();
//    }

    public String receive() {
        return text;
    }
}
