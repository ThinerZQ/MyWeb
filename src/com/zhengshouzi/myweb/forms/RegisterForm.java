package com.zhengshouzi.myweb.forms;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhengshouzi on 2015/8/24.
 */
public class RegisterForm {
    private String email;
    private String password;
    private String status=String.valueOf(0);//¼¤»î×´Ì¬
    private String activatecode;//¼¤»îÂë
    private Timestamp registerTime;//×¢²áÊ±¼ä

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public String getStatus() {
        return status;
    }

    public String getActivatecode() {
        return activatecode;
    }

    public void setActivatecode(String activatecode) {
        this.activatecode = activatecode;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }




    public Date getLastActivateTime() {
        Calendar cl = Calendar.getInstance();
        cl.setTime(registerTime);
        cl.add(Calendar.DATE , 2);

        return cl.getTime();
    }

}
