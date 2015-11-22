package com.mahmoud.mahmoudapp.Entity;

public class UserResponse {
    private int id;
    private String email;
    private String last_name;
    private String cell;

    public UserResponse(int id, String email, String last_name, String cell) {
        this.id = id;
        this.email = email;
        this.last_name = last_name;
        this.cell = cell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }
}
