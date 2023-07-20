package com.example.webhookdemo.service;

import com.sun.tools.javac.Main;

import java.util.List;

public class NewClass {

    public NewClass(List ... mains){
        for (List list : mains){
            System.out.println(list);
        }

    }

    public static void salom(Integer ... integers){

    }
}
