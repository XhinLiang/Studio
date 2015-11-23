package com.wecan.xhin.studio.bean.up;

/**
 * Created by xhinliang on 15-11-23.
 * xhinliang@gmail.com
 */
public class RegisterBody  {

    private int position;	/*3*/
    private int group_name;	/*2*/
    private String phone;	/*121*/
    private int sex;	/*1*/
    private String name;	/*wer123*/
    private String imgurl;	/*www.123456.com*/

    public RegisterBody(int position, int group_name, String phone, int sex, String name) {
        this.position = position;
        this.group_name = group_name;
        this.phone = phone;
        this.sex = sex;
        this.name = name;
    }
}
