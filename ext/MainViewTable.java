package com.roadjava.student.view.ext;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Vector;

public class MainViewTable extends JTable {
    public MainViewTable(){
        //设置表头
        JTableHeader tableHeader = getTableHeader();
        tableHeader.setFont(new Font(null,Font.BOLD,16));//设置字体，默认字体，加粗，16号
        tableHeader.setForeground(Color.RED);//表头颜色红色
        //设置表格体
        setFont(new Font(null,Font.PLAIN,14));
        setForeground(Color.black);
        setGridColor(Color.BLACK);//表格线
        setRowHeight(30);
        //设置多行选择
        getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    }

    public void renderRule(){
        //设置表格的渲染方式
        Vector<String> columns = MainViewTableModel.getColumns();
        MainViewCellRender render = new MainViewCellRender();
        for(int i=0;i<columns.size();i++){
            TableColumn column = getColumn(columns.get(i));
            column.setCellRenderer(render);//设置渲染方式
            if(i==0){
                column.setPreferredWidth(50);//序号列比较窄，直接设置宽度为50
                column.setResizable(false);//设置宽度不可改变
            }
        }
    }
}
