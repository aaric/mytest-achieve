package com.github.aaric.achieve.mybatis;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRepository
 *
 * @author Aaric, created on 2017-05-26T11:36.
 * @since 1.0-SNAPSHOT
 */
@Mapper
@Repository
public interface UserRepository {

    int insert(User user);

    @Select("select * from t_user")
    List<User> queryList();

    @Select("select * from t_user where id = #{id}")
    User getOne(@Param("id") Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into t_user (name, email, password) values (#{name}, #{email}, #{password})")
    int add(User user);

    @Update("update t_user set name = #{name} where id = #{id}")
    int update(@Param("name") String name, @Param("id") Integer id);

    @Delete("delete from t_user where id = #{id}")
    int delete(@Param("id") Integer id);
}
