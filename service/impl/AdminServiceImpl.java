package com.roadjava.service.impl;

import com.roadjava.entity.AdminDO;
import com.roadjava.service.AdminService;
import com.roadjava.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminServiceImpl implements AdminService {

    @Override
    public boolean validataAdmin(AdminDO adminDo) {  //重写validataAdmin方法，返回值为true或false
        String userName = adminDo.getUserName(); //调用getUserName()方法，获取用户名
        String pwdParam = adminDo.getPwd();//调用getPwd()方法，获取密码
        String sql = "select pwd from manager where user_name = ?";//定义数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet=null;//及时关闭资源
        //基本校验
        try{
            conn = DBUtil.getConn();//获取连接
            if(conn==null){  //如果产生空指针，return false
                return false;
            }
            ps=conn.prepareStatement(sql);
            ps.setString(1, adminDo.getUserName());
            resultSet = ps.executeQuery();//  执行PreparedStatement对象中的SQL语句，即sql，查询密码，并储存在resultSet中
            while(resultSet.next()){
                    String pwd=resultSet.getString(1);//获取数据库传过来的密码
                    if(adminDo.getPwd().equals(pwd)){  //判断输入密码与数据库中密码是否一致
                    return true;   //密码一致，返回true
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {    //关闭结果
            DBUtil.closeRs(resultSet);
            DBUtil.closeConn(conn);
            DBUtil.closePs(ps);
        }




        return false;
    }
}
