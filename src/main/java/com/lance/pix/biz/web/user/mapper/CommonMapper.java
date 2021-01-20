package com.lance.pix.biz.web.user.mapper;

import com.lance.pix.biz.web.common.po.User;
import org.apache.ibatis.annotations.*;

/**
 * @Author lancer1126
 * @Date 2020-12-8
 * @Description 用户相关mapper
 */
public interface CommonMapper {

    @Select({"SELECT IFNULL((SELECT 1 FROM users WHERE email=#{email} LIMIT 1),0)"})
    int checkUserEmail(String email);

    @Select({"SELECT IFNULL((SELECT 1 FROM users WHERE username=#{username} LIMIT 1),0)"})
    int checkUserName(String username);

    @Insert("insert into users(email, username,password,permission_level,is_ban,star,create_date) values (#{email}, #{username}, #{password}, " +
            "#{permissionLevel}, #{isBan},#{star},#{createDate,typeHandler=org.apache.ibatis.type.LocalDateTimeTypeHandler})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "user_id")
    Integer insertUser(User user);

    @Select({
            " SELECT * FROM (SELECT * FROM users WHERE username= #{username} OR email=#{username})  temp where temp.PASSWORD=#{password}",
    })
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "isBan", column = "is_ban"),
            @Result(property = "permissionLevel", column = "permission_level"),
            @Result(property = "pixivAccount", column = "pixiv_account"),
            @Result(property = "pixivPassword", column = "pixiv_password"),
            @Result(property = "qqOpenId", column = "qq_open_id"),
            @Result(property = "isCheckEmail", column = "is_check_email"),
            @Result(property = "createDate", column = "create_date", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class),
            @Result(property = "updateDate", column = "update_date", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class),
            @Result(property = "permissionLevelExpireDate", column = "permission_level_expire_date", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class )})
    User queryUserByusernameAndPassword(String username, String password);

    @Update("update users set avatar=#{avatar} where user_id=#{userId}")
    Integer setAvatar(String avatar, int userId);

    @Insert("insert into user_collection_summary (user_id) values (#{userId})")
    Integer initSummary(Integer userId);

    @Select({ "SELECT * FROM users WHERE user_id= #{userId} ",})
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "isBan", column = "is_ban"),
            @Result(property = "permissionLevel", column = "permission_level"),
            @Result(property = "pixivAccount", column = "pixiv_account"),
            @Result(property = "pixivPassword", column = "pixiv_password"),
            @Result(property = "qqOpenId", column = "qq_open_id"),
            @Result(property = "isCheckEmail", column = "is_check_email"),
            @Result(property = "createDate", column = "create_date", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class),
            @Result(property = "updateDate", column = "update_date", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class),
            @Result(property = "permissionLevelExpireDate", column = "permission_level_expire_date", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class )})
    User queryUserByUserId(int userId);
}
