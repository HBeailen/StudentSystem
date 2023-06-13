package com.roadjava.handler;

import com.roadjava.entity.StudentDO;
import com.roadjava.service.StudentService;
import com.roadjava.service.impl.StudentServiceImpl;
import com.roadjava.student.view.AddStudentScoreView;
import com.roadjava.student.view.AddStudentView;
import com.roadjava.student.view.MainView;
import com.roadjava.student.view.UpdateView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateStudentViewHandler implements ActionListener {
    private UpdateView updateView;
    private MainView mainView;
    public UpdateStudentViewHandler(UpdateView updateView, MainView mainView){//AddStudentViewHandler构造方法
        this.updateView=updateView;
        this.mainView=mainView;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();//强转为JButton
        String text = jButton.getText();
        if ("修改".equals(text)){
            String no = updateView.getNo();
            new AddStudentView(mainView,no);
            updateView.dispose();
        }else if ("删除".equals(text)){
            StudentService studentService = new StudentServiceImpl();
            boolean addResult = studentService.deleteByNo(updateView.getNo());
            if(addResult){
                mainView.reloadTable(); //重新加载表格查到最新数据
                updateView.dispose(); //添加成功销毁窗口
            }
        }

    }
}
