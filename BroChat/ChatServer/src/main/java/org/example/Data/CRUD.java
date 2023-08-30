package org.example.Data;

import org.example.Authorization.User;

public interface CRUD {
    void create(User user);
    User read(String login, String password);
    void update(User user);
    void delete(User user);
}
