package com.mini;

public class NativeMethod {
    public static void main(String[] args) {
        NativeMethod nm = new NativeMethod();
        System.out.println(nm.cal(3,5));

    }

    public native int  cal(int a,int b);

}
