package com.mahmoud.mahmoudapp.Entity;

public class RegisterEntity {
    private String email;
    private String password;
    private String last_name;
    private String cell;

    public RegisterEntity(String email, String password, String last_name, String cell) {
        this.email = email;
        this.password = password;
        this.last_name = last_name;
        this.cell = cell;
    }
}
