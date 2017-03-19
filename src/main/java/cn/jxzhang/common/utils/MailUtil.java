package cn.jxzhang.common.utils;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Created on 2017-03-17 11:48
 * <p>Title:       MailUtil</p>
 * <p>Description: [Description]</p>
 *
 * @author <a href="mailto:zhangjiaxing@ultrapower.com.cn">zhangjiaxing</a>
 * @version 1.0
 */
public class MailUtil {

    private MailUtil() { /* can not be instantiated*/ }

    static {
        Properties props = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.properties");
        try {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        session = Session.getDefaultInstance(props);
        addressor = props.getProperty("SEND_EMAIL_ACCOUNT");
        password = props.getProperty("SEND_EMAIL_PASSWORD");
        personal = props.getProperty("SEND_EMAIL_ACCOUNT_NAME");
    }

    /** The Session class represents a mail session and is not subclassed. */
    private static Session session;

    /** 发件人地址 */
    private static String addressor;

    /** 发件箱密码 */
    private static String password;

    /** 发件人姓名 */
    private static String personal;

    /**
     * 发送邮件
     *
     * @param recipient 收件人
     * @param subject   邮件主题
     * @param content   邮件内容
     * @throws Exception 发送邮件失败将抛出异常
     */
    public static void sendEmail(String recipient, String subject, String content) throws Exception {
        MimeMessage message = createMimeMessage(session, recipient, subject, content);
        Transport transport = session.getTransport();
        transport.connect(addressor, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    /**
     * @param session   会话
     * @param recipient 收件人
     * @param subject   邮件主题
     * @param content   邮件内容
     * @return
     * @throws Exception
     */
    private static MimeMessage createMimeMessage(Session session, String recipient, String subject, String content) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(addressor, personal, "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient, recipient, "UTF-8"));
        message.setSubject(subject, "UTF-8");
        message.setContent(content, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
}
