package com.lance.pix.biz.web.user.dto;

import com.lance.pix.biz.userinfo.dto.ArtistWithIsFollowedInfo;
import com.lance.pix.common.po.Artist;
import com.lance.pix.common.po.Illustration;
import lombok.Data;

import java.util.List;

/**
 * @Author lancer1126
 * @Date 2020-12-6
 * @Description 关注的画师以及其最近的几幅画作
 */
@Data
public class ArtistWithRecentlyIllusts extends ArtistWithIsFollowedInfo {
    private List<Illustration> recentlyIllustrations;

    public ArtistWithRecentlyIllusts(Artist artist, List<Illustration> illustrations) {
        super(artist);
        if (artist instanceof ArtistWithIsFollowedInfo) {
            this.isFollowed = ((ArtistWithIsFollowedInfo) artist).getIsFollowed();
        } else {
            this.isFollowed = null;
        }
        this.recentlyIllustrations = illustrations;
    }
}