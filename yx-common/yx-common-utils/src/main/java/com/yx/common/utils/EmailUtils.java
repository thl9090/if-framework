package com.yx.common.utils;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发送电子邮件
 */
public class EmailUtils {

    private final static Logger logger = LoggerFactory.getLogger(EmailUtils.class);

    /**
     * 发送邮件
     * @param toAddress 接收地址
     * @param subject 标题
     * @param content 内容
     * @return
     */
    public static boolean send(String toAddress, String subject, String content) {

//        String fromAddress = "webmaster@yinxincaihang.com";
//        String fromPassword = "QWERty1234";
//        String fromHostName = "smtp.exmail.qq.com";
//        String sslOnConnect = "true";
//        String sslSmtpPort = "465";
        String fromAddress = "thl9090@163.com";
        String fromPassword = "yourpwd";
        String fromHostName = "smtp.163.com";
        String sslOnConnect = "true";
        String sslSmtpPort = "994";
        return send(fromAddress, fromPassword, fromHostName, sslOnConnect, sslSmtpPort, toAddress, subject, content);
    }

    /**
     * 发送邮件
     * @param toAddress 接收地址
     * @param subject 标题
     * @param content 内容
     * @return
     */
    public static boolean send(String toAddress, String subject, String content,String fileName) {

        String fromAddress = "webmaster@yourcompany.com";
        String fromPassword = "yourpwd";
        String fromHostName = "smtp.exmail.qq.com";
        String sslOnConnect = "true";
        String sslSmtpPort = "465";
       /* String fromAddress = "thl9090@163.com";
        String fromPassword = "yourpwd";
        String fromHostName = "smtp.163.com";
        String sslOnConnect = "true";
        String sslSmtpPort = "994";*/
        return send(fromAddress, fromPassword, fromHostName, sslOnConnect, sslSmtpPort, toAddress, subject, content,fileName);
    }

    /**
     * 发送邮件
     * @param toAddress 接收地址
     * @param subject 标题
     * @param content 内容
     * @return
     */
    public static boolean send(String fromAddress, String fromPassword, String fromHostName,
                               String sslOnConnect, String sslSmtpPort, String toAddress, String subject, String content) {
        try {
            HtmlEmail htmlEmail = new HtmlEmail();
            // 发送地址
            htmlEmail.setFrom(fromAddress);
            // 密码校验
            htmlEmail.setAuthentication(fromAddress, fromPassword);
            // 发送服务器协议
            htmlEmail.setHostName(fromHostName);

            // SSL
            if ("true".equals(sslOnConnect)) {
                htmlEmail.setSSLOnConnect(true);
                htmlEmail.setSslSmtpPort(sslSmtpPort);
            }

            // 接收地址
            htmlEmail.addTo(toAddress);

            // 标题
            htmlEmail.setSubject(subject);
            // 内容
            htmlEmail.setMsg(content);

            // 其他信息
            htmlEmail.setCharset("utf-8");

            // 发送
            htmlEmail.send();
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return false;
    }


    /**
     * 发送带附件的邮件
     * @param fromAddress
     * @param fromPassword
     * @param fromHostName
     * @param sslOnConnect
     * @param sslSmtpPort
     * @param toAddress
     * @param subject
     * @param content
     * @param fileName 附件地址
     * @return
     */
    public static boolean send(String fromAddress, String fromPassword, String fromHostName,
                               String sslOnConnect, String sslSmtpPort, String toAddress, String subject, String content,String fileName ) {
        try {
            HtmlEmail htmlEmail = new HtmlEmail();
            // 发送地址
            htmlEmail.setFrom(fromAddress);
            // 密码校验
            htmlEmail.setAuthentication(fromAddress, fromPassword);
            // 发送服务器协议
            htmlEmail.setHostName(fromHostName);

            // SSL
            if ("true".equals(sslOnConnect)) {
                htmlEmail.setSSLOnConnect(true);
                htmlEmail.setSslSmtpPort(sslSmtpPort);
            }

            // 接收地址
            htmlEmail.addTo(toAddress);

            // 标题
            htmlEmail.setSubject(subject);
            // 内容
            htmlEmail.setMsg(content);

            // 其他信息
            htmlEmail.setCharset("utf-8");

            EmailAttachment attachment  = new EmailAttachment();
            attachment.setPath(fileName);
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            htmlEmail.attach( attachment );
            // 发送
            htmlEmail.send();
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return false;
    }



}
