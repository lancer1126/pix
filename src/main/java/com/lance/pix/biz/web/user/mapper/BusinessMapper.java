package com.lance.pix.biz.web.user.mapper;

import com.lance.pix.biz.web.user.dto.UserListDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description BusinessMapper
 */
public interface BusinessMapper {

    @Insert("insert into user_artist_followed (user_id,username, artist_id,create_date) " +
            "values (#{userId},#{username}, #{artistId}, #{now,typeHandler=org.apache.ibatis.type.LocalDateTimeTypeHandler})")
    int follow(int userId, int artistId, String username, LocalDateTime now);

    @Delete("delete from user_artist_followed where user_id=#{userId} and artist_id = #{artistId}")
    int cancelFollow(int userId, int artistId);

    @Insert("replace into user_illust_bookmarked (user_id, illust_id,illust_type,username,create_date) " +
            "values (#{userId}, #{illustId},#{illustType},#{username}, #{now,typeHandler=org.apache.ibatis.type.LocalDateTimeTypeHandler})")
    int bookmark(int userId, int illustId, Integer illustType, String username, LocalDateTime now);

    @Delete("delete from user_illust_bookmarked where user_id=#{userId} and illust_id=#{illustId} ")
    int cancelBookmark(int userId, int illustId);

    @Select("select illust_id from user_illust_bookmarked where user_id=#{userId} and illust_type=#{type} order by id desc limit #{currIndex} , #{pageSize}")
    List<Integer> queryBookmarked(int userId, Integer type, int currIndex, int pageSize);

    @Select("select artist_id from user_artist_followed where user_id = #{userId} order by create_date desc limit #{currIndex}, #{pageSize}")
    @Results({
            @Result(property = "id", column = "artist_id"),
    })
    List<Integer> queryFollowed(int userId, int currIndex, int pageSize);

    @Select("select illust_id\n" +
            "from (select artist_id from user_artist_followed where user_id = #{userId}) u " +
            "         join artist_illust_relation i on i.artist_id = u.artist_id " +
            "where i.illust_type = (case #{type} WHEN 'illust' THEN 1 " +
            "                     WHEN 'manga' THEN 2 " +
            "                     ELSE 3 end) " +
            "  and create_date >= (SELECT DATE_ADD(now(), INTERVAL -2 MONTH))")
    List<Integer> queryFollowedLatestIllustId(int userId, String type);

    @Select("select user_id,username,create_date from user_illust_bookmarked where illust_id=#{illustId} order by id desc  limit #{currIndex} , #{pageSize}")
    @Results({
            @Result(property = "illustId", column = "illust_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "createDate", column = "create_Date", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class)
    })
    List<UserListDTO> queryUserListBookmarkedIllust(Integer illustId, int currIndex, Integer pageSize);
}