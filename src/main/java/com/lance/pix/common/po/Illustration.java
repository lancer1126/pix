package com.lance.pix.common.po;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.lance.pix.common.po.illust.ArtistPreView;
import com.lance.pix.common.po.illust.ImageUrl;
import com.lance.pix.common.po.illust.Tag;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description 插画
 */
@Data
public class Illustration {

    @JsonSetter("illust_id")
    @JsonAlias({"id", "illust_id"})
    protected Integer id;

    @JsonSetter("artist_id")
    @JsonAlias({"artist_id", "artistId"})
    protected Integer artistId;

    protected String title;
    protected String type;
    protected String caption;

    @JsonSetter("artist")
    @JsonAlias({"artist", "artistPreView"})
    protected ArtistPreView artistPreView;

    protected List<Tag> tags;

    @JsonSetter("image_urls")
    @JsonAlias({"image_urls", "imageUrls"})
    protected List<ImageUrl> imageUrls;

    protected List<String> tools;

    @JsonSetter("create_date")
    @JsonAlias({"create_date", "createDate"})
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    protected Date createDate;

    @JsonSetter("page_count")
    @JsonAlias({"page_count", "pageCount"})
    protected Integer pageCount;

    protected Integer width;
    protected Integer height;

    @JsonSetter("sanity_level")
    @JsonAlias({"sanity_level", "sanityLevel"})
    protected Integer sanityLevel;

    protected Integer restrict;

    @JsonSetter("x_restrict")
    @JsonAlias({"x_restrict", "xRestrict"})
    protected Integer xRestrict;

    @JsonSetter("total_view")
    @JsonAlias({"total_view", "totalView"})
    protected Integer totalView;

    @JsonSetter("total_bookmarks")
    @JsonAlias({"total_bookmarks", "totalBookmarks"})
    protected Integer totalBookmarks;

    public void setArtistPreView(Integer id, String name, String account, String avatar) {
        this.artistPreView = new ArtistPreView(id, name, account, avatar);
    }
}