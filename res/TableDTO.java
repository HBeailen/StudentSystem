package com.roadjava.res;

import java.util.Vector;

public class TableDTO {
    private Vector<Vector<Object>> data;  //，第一个vector代表行，第二个vector代表这个行的各列数据
    private int totalCount;

    public Vector<Vector<Object>> getData() {

        return data;
    }

    public void setData(Vector<Vector<Object>> data) {

        this.data = data;
    }

    public int getTotalCount() {

        return totalCount;
    }

    public void setTotalCount(int totalCount) {

        this.totalCount = totalCount;
    }
}
