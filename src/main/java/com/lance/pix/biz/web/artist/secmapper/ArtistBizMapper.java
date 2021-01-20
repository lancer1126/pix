package com.lance.pix.biz.web.artist.secmapper;

import com.lance.pix.common.po.Artist;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lancer1126
 * @Date 2020-12-6
 * @Description ArtistBizMapper
 */
public interface ArtistBizMapper {
    @Select("select * from artists where artist_id =#{artistId}")
    @Results({
            @Result(property = "id", column = "artist_id"),
    })
    Artist queryArtistById(Integer artistId);

    @Select("select illust_id from illusts where artist_id = #{artistId} and type = #{type} order by create_date desc  limit #{currIndex} , #{pageSize}")
    List<Integer> queryIllustrationsByArtistId(Integer artistId, String type, int currIndex, int pageSize);
}
