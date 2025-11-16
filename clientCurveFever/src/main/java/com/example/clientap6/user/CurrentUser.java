package com.example.clientap6.user;

public class CurrentUser {
    private User user;
    private static CurrentUser instance;

    public CurrentUser()  {
        instance = this;
    }

    public static CurrentUser getInstance() {
        if (instance == null)
            instance = new CurrentUser();
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
