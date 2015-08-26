
package com.zhengshouzi.myweb.servicesimpl;
import com.zhengshouzi.myweb.beans.MailBean;
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
import java.util.HashMap;
import java.util.Map;

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
        boolean b=false;

        try {
            //���ע��ĸ�����Ϣ
            registerForm.setRegisterTime(new Timestamp(System.currentTimeMillis()));
            registerForm.setStatus(String.valueOf(0));
            registerForm.setActivatecode(MD5Utils.encode2hex(registerForm.getEmail()));

            //�����û���Ϣ
            b = registerDao.register(registerForm);

            //����ɹ����ͼ����ʼ�
            if(b==true){
                ///�����ʼ�

                String Content ="<html><head><meta http-equiv='keywords' content='keyword1,keyword2,keyword3'>" +
                "<meta http-equiv='description' content='this is my page'><meta http-equiv='content-type' content='text/html; charset=utf-8'>" +
                        "</head><body><h1>"+registerForm.getEmail()
                        +"��ã�</h1><h1>     ����������Ӽ����˺ţ�48Сʱ��Ч����������ע���˺ţ�����ֻ��ʹ��һ�Σ��뾡�켤�</br>" +
                        "<a href=http://localhost:8080/MyWeb/register/activate.do?email="+registerForm.getEmail()+"&validateCode="+registerForm.getActivatecode()+">ȥ��֤</a>"
                        +"</h1><h1></h1><h1></h1><h1>  ����һ��ϵͳ�ʼ����벻�ػظ���</h1><h1>   лл��</h1><h1>"+new Date().toString()+"</h1></body></html>";

                Map map = new HashMap();
                map.put("link_address", Content);

                MailBean mailBean = new MailBean();
                mailBean.setToEmail(registerForm.getEmail());
                mailBean.setSubject("֣ǿ������������");
                mailBean.setFrom("601097836@qq.com");
                mailBean.setFromName("֣ǿ");
                mailBean.setData(map);

                System.out.println(mailBean.toString());
System.out.println(Content);

                try {
                    sendMail.sendHtmlMail(mailBean);
                }catch (Exception e){
                    //�����ʼ�ʧ�ܣ�ɾ��ע����Ϣ
                    b=false;
                    registerDao.deleteRegisterByEmail(registerForm.getEmail());
                    e.printStackTrace();
                }
            }else{

            }
        } catch (Exception e) {
            b=false;
            e.printStackTrace();
        }

       return b;
    }

    @Override
    public boolean processActivate(String email, String validateCode) throws ServiceException {

        boolean b=false;
        //���ݷ��ʲ㣬ͨ��email��ȡ�û���Ϣ
        RegisterForm registerForm = registerDao.findByEmail(email);
        System.out.println(registerForm.toString());
        //��֤�û��Ƿ����
        if(registerForm!=null) {
            //��֤�û�����״̬
            if(registerForm.getStatus().equals("0")) {
                ///û����
               //��ȡ��ǰʱ��
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());

                //��֤�����Ƿ����
               // currentTime.before(registerForm.getRegisterTime());
                if(currentTime.before(registerForm.getLastActivateTime())) {
                    //��֤�������Ƿ���ȷ
                    if(validateCode.equals(registerForm.getActivatecode())) {
                        //����ɹ��� //�������û��ļ���״̬��Ϊ�Ѽ���
                        System.out.println("==sq==="+registerForm.getStatus());
                        registerForm.setStatus(String.valueOf(1));//��״̬��Ϊ����
                        System.out.println("==sh===" + registerForm.getStatus());
                        registerDao.updateRegisterStatus(registerForm.getEmail(), String.valueOf(1));
                        b = true;
                    } else {
                        throw new ServiceException("�����벻��ȷ");
                    }
                } else { throw new ServiceException("�������ѹ��ڣ�");
                }
            } else {
                throw new ServiceException("�����Ѽ�����¼��");
            }
        } else {
            throw new ServiceException("������δע�ᣨ�����ַ�����ڣ���");
        }

        return b;

    }
}
