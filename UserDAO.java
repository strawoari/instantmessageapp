package com.heiyu.messaging.DAO;

import com.heiyu.messaging.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface UserDAO {

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("INSERT INTO user (username, nickname, password, email, address, " +
            "gender, register_time, is_valid, login_token, last_login_time) " +
            "    VALUES (#{username}, #{nickname}, #{password}, #{email}, #{address}, " +
            "#{gender}, #{registerTime}, #{isValid}, #{loginToken}, #{lastLoginTime})")
    void insert(User user); // user.getUsername();

    @Select("select * from user where username=#{username}")
    User findByUsername(String username);

    @Update("UPDATE user SET is_valid=1 WHERE id=#{id}")
    void updateToValid(int id);

    @Select("select * from user where email=#{email}")
    User findByEmail(String email);

    @Update("UPDATE user SET login_token=#{loginToken}, "+
            "last_login_time=#{lastLoginTime} WHERE id=#{id}")
    void login(String loginToken, Date lastLoginTime, int id);

    @Select("SELECT * FROM user WHERE login_token = #{loginToken}")
    User findByLoginToken(String loginToken);

    @Select("SELECT * FROM user WHERE id = #{userId}")
    User findById(int userId);
}

//class UserDAOImpl implements UserDAO {
//
//    @Override
//    public void insert(User user) {
//        // 1. "INSERT INTO user (username, nickname, ...) VALUES (#{username}, #{nickname}, ...)"
//        // 2. "INSERT INTO user (username, nickname, ...) VALUES ("George", "Alice"...);
//        // 3. connect to mysql
//        // 4. commit the query in the 2nd step to mysql
//        // fetch generated key -> setKk()
//        // 5. close connection
//    }
//}