package com.zhengshouzi.myweb.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by zhengshouzi on 2015/8/24.
 */
public class DBHelper {

    public static Connection getMySqlConnection(){
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/MyWeb";
        String user = "root";
        String password = "zhengqiang";
        Connection conn = null;
        try {
            Class.forName(driver);//ָ����������
            conn = DriverManager.getConnection(url, user, password);//��ȡ����
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }





}