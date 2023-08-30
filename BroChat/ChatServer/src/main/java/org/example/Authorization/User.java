package org.example.Authorization;

public class User {
    private int id;
    private String login;
    private String password;
    public User(int id,String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(){}
    public String getPassword() {
        return password;
    }
    public int getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    @Override
    public String toString(){
        return "id = " + id + ", login = " + login + ", password = "  + password;
    }
}
