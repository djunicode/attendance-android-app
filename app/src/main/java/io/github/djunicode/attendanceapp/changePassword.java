package io.github.djunicode.attendanceapp;

public class changePassword {
    String old_password,new_password;

    public changePassword(String old_password, String new_password) {
        this.old_password = old_password;
        this.new_password = new_password;
    }

    public String getOld_password() {
        return old_password;
    }

    public String getNew_password() {
        return new_password;
    }
}
