package com.wecan.xhin.studio.api.post;

/**
 * Created by xhinliang on 15-11-19.
 * xhinliang@gmail.com
 */
public class BodyLogin {
    private String name;
    private String phone;

    public BodyLogin(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
