package com.heiyu.messaging.DAO;

import com.heiyu.messaging.model.User;
import com.heiyu.messaging.model.UserValidationCode;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserValidationCodeDAO {

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("INSERT INTO user_validation_code (user_id, validation_code)" +
            "VALUES (#{userId},#{validationCode})")
    void insert(UserValidationCode userValidationCode);

    @Select("SELECT id, user_id as userId, validation_code as validationCode FROM user_validation_code WHERE user_id=#{userId}")
    UserValidationCode findByUserId(int userId);

    @Delete("DELETE FROM user_validation_code WHERE id=#{id}")
    void deleteById(int id);
}

