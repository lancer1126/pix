package com.lance.pix.biz.userinfo.dto;

import com.lance.pix.common.po.Artist;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author lancer1126
 * @Date 2020-12-6
 * @Description ArtistWithIsFollowedInfo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ArtistWithIsFollowedInfo extends Artist {
    protected Boolean isFollowed;

    public ArtistWithIsFollowedInfo(Artist artist, Boolean isFollowed) {
        this.isFollowed = isFollowed;
        this.id = artist.getId();
        this.name = artist.getName();
        this.account = artist.getAccount();
        this.avatar = artist.getAvatar();
        this.comment = artist.getComment();
        this.gender = artist.getGender();
        this.birthDay = artist.getBirthDay();
        this.region = artist.getRegion();
        this.webPage = artist.getWebPage();
        this.twitterAccount = artist.getTwitterAccount();
        this.twitterUrl = artist.getTwitterUrl();
        this.totalFollowUsers = artist.getTotalFollowUsers();
        this.totalIllustBookmarksPublic = artist.getTotalIllustBookmarksPublic();
    }

    public ArtistWithIsFollowedInfo(Artist artist) {
        this.id = artist.getId();
        this.name = artist.getName();
        this.account = artist.getAccount();
        this.avatar = artist.getAvatar();
        this.comment = artist.getComment();
        this.gender = artist.getGender();
        this.birthDay = artist.getBirthDay();
        this.region = artist.getRegion();
        this.webPage = artist.getWebPage();
        this.twitterAccount = artist.getTwitterAccount();
        this.twitterUrl = artist.getTwitterUrl();
        this.totalFollowUsers = artist.getTotalFollowUsers();
        this.totalIllustBookmarksPublic = artist.getTotalIllustBookmarksPublic();
    }
}