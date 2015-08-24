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
            mail.setTo("281023005@qq.com");// ������
            mail.setFrom("601097836@qq.com");// ������
            mail.setSubject("zq love lmm");// ����
            mail.setText("I am test spring mail ,love you ,what are you doing");// �ʼ�����
            javaMailSender.send(mail);
            System.out.println("�������");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    public boolean sendHtmlMail() throws javax.mail.MessagingException {
        javax.mail.internet.MimeMessage mm = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mm, true, "utf-8");
        try {
            helper.setTo("281023005@qq.com");// ������
            helper.setFrom("601097836@qq.com");// ������
            helper.setSubject("�ʼ����� mess character");// ����
            helper.setText("<html><head></head><body><h1><a href='http://www.baidu.com'>hello!!lmm click me to baidu</a></h1></body></html>",true);// �ڶ������������͵���������html
            javaMailSender.send(mm);
            System.out.println("�������");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean sendFileMail() throws javax.mail.MessagingException {
        MimeMessage mm = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mm, true, "utf-8");
        try {
            helper.setTo("281023005@qq.com");// ������
            helper.setFrom("601097836@qq.com");// ������
            helper.setSubject("�ʼ����� mess character");// ����
            helper.setText("�ʼ����� content with attachment");// �ʼ�����

            //��������ļ�
            ClassPathResource in = new ClassPathResource("lmm.txt");
            ClassPathResource in2 = new ClassPathResource("attachment.txt");

            //MimeUtility.encodeWord()����������ļ���Ϊ��������
            helper.addAttachment(MimeUtility.encodeWord(in.getFilename()), in);
            helper.addAttachment(MimeUtility.encodeWord(in2.getFilename()), in2);

            javaMailSender.send(mm);
            System.out.println("�������");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /*
     * check �ʼ�
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
