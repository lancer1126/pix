package com.lance.pix.biz.web.illust.secmapper;

import com.lance.pix.common.po.Illustration;
import com.lance.pix.common.po.illust.ArtistPreView;
import com.lance.pix.common.util.json.JsonTypeHandler;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description 插画mapper
 */
public interface IllustrationBizMapper {

    @Select("select * from illusts where illust_id = #{illustId}")
    @Results({
            @Result(property = "id", column = "illust_id"),
            @Result(property = "artistPreView", column = "artist", javaType = ArtistPreView.class, typeHandler = JsonTypeHandler.class),
            @Result(property = "tools", column = "tools", javaType = List.class, typeHandler = JsonTypeHandler.class),
            @Result(property = "tags", column = "tags", javaType = List.class, typeHandler = JsonTypeHandler.class),
            @Result(property = "imageUrls", column = "image_urls", javaType = List.class, typeHandler = JsonTypeHandler.class),
            @Result(property = "tags", column = "tags", javaType = List.class, typeHandler = JsonTypeHandler.class),
            @Result(property = "artistId", column = "artist_id"),
            @Result(property = "pageCount", column = "page_count"),
            @Result(property = "sanityLevel", column = "sanity_level"),
            @Result(property = "totalBookmarks", column = "total_bookmarks"),
            @Result(property = "totalView", column = "total_view"),
            @Result(property = "xRestrict", column = "x_restrict"),
            @Result(property = "createDate", column = "create_date"),
    })
    Illustration queryIllustrationByIllustId(Integer illustId);
}