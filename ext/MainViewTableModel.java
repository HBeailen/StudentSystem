package com.roadjava.student.view.ext;



import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class MainViewTableModel extends DefaultTableModel {
    static Vector<String> columns = new Vector<>();//定义列
    static {  //不变的，共享的
        columns.addElement("姓名");
        columns.addElement("学号");
        columns.addElement("高等数学");
        columns.addElement("大学体育");
        columns.addElement("Java程序设计");
        //columns.addElement("计算机基础");
        //columns.addElement("平均分");

    }
    private MainViewTableModel(){   //私有不被修改

        super(null,columns);
    }
    private static MainViewTableModel mainViewTableModel = new MainViewTableModel();

    public static MainViewTableModel assembleModel(Vector<Vector<Object>>data){ //提供assembleModel方法
        mainViewTableModel.setDataVector(data,columns);//进行数据更新  替换原有的数据  setDataVector自带函数
        return mainViewTableModel;
    }
    public static void updateModelModel(Vector<Vector<Object>>data){
        mainViewTableModel.setDataVector(data,columns);//进行数据更新
    }

    public static Vector<String> getColumns() {

        return columns;
    }

    @Override
    public boolean isCellEditable(int row, int column) {

        return false;
    }
}