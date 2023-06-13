package com.roadjava.service;

import com.roadjava.entity.StudentDO;
import com.roadjava.req.StudentRequest;
import com.roadjava.res.TableDTO;

public interface StudentService {
    //检索
    TableDTO retrieveStudents(StudentRequest request);

    //添加
    boolean add(StudentDO studentDO);
    //修改成绩
    boolean updateByNo(StudentDO studentDO);


    StudentDO getByNo(String no);
    //修改学生
    boolean updateStuByNo(StudentDO studentDO);
    //删除
    boolean deleteByNo(String no);

}
