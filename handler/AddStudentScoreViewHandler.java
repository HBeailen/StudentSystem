package com.roadjava.handler;

import com.roadjava.entity.StudentDO;
import com.roadjava.service.StudentService;
import com.roadjava.service.impl.StudentServiceImpl;
import com.roadjava.student.view.AddStudentScoreView;
import com.roadjava.student.view.AddStudentView;
import com.roadjava.student.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStudentScoreViewHandler implements ActionListener {
    private  AddStudentScoreView addStudentScoreView;
    private MainView mainView;
    public AddStudentScoreViewHandler(AddStudentScoreView addStudentScoreView, MainView mainView){//AddStudentViewHandler构造方法
        this.addStudentScoreView=addStudentScoreView;
        this.mainView=mainView;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();//强转为JButton
        String text = jButton.getText();
        if("添加".equals(text)){  //如果添加按钮被按下
            StudentService studentService = new StudentServiceImpl(); //创建了一个studentService的对象
            StudentDO studentDO = addStudentScoreView.buildStudentDO();   //获取添加的学生信息
            boolean addResult = studentService.updateByNo(studentDO); //调用add方法
            if(addResult){
                mainView.reloadTable(); //重新加载表格查到最新数据
                addStudentScoreView.dispose(); //添加成功销毁窗口
            }
            else{
                JOptionPane.showMessageDialog(addStudentScoreView,"添加失败");
            }
        }


    }

}
