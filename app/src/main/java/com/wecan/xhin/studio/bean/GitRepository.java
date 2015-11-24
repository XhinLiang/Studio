package com.wecan.xhin.studio.bean;

/**
 * Created by xhinliang on 15-11-23.
 * xhinliang@gmail.com
 */
public class GitRepository {
    public String author;
    public String name;
    public String url;

    public GitRepository(String author, String name, String url) {
        this.author = author;
        this.name = name;
        this.url = url;
    }


    @Override
    public String toString() {
        return String.format("%s / %s", author, name);
    }
}
