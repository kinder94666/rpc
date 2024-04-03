package com.yupi.example.provider;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class AcmModelTest {

    public static class Person implements Comparable<Person>{
        public String name;
        public int age;
        public Person(String name, int age){
            this.name = name;
            this.age = age;
        }
        @Override
        public int compareTo(Person o) {
            return this.age - o.age;
        }
        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

    }
    public static void main(String[] args) {
       ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
       while(true){
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("hello"+Thread.currentThread().getName());
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

       }

//
//        Scanner in = new Scanner(System.in);
//        String ss = in.nextLine();
//        String[] arr = ss.split(" ");
//        String raw = in.next();
//        String res = in.next();
//        for (int i = 0; i < arr.length; i++) {
//            if(arr[i].equals(raw)){
//                arr[i] = res;
//            }
//        }
//        String resStr = "";
//        for (int i = 0; i < arr.length; i++) {
//            resStr += arr[i];
//            if(i != arr.length - 1){
//                resStr += " ";
//            }
//        }
//        System.out.println(resStr);
    }
//    }
}
