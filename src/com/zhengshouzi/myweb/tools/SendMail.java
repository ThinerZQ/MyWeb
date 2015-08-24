package com.zhengshouzi.myweb.tools;

/**
 * Created by zhengshouzi on 2015/8/24.
 */
        import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
        import com.zhengshouzi.myweb.beans.MailBean;
        import org.springframework.core.io.ClassPathResource;
        import org.springframework.mail.SimpleMailMessage;
        import org.springframework.mail.javamail.JavaMailSender;
        import org.springframework.mail.javamail.MimeMessageHelper;
        import org.springframework.messaging.MessagingException;
        import javax.mail.internet.MimeMessage;
        import javax.annotation.Resource;


/**
 *
 * @author Qixuan.Chen
 */
public class SendMail {

    @Resource(name = "mailSender")
    private JavaMailSender javaMailSender;

    public boolean sendMail() throws MessagingException {

        SimpleMailMessage mail = new SimpleMailMessage();
        try {
            mail.setTo("281023005@qq.com");// 接受者
            mail.setFrom("601097836@qq.com");// 发送者
            mail.setSubject("zq love lmm");// 主题
            mail.setText("I am test spring mail ,love you ,what are you doing");// 邮件内容
            javaMailSender.send(mail);
            System.out.println("发送完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    public boolean sendHtmlMail() throws javax.mail.MessagingException {
        javax.mail.internet.MimeMessage mm = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mm, true, "utf-8");
        try {
            helper.setTo("281023005@qq.com");// 接受者
            helper.setFrom("601097836@qq.com");// 发送者
            helper.setSubject("邮件主题 mess character");// 主题
            helper.setText("<html><head></head><body><h1><a href='http://www.baidu.com'>hello!!lmm click me to baidu</a></h1></body></html>",true);// 第二个参数代表发送的是正文是html
            javaMailSender.send(mm);
            System.out.println("发送完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean sendFileMail() throws javax.mail.MessagingException {
        MimeMessage mm = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mm, true, "utf-8");
        try {
            helper.setTo("281023005@qq.com");// 接受者
            helper.setFrom("601097836@qq.com");// 发送者
            helper.setSubject("邮件主题 mess character");// 主题
            helper.setText("邮件内容 content with attachment");// 邮件内容

            //多个附件文件
            ClassPathResource in = new ClassPathResource("lmm.txt");
            ClassPathResource in2 = new ClassPathResource("attachment.txt");

            //MimeUtility.encodeWord()解决附件的文件名为中文问题
            helper.addAttachment(MimeUtility.encodeWord(in.getFilename()), in);
            helper.addAttachment(MimeUtility.encodeWord(in2.getFilename()), in2);

            javaMailSender.send(mm);
            System.out.println("发送完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /*
     * check 邮件
     */
    private boolean checkMailBean(MailBean mailBean){
        if (mailBean == null) {
            return false;
        }
        if (mailBean.getSubject() == null) {
            return false;
        }
        if (mailBean.getToEmails() == null) {
            return false;
        }
        if (mailBean.getTemplate() == null) {
            return false;
        }
        return true;
    }




}
