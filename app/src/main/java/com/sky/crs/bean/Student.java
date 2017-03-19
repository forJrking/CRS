package com.sky.crs.bean;

public class Student {
    public String name;
    public String studentid;
    public int gender;
    public String college;
    public String avatar;
    public String psd;
    public int perm;


    public int getPerm() {
        if (perm != 0 && perm != 1 && perm != 2) {
            return perm = 0;
        }
        return perm;
    }

    public void setPerm(int perm) {
        this.perm = perm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getPerStr() {
        if (2 == perm) {
            return "教导员";
        } else if (1 == perm) {
            return "学生班委";
        } else {
            return "普通学生";
        }

    }

    public boolean isMan() {
        return gender == 1;
    }
}
