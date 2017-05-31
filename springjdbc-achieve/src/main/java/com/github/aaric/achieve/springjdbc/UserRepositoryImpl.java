package com.github.aaric.achieve.springjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserServiceImpl
 *
 * @author Aaric, created on 2017-05-26T10:24.
 * @since 1.0-SNAPSHOT
 */
@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(User user) {
        String sql = "insert into t_user (name, email, password) values (?, ?, ?)";
        return jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword());
    }

    @Override
    public int remove(Integer id) {
        String sql = "delete from t_user where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int update(User user) {
        String sql = "update t_user set name = ?, email = ?, password = ? where id = ?";
        return jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getId());
    }

    @Override
    public User getOne(Integer id) {
        String sql = "select * from t_user where id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    @Override
    public List<User> queryList() {
        String sql = "select * from t_user";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }
}
