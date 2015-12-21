package com.wecan.xhin.studio.bean.up;

/**
 * Created by xhinliang on 15-11-23.
 * xhinliang@gmail.com
 */
public class RegisterBody {

    private int position;
    private int group_name;
    private int sex;
    private String phone;
    private String name;
    private String imgurl;
    private String code;
    private String description;

    public RegisterBody(int position, int group_name, String phone, int sex, String name, String code) {
        this.position = position;
        this.group_name = group_name;
        this.phone = phone;
        this.sex = sex;
        this.name = name;
        this.code = code;
        this.imgurl = "";
        this.description = "";
    }
}
