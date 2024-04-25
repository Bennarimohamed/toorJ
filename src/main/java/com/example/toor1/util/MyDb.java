package com.example.toor1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDb {

    final String URL="jdbc:mysql://localhost:3306/tttt";
    final String USER="root";
    final String PWD="";

    private static MyDb instance;


    Connection cnx;

    private MyDb(){
        try{
            cnx= DriverManager.getConnection(URL,USER,PWD);
            System.out.println("Connected to database " + cnx.toString());
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("_____not connected_____");
        }
    }

    public static MyDb getInstance(){
        if (instance==null){
            instance=new MyDb();
        }return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
