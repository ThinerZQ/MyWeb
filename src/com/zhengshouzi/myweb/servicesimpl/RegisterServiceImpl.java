
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

        //���ע��ʱ��
        registerForm.setRegisterTime(new Timestamp(System.currentTimeMillis()));
        registerForm.setStatus(String.valueOf(0));
        registerForm.setActivatecode(MD5Utils.encode2hex(registerForm.getEmail()));

        //�����û���Ϣ
        boolean b = registerDao.register(registerForm);






        ///�����ʼ�
        StringBuffer sb=new StringBuffer("����������Ӽ����˺ţ�48Сʱ��Ч����������ע���˺ţ�����ֻ��ʹ��һ�Σ��뾡�켤�</br>");
        sb.append("<a href=\"http://localhost:8080/springmvc/user/register?action=activate&email=");
        sb.append(registerForm.getEmail());
        sb.append("&validateCode=");
        sb.append(registerForm.getActivatecode());
        sb.append("\">http://localhost:8080/springmvc/user/register?action=activate&email=");
        sb.append(registerForm.getEmail());
        sb.append("&validateCode=");
        sb.append(registerForm.getActivatecode());
        sb.append("</a>");





        //�����ʼ�

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
        //���ݷ��ʲ㣬ͨ��email��ȡ�û���Ϣ
        RegisterForm registerForm = registerDao.findByEmail(email);
        //��֤�û��Ƿ����
        if(registerForm!=null) {
            //��֤�û�����״̬
            if(registerForm.getStatus()=="0") {
                ///û����
                Date currentTime = new Date();//��ȡ��ǰʱ��
                //��֤�����Ƿ����
                currentTime.before(registerForm.getRegisterTime());
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
