package com.zhengshouzi.myweb.beans;

/**
 * Created by zhengshouzi on 2015/8/24.
 */
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;


public class MailBean implements Serializable {

    private String from;
    private String fromName;
    private String[] toEmails;
    private String toEmail;
    private String subject;
private String Text;
    private Map data ;          //ÓÊ¼þÊý¾Ý


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

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
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

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    @Override
    public String toString() {
        return "MailBean{" +
                "from='" + from + '\'' +
                ", fromName='" + fromName + '\'' +
                ", toEmails=" + Arrays.toString(toEmails) +
                ", toEmail='" + toEmail + '\'' +
                ", subject='" + subject + '\'' +
                ", Text='" + Text + '\'' +
                ", data=" + data +
                '}';
    }
}