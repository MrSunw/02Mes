package com.mes.annotaion;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ReadDataSource {
    public static DataSource dataSource;

    static {
        dataSource = new ComboPooledDataSource();
    }

    public static DataSource getDataSource(){
        return dataSource;
    }
}
