package com.github.aaric.achieve.springjdbc;

import java.util.List;

/**
 * UserService
 *
 * @author Aaric, created on 2017-05-26T10:21.
 * @since 1.0-SNAPSHOT
 */
public interface UserRepository {

    int add(User user);
    int remove(Integer id);
    int update(User user);
    User getOne(Integer id);
    List<User> queryList();
}
