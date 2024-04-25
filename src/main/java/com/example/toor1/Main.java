package com.example.toor1;

import com.example.toor1.util.MyDb;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        MyDb fdb = MyDb.getInstance();

        fdb.getCnx();


    }
}
