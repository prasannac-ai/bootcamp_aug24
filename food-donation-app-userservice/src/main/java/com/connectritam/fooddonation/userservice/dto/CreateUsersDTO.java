package com.connectritam.fooddonation.userservice.dto;

public class CreateUsersDTO extends UsersDTO {

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}