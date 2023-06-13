package com.roadjava.handler;

import com.roadjava.entity.StudentDO;
import com.roadjava.service.StudentService;
import com.roadjava.service.impl.StudentServiceImpl;
import com.roadjava.student.view.AddStudentView;
import com.roadjava.student.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStudentViewHandler implements ActionListener {
    private  AddStudentView addStudentView;
    private MainView mainView;
    public AddStudentViewHandler(AddStudentView addStudentView, MainView mainView){//AddStudentViewHandler构造方法
        this.addStudentView=addStudentView;
        this.mainView=mainView;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();//强转为JButton
        String text = jButton.getText();
        if("添加".equals(text)){  //如果添加按钮被按下
            StudentService studentService = new StudentServiceImpl(); //创建了一个studentService的对象
            StudentDO studentDO = addStudentView.buildStudentDO();   //获取添加的学生信息
            boolean addResult = studentService.add(studentDO); //调用add方法
            if(addResult){
                mainView.reloadTable(); //重新加载表格查到最新数据
                addStudentView.dispose(); //添加成功销毁窗口
            }
            else{
                JOptionPane.showMessageDialog(addStudentView,"添加失败");
            }
        }else if ("修改".equals(text)){
            StudentService studentService = new StudentServiceImpl(); //创建了一个studentService的对象
            StudentDO studentDO = addStudentView.buildStudentDO();   //获取添加的学生信息
            boolean addResult = studentService.updateStuByNo(studentDO); //调用add方法
            if(addResult){
                mainView.reloadTable(); //重新加载表格查到最新数据
                addStudentView.dispose(); //添加成功销毁窗口
            }
            else{
                JOptionPane.showMessageDialog(addStudentView,"修改失败");
            }
        }


    }

}
