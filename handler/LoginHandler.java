/*
* 登陆界面按钮条件判断
* */
package com.roadjava.handler;

import com.roadjava.entity.AdminDO;
import com.roadjava.service.AdminService;
import com.roadjava.service.impl.AdminServiceImpl;
import com.roadjava.student.view.LoginView;
import com.roadjava.student.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginHandler extends KeyAdapter implements ActionListener {
    private LoginView loginView;
    public LoginHandler(LoginView loginView){  //LoginHandler的构造方法

        this.loginView=loginView;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();//强转为JButton
        String text = jButton.getText();
        //如果重置按钮被按下，登陆界面的用户用和密码清空
        if("重置".equals(text)){
         loginView.getUserText().setText("");
         loginView.getPwdField().setText("");
        }
        //如果登陆按钮被按下，执行login方法，login方法在下面
        else if("登录".equals(text)){
            login(); //抽象出来一个方法，方便重复使用
        }
    }

    private void login() {
        String user=loginView.getUserText().getText();//获取用户名内容
        char[] chars=loginView.getPwdField().getPassword();//获取密码内容
        //trim方法：删除字符串首部和尾部的空格
        //如果用户名为空，出示用户名密码必填
        if(user==null||"".equals(user.trim())||chars==null){
            JOptionPane.showMessageDialog(loginView,"用户名密码必填");
            return;
        }
        String pwd=new String(chars);
        System.out.println(user+":"+pwd);
        //查询数据库是否有此用户名和密码
        AdminService adminService = new AdminServiceImpl();
        AdminDO adminDO = new AdminDO();
        adminDO.setUserName(user); //获取用户输入用户名
        adminDO.setPwd(pwd);  //获取用户输入密码

        boolean flag=adminService.validataAdmin(adminDO); //定义一个flag，为validataAdmin返回的true或者false
        if(flag){   //如果为true，说明密码校验正确
           //跳转到主界面并销毁登陆界面
            new MainView();
            loginView.dispose();//销毁登录界面
        }
        else{ //密码不正确
            JOptionPane.showMessageDialog(loginView,"用户名密码错误");
        }
    }
    //回车登录
    @Override
    public void keyPressed(KeyEvent e) {
        if(KeyEvent.VK_ENTER==e.getKeyCode()){
            login();
        }

    }
}
