package com.roadjava.service.impl;

import com.roadjava.entity.StudentDO;
import com.roadjava.req.StudentRequest;
import com.roadjava.res.TableDTO;
import com.roadjava.service.StudentService;
import com.roadjava.util.DBUtil;
import com.roadjava.util.SnowFlakeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class StudentServiceImpl implements StudentService {

    @Override   //重写retrieveStudents方法 检索学生信息
    //分两种情况，情况一：检索出全部数据 情况二：如果搜索栏不为空，检索满足条件的数据
    public TableDTO retrieveStudents(StudentRequest request) {
        StringBuilder sql = new StringBuilder(); // StringBuilder 可变的字符序列
        sql.append("select*from student ");
        //如果搜索栏不为空，数据库中查找输入的姓名
        if(request.getSearchKey()!=null && !"".equals(request.getSearchKey().trim()) && "姓名查询".equals(request.getType())){  //搜索
            sql.append(" where name like '%"+request.getSearchKey().trim()+"%'");
        }else if (request.getSearchKey()!=null && !"".equals(request.getSearchKey().trim()) && "学号查询".equals(request.getType())){
            sql.append(" where no="+request.getSearchKey().trim() + " ");
        }
        //排序，平均分降序排序，总分高，平均分就高，所以计算总分即可
        sql.append("order by (math+english+Java+computer) desc limit ").append(request.
                        getStart()).append(",")
                .append(request.getPageSize());
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        TableDTO returnDTO = new TableDTO();

        try{
           conn= DBUtil.getConn();
           ps = conn.prepareStatement(sql.toString());//获取到ps
            rs = ps.executeQuery();//执行查询
            //查询记录
           returnDTO.setData(fillData(rs));//fillData抽出来的方法  填充数据，把id 姓名，学号，各科成绩，最高分，最低分，平均分都加进去
            sql.setLength(0);
            sql.append("select count(*) from student");
            if(request.getSearchKey()!=null && !"".equals(request.getSearchKey().trim()) && "姓名查询".equals(request.getType())){
                sql.append(" where name like '%"+request.getSearchKey().trim()+"%'");  //数据库中查找关键词
            }else if (request.getSearchKey()!=null && !"".equals(request.getSearchKey().trim()) && "学号查询".equals(request.getType())){
                sql.append(" where no="+request.getSearchKey().trim());
            }
            ps=conn.prepareStatement(sql.toString());  //  toString():return new String(value, 0, count);
            // prepareStatement 先处理给定的sql语句，对其执行语法检查
            rs=ps.executeQuery();  //把数据库响应的查询结果给rs
            while(rs.next()){
                int count = rs.getInt(1);
                returnDTO.setTotalCount(count);//获取有多少条数据
            }
            System.out.println(returnDTO);
            return returnDTO;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.closeRs(rs);
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }

        return null;
    }

    @Override
    public boolean add(StudentDO studentDO) {  //布尔类型的add方法
        StringBuilder sql = new StringBuilder();
        sql.append("insert into student(name,no,college,grade,classes,sex,birthday) "); //SQL语句 插入到student表格中
        sql.append("values(?,?,?,?,?,?,?) ");
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            //获取输入的信息
            conn= DBUtil.getConn();
            ps = conn.prepareStatement(sql.toString());//获取到ps
            ps.setString(1,studentDO.getName());
            ps.setString(2, SnowFlakeUtil.getSnowFlakeId().toString());
            ps.setString(3,studentDO.getCollege());
            ps.setString(4,studentDO.getGrade());
            ps.setString(5,studentDO.getClasses());
            ps.setString(6,studentDO.getSex());
            ps.setString(7,studentDO.getBirthday());
            return ps.executeUpdate()==1;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }

        return false;
    }

    @Override
    public boolean updateByNo(StudentDO studentDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE student SET math=?,english=?,Java=?,computer=? WHERE `no`=?"); //SQL语句 插入到student表格中
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            //获取输入的信息
            conn= DBUtil.getConn();
            ps = conn.prepareStatement(sql.toString());//获取到ps
            ps.setInt(1,studentDO.getMath());
            ps.setInt(2,studentDO.getPe());
            ps.setInt(3,studentDO.getJava());
            ps.setInt(4,studentDO.getComputer());
            ps.setString(5,studentDO.getNo());
            return ps.executeUpdate()==1;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }

        return false;

    }

    @Override
    public StudentDO getByNo(String no) {
        StringBuilder sql = new StringBuilder(); // StringBuilder 可变的字符序列
        sql.append("select * from student where no=" + no);


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        StudentDO studentDO = null;

        try{
            conn= DBUtil.getConn();
            ps = conn.prepareStatement(sql.toString());//获取到ps
            rs = ps.executeQuery();//执行查询
            //查询记录
            while (rs.next()){
                studentDO = new StudentDO();
                String name = rs.getString("name");
                studentDO.setName(name);
                String college = rs.getString("college");
                studentDO.setCollege(college);
                String grade = rs.getString("grade");
                studentDO.setGrade(grade);
                String classes = rs.getString("classes");
                studentDO.setClasses(classes);
                String sex = rs.getString("sex");
                studentDO.setSex(sex);
                String birthday = rs.getString("birthday");
                studentDO.setBirthday(birthday);
                studentDO.setNo(no);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return studentDO;
    }

    @Override
    public boolean updateStuByNo(StudentDO studentDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE student SET name=?,college=?,grade=?,classes=?,sex=?,birthday=? WHERE `no`=?"); //SQL语句 插入到student表格中
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            //获取输入的信息
            conn= DBUtil.getConn();
            ps = conn.prepareStatement(sql.toString());//获取到ps
            ps.setString(1,studentDO.getName());
            ps.setString(2,studentDO.getCollege());
            ps.setString(3,studentDO.getGrade());
            ps.setString(4,studentDO.getClasses());
            ps.setString(5,studentDO.getSex());
            ps.setString(6,studentDO.getBirthday());
            ps.setString(7,studentDO.getNo());
            return ps.executeUpdate()==1;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }

        return false;

    }

    @Override
    public boolean deleteByNo(String no) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from student WHERE `no`=?"); //SQL语句 插入到student表格中
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            //获取输入的信息
            conn= DBUtil.getConn();
            ps = conn.prepareStatement(sql.toString());//获取到ps
            ps.setString(1,no);
            return ps.executeUpdate()==1;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }

        return false;
    }

    //填充数据
    private  Vector<Vector<Object>> fillData(ResultSet rs) throws SQLException {
        Vector<Vector<Object>> data=new Vector<>();
        double maxMath = 0;
        double maxEnglish = 0;
        double maxJava = 0;
        double maxComputer = 0;
        double minMath = 999;
        double minEnglish = 999;
        double minJava = 999;
        double minComputer = 999;
        double avgMath = 0;
        double avgEnglish = 0;
        double avgJava = 0;
        double avgComputer = 0;
        int count = 0;
        while (rs.next()){
            //处理查处的每一条记录
            Vector<Object> oneRecord = new Vector<>();
            //获取数据
            String name = rs.getString("name");
            String no = rs.getString("no");
            int math = rs.getInt("math");
            if (math>maxMath){
                maxMath = math;
            }
            if (math<minMath){
                minMath = math;
            }
            avgMath += math;
            int English = rs.getInt("english");
            if (English>maxEnglish){
                maxEnglish = English;
            }
            if (English<minEnglish){
                minEnglish = English;
            }
            avgEnglish += English;
            int JavaScore = rs.getInt("Java");
            if (JavaScore>maxJava){
                maxJava = JavaScore;
            }
            if (JavaScore<minJava){
                minJava = JavaScore;
            }
            avgJava += JavaScore;
            int ComScore = rs.getInt("computer");
            if (ComScore>maxComputer){
                maxComputer = ComScore;
            }
            if (ComScore<minComputer){
                minComputer = ComScore;
            }
            avgComputer += ComScore;
            count++;
            int totalScore = math+English+JavaScore+ComScore;
            double AveScore = totalScore/4.0;
            oneRecord.addElement(name);
            oneRecord.addElement(no);
            oneRecord.addElement(math);
            oneRecord.addElement(English);
            oneRecord.addElement(JavaScore);
            oneRecord.addElement(ComScore);
            oneRecord.addElement(AveScore);
            data.addElement(oneRecord);
        }
        //求平均分，总分/数据条数
        avgMath = avgMath/count;
        avgEnglish = avgEnglish/count;
        avgJava = avgJava/count;
        avgComputer = avgComputer/count;

        Vector<Object> avg = new Vector<>();
        avg.addElement("平均分");
        avg.addElement("——");
        avg.addElement(String.format("%.2f",avgMath));
        avg.addElement(String.format("%.2f",avgEnglish));
        avg.addElement(String.format("%.2f",avgJava));
        avg.addElement(String.format("%.2f",avgComputer));
        avg.addElement("——");
        data.addElement(avg);
        Vector<Object> max = new Vector<>();
        max.addElement("最高分");
        max.addElement("——");
        max.addElement(maxMath);
        max.addElement(maxEnglish);
        max.addElement(maxJava);
        max.addElement(maxComputer);
        max.addElement("——");
        data.addElement(max);
        Vector<Object> min = new Vector<>();
        min.addElement("最低分");
        min.addElement("——");
        min.addElement(minMath);
        min.addElement(minEnglish);
        min.addElement(minJava);
        min.addElement(minComputer);
        min.addElement("——");
        data.addElement(min);
        return data;
    }
}
