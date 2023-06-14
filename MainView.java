package com.roadjava.student.view;

import com.roadjava.handler.LoginHandler;
import com.roadjava.handler.MainViewHandler;
import com.roadjava.req.StudentRequest;
import com.roadjava.res.TableDTO;
import com.roadjava.service.StudentService;
import com.roadjava.service.impl.StudentServiceImpl;
import com.roadjava.student.view.ext.MainViewTable;
import com.roadjava.student.view.ext.MainViewTableModel;
import com.roadjava.util.DimensionUtil;
//import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class MainView extends JFrame {
    JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton addBtn = new JButton("增加学生");
    JButton updateBtn = new JButton("修改学生");
    JButton deleteBtn = new JButton("删除学生");
    JButton addScoreBtn = new JButton("录入分数");
    JTextField searchTxt = new JTextField(15);
    JButton searchNoBtn = new JButton("学号查询");
    JButton searchNameBtn = new JButton("姓名查询");
    JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));//靠右，但是在中间也挺好看的
    /*JButton preBtn = new JButton("上一页");
    JButton nextBtn = new JButton("下一页");*/

    MainViewTable mainViewTable = new MainViewTable();
    private int pageNow = 1;//当前是第几页
    private int pageSize = 40;  //一页显示多少条记录

    MainViewHandler mainViewHandler;

    public MainView() {   //MainView的构造方法
        super("学生成绩管理系统");
        Container contentPane = getContentPane();//获取内容面板
        mainViewHandler = new MainViewHandler(this);
        //放置北边的组件
        layoutNorth(contentPane);//抽象出来的函数
        //放置中间的jtable
        layoutCenter(contentPane);
        //放置南边的组件
        //layoutSouth(contentPane);

        //可以在这里自定义图标

        //根据屏幕大小设置主界面大小
        setBounds(DimensionUtil.getBounds());
        //设置窗体完全充满整个屏幕的可见大小
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);//直接居中显示
        //关闭退出程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);//true大小可以改变 false大小不可改变
        setVisible(true);//设置可见
    }

    //放置中间
    //实现主界面表格创建
    private void layoutCenter(Container contentPane) {
        TableDTO dto = getTableDTO();  // getTableDTO() 抽出来的方法
        MainViewTableModel mainViewTableModel = MainViewTableModel.assembleModel(dto.getData());//获取数据
        //把jtable和model关联
        mainViewTable.setModel(mainViewTableModel);//把数据写进去
        mainViewTable.renderRule();//渲染规则，隔行变色，不变也行
        JScrollPane jScrollPane = new JScrollPane(mainViewTable); //将mainViewTable添加到滚动面板：一页设置条数很多的话会自动生成滚动面板
        contentPane.add(jScrollPane, BorderLayout.CENTER); //添加到内容面板
        //showPreNext(dto.getTotalCount());//设置上一页下一页是否可见,传入获取到的数据总数
    }

    private TableDTO getTableDTO() {
        StudentService studentService = new StudentServiceImpl();
        StudentRequest request = new StudentRequest();
        request.setPageNow(pageNow);
        request.setPageSize(pageSize);
        request.setSearchKey(searchTxt.getText().trim()); //获取搜索栏的内容 trim() 清除文本前后的空格
        request.setType(searchNoBtn.getText());
        TableDTO tableDTO = studentService.retrieveStudents(request);
        return tableDTO;
    }
    private TableDTO getTableDTO(String text) {
        StudentService studentService = new StudentServiceImpl();
        StudentRequest request = new StudentRequest();
        request.setPageNow(pageNow);
        request.setPageSize(pageSize);
        request.setSearchKey(searchTxt.getText().trim()); //获取搜索栏的内容 trim() 清除文本前后的空格
        request.setType(text);
        TableDTO tableDTO = studentService.retrieveStudents(request);
        return tableDTO;
    }

    //设置上一页下一页是否可见
    /*private void showPreNext(int totalCount) {
        if (pageNow == 1) {   //如果是第一页，则上一页不可见
            preBtn.setVisible(false);
        } else {
            preBtn.setVisible(true);
        }
        int pageCount = 0;//总共有多少页
        //设定了一页存储多少条数据，如果整除，则页数=总数/一页的条数  不是整除，页数=总数/一页的条数+1
        if (totalCount % pageSize == 0) {
            pageCount = totalCount / pageSize;
        } else {
            pageCount = totalCount / pageSize + 1;
        }
        if (pageNow == pageCount) {   //如果当前页码=页码总数，设置下一页不可见
            nextBtn.setVisible(false);
        } else {
            nextBtn.setVisible(true);  //否则可见
        }

    }*/

    //北边组件函数
    private void layoutNorth(Container contentPane) {
        //增加事件监听
        addBtn.addActionListener(mainViewHandler);
        updateBtn.addActionListener(mainViewHandler);
        deleteBtn.addActionListener(mainViewHandler);
        searchNoBtn.addActionListener(mainViewHandler);
        searchNameBtn.addActionListener(mainViewHandler);
        northPanel.add(addBtn);
        northPanel.add(updateBtn);
        northPanel.add(deleteBtn);
        addScoreBtn.addActionListener(mainViewHandler);
        northPanel.add(addScoreBtn);
        northPanel.add(searchTxt);
        northPanel.add(searchNoBtn);
        northPanel.add(searchNameBtn);
        contentPane.add(northPanel, BorderLayout.NORTH);
    }

    //南边组件函数
    /*private void layoutSouth(Container contentPane) {
        preBtn.addActionListener(mainViewHandler);
        nextBtn.addActionListener(mainViewHandler);
        southPanel.add(preBtn);
        southPanel.add(nextBtn);

        contentPane.add(southPanel, BorderLayout.SOUTH);
    }*/


   /* public static void main(String[] args) {

        new MainView();
    }*/

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public int getPageNow() {
        return pageNow;
    }

    public void reloadTable() {    //重现加载数据
        TableDTO dto = getTableDTO();  //返回tableDTO = studentService.retrieveStudents(request);
        MainViewTableModel.updateModelModel(dto.getData()); // 先检索到数据，然后再获取数据   更新数据
        mainViewTable.renderRule();
       // showPreNext(dto.getTotalCount()); //下一页上一页显示设置
    }

    public void reloadTable(String text) {    //重现加载数据
        TableDTO dto = getTableDTO(text);  //返回tableDTO = studentService.retrieveStudents(request);
        MainViewTableModel.updateModelModel(dto.getData()); // 先检索到数据，然后再获取数据   更新数据
        mainViewTable.renderRule();
        //showPreNext(dto.getTotalCount()); //下一页上一页显示设置
    }
}