package com.zhengshouzi.myweb.beans;

/**
 * Created by zhengshouzi on 2015/8/24.
 */
import java.io.Serializable;
import java.util.Map;


public class MailBean implements Serializable {

    private String from;
    private String fromName;
    private String[] toEmails;

    private String subject;

    private Map data ;          //邮件数据
    private String template;    //邮件模板

    public String getFrom() {
        return from;
    }

    public String getFromName() {
        return fromName;
    }

    public String[] getToEmails() {
        return toEmails;
    }

    public String getSubject() {
        return subject;
    }

    public Map getData() {
        return data;
    }

    public String getTemplate() {
        return template;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public void setToEmails(String[] toEmails) {
        this.toEmails = toEmails;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}