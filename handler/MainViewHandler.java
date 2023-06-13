/*
*
* 主界面按钮功能实现
* */
package com.roadjava.handler;

import com.roadjava.entity.AdminDO;
import com.roadjava.service.AdminService;
import com.roadjava.service.impl.AdminServiceImpl;
import com.roadjava.student.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainViewHandler  implements ActionListener {
    private MainView mainView;
    public MainViewHandler(MainView mainView){  //MainViewHandler构造方法

        this.mainView=mainView;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();//强转为JButton
        String text = jButton.getText();
        if("增加学生".equals(text)){  //如果增加按钮被按下
            new AddStudentView(mainView,null);
        }
        else if(text.contains("查询")){ //如果查询按钮被按下
            mainView.setPageNow(1);//切换到第一页
            mainView.reloadTable(text); //更新数据
        }
        /*else if("上一页".equals(text)){
            mainView.setPageNow(mainView.getPageNow()-1);
            mainView.reloadTable();
        }
        else if("下一页".equals(text)){
            mainView.setPageNow(mainView.getPageNow()+1);
            mainView.reloadTable();
        }*/
        else if ("录入分数".equals(text)){
            //录入分数
            new AddStudentScoreView(mainView);
        }
        else if ("修改学生".equals(text)){
            new UpdateView(mainView,2);
        }
        else if ("删除学生".equals(text)){
            new UpdateView(mainView,1);
        }
    }

}
