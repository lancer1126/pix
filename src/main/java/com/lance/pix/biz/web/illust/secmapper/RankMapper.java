package com.lance.pix.biz.web.illust.secmapper;

import com.lance.pix.biz.web.illust.po.Rank;
import com.lance.pix.common.util.json.JsonTypeHandler;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lancer1126
 * @Date 2020-12-6
 * @Description 排行榜mapper
 */
public interface RankMapper {

    @Select("select * from ranks where date = #{date} and mode= #{mode} limit 1")
    @Results({
            @Result(property = "date", column = "date"),
            @Result(property = "mode", column = "mode"),
            @Result(property = "data", column = "data", javaType = List.class, typeHandler = JsonTypeHandler.class)
    })
    Rank queryByDateAndMode(String date, String mode);

    @Select("select * from ranks where date = #{date}")
    @Results({
            @Result(property = "date", column = "date"),
            @Result(property = "mode", column = "mode"),
            @Result(property = "data", column = "data", javaType = List.class, typeHandler = JsonTypeHandler.class)
    })
    List<Rank> queryByDate(String date);
}
