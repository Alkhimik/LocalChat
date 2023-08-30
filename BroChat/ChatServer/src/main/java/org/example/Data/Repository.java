package org.example.Data;

import org.example.Authorization.User;
import org.example.Data.SpringConfiguration.UserMapper;
import org.example.Data.SpringConfiguration.idMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public record Repository(JdbcTemplate jdbcTemplate) implements CRUD {
    @Override
    public void create(User user) {
        jdbcTemplate.update("INSERT INTO chatServerData.userInfo VALUES(?,?,?)", user.getId(), user.getLogin(), user.getPassword());
    }

    @Override
    public User read(String login, String password) {
        return jdbcTemplate.query("SELECT * FROM chatServerData.userInfo WHERE login=? AND password=?", new Object[]{login, password}, new UserMapper())
                .stream().findAny().orElse(null);
    }

    public Optional<Integer> findMaxID() {
        Optional<Integer> id = jdbcTemplate.query("SELECT MAX(id) FROM chatServerData.userInfo", new idMapper()).stream().findAny();
        return id;
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("UPDATE chatServerData.userInfo SET login=?, password=? WHERE id = ?", user.getLogin(), user.getPassword(), user.getId());

    }

    @Override
    public void delete(User user) {
        jdbcTemplate.update("DELETE FROM chatServerData.userInfo WHERE id=? AND password=?", user.getId(), user.getPassword());

    }

    public User getById(int id) {
        return jdbcTemplate.query("SELECT * FROM chatServerData.userInfo WHERE id = ?", new Object[]{id}, new UserMapper())
                .stream().findAny().orElse(null);
    }

    public List<User> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM chatServerData.userInfo", new UserMapper());
    }
}
