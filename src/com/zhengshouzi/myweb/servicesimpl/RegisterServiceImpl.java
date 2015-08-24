
package com.zhengshouzi.myweb.servicesimpl;
import com.zhengshouzi.myweb.dao.RegisterDao;
import com.zhengshouzi.myweb.exceptions.ServiceException;
import com.zhengshouzi.myweb.forms.RegisterForm;
import com.zhengshouzi.myweb.services.RegisterService;
import com.zhengshouzi.myweb.tools.MD5Utils;
import com.zhengshouzi.myweb.tools.SendMail;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by zhengshouzi on 2015/8/24.
 */
public class RegisterServiceImpl implements RegisterService{

    @Resource(name="registerDao")
    RegisterDao registerDao;
    @Resource(name = "sendMail")
    SendMail sendMail;

    @Override
    public boolean register(RegisterForm registerForm) {

        //添加注册时间
        registerForm.setRegisterTime(new Timestamp(System.currentTimeMillis()));
        registerForm.setStatus(String.valueOf(0));
        registerForm.setActivatecode(MD5Utils.encode2hex(registerForm.getEmail()));

        //保存用户信息
        boolean b = registerDao.register(registerForm);






        ///发送邮件
        StringBuffer sb=new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
        sb.append("<a href=\"http://localhost:8080/springmvc/user/register?action=activate&email=");
        sb.append(registerForm.getEmail());
        sb.append("&validateCode=");
        sb.append(registerForm.getActivatecode());
        sb.append("\">http://localhost:8080/springmvc/user/register?action=activate&email=");
        sb.append(registerForm.getEmail());
        sb.append("&validateCode=");
        sb.append(registerForm.getActivatecode());
        sb.append("</a>");





        //发送邮件

        //sendMail.sendMail();
        try {
            //sendMail.sendHtmlMail();
            sendMail.sendFileMail();
        } catch (MessagingException e) {
            e.printStackTrace();
        }



       return b;
    }

    @Override
    public boolean processActivate(String email, String validateCode) throws ServiceException {

        boolean b=false;
        //数据访问层，通过email获取用户信息
        RegisterForm registerForm = registerDao.findByEmail(email);
        //验证用户是否存在
        if(registerForm!=null) {
            //验证用户激活状态
            if(registerForm.getStatus()=="0") {
                ///没激活
                Date currentTime = new Date();//获取当前时间
                //验证链接是否过期
                currentTime.before(registerForm.getRegisterTime());
                if(currentTime.before(registerForm.getLastActivateTime())) {
                    //验证激活码是否正确
                    if(validateCode.equals(registerForm.getActivatecode())) {
                        //激活成功， //并更新用户的激活状态，为已激活
                        System.out.println("==sq==="+registerForm.getStatus());
                        registerForm.setStatus(String.valueOf(1));//把状态改为激活
                        System.out.println("==sh===" + registerForm.getStatus());
                        registerDao.updateRegisterStatus(registerForm.getEmail(), String.valueOf(1));
                        b = true;
                    } else {
                        throw new ServiceException("激活码不正确");
                    }
                } else { throw new ServiceException("激活码已过期！");
                }
            } else {
                throw new ServiceException("邮箱已激活，请登录！");
            }
        } else {
            throw new ServiceException("该邮箱未注册（邮箱地址不存在）！");
        }

        return b;

    }
}
