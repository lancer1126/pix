package com.lance.pix.biz.web.user.controller;

import com.lance.pix.basic.auth.annotation.PermissionRequired;
import com.lance.pix.basic.auth.constant.PermissionLevel;
import com.lance.pix.biz.userinfo.annotation.WithUserInfo;
import com.lance.pix.biz.web.user.dto.ArtistWithRecentlyIllusts;
import com.lance.pix.biz.web.user.po.BookmarkRelation;
import com.lance.pix.biz.web.user.po.FollowedRelation;
import com.lance.pix.biz.web.user.service.BusinessService;
import com.lance.pix.common.constant.AuthConstant;
import com.lance.pix.common.context.AppContext;
import com.lance.pix.common.po.Illustration;
import com.lance.pix.common.po.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.List;


/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description user相关业务
 */
@RestController
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@PermissionRequired
@RequestMapping("/users")
public class BusinessController {
    private final BusinessService businessService;

    /**
     * 关注一个artist
     * @param followedRelation user与artist关联
     */
    @PostMapping("/followed")
    public ResponseEntity<Result<String>> follow(@RequestBody @Valid FollowedRelation followedRelation,
                                                 @RequestHeader("Authorization") String token) {
        businessService.follow((Integer) AppContext.get().get(AuthConstant.USER_ID),followedRelation.getArtistId(),followedRelation.getUsername());
        return ResponseEntity.ok().body(new Result<>("follow成功"));
    }

    /**
     * 取消关注一个artist
     * @param followedRelation  user与artist关联表
     */
    @DeleteMapping("/followed")
    public ResponseEntity<Result<String>> cancelFollow(@RequestBody FollowedRelation followedRelation, @RequestHeader("Authorization") String token) {
        businessService.cancelFollow((Integer) AppContext.get().get(AuthConstant.USER_ID),followedRelation.getArtistId());
        return ResponseEntity.ok().body(new Result<>("取消follow成功"));
    }

    /**
     * 收藏一个画作
     * @param bookmarkRelation  收藏关系
     */
    @PostMapping("/bookmarked")
    public ResponseEntity<Result<String>> bookmark(@RequestBody @Valid BookmarkRelation bookmarkRelation, @RequestHeader("Authorization") String token) {
        businessService.bookmark((Integer) AppContext.get().get(AuthConstant.USER_ID), bookmarkRelation.getUsername(), bookmarkRelation.getIllustId());
        return ResponseEntity.ok().body(new Result<>("收藏成功"));
    }


    /**
     * 取消收藏某个画作
     */
    @DeleteMapping("/bookmarked")
    public ResponseEntity<Result<String>> cancelBookmark(@RequestBody @Valid BookmarkRelation bookmarkRelation, @RequestHeader ("Authorization") String token) {
        businessService.cancelBookmark((Integer) AppContext.get().get(AuthConstant.USER_ID), bookmarkRelation.getIllustId(), bookmarkRelation.getId());
        return ResponseEntity.ok().body(new Result<>("取消收藏成功"));
    }

    /**
     * 获取所有收藏的画作
     * @param userId        用户id
     * @param type          illust或maga
     * @param page          当前页数
     * @param pageSize      一页的大小
     * @param token         token
     */
    @GetMapping("/{userId}/bookmarked/{type}")
    @PermissionRequired(PermissionLevel.ANONYMOUS)
    @WithUserInfo
    public ResponseEntity<Result<List<Illustration>>> queryBookmark(@PathVariable Integer userId, @PathVariable String type,
                                                                    @RequestParam(defaultValue = "1") @Max(300) int page, @RequestParam(defaultValue = "30")
                                                                        @Max(30) int pageSize, @RequestHeader(value = "Authorization", required = false) String token) {
        List<Illustration> illustrations = businessService.queryBookmark(userId, type, (page - 1) * pageSize, pageSize);
        return ResponseEntity.ok().body(new Result<>("获取收藏的画作成功",illustrations));
    }

    /**
     * 获取用户关注的画师以及其最近的插画
     * @param userId        用户id
     * @param type          illust or maga
     * @param page          当前页数
     * @param pageSize      一页大小
     * @param token         token
     * @return              ArtistWithRecentlyIllusts
     */
    @GetMapping("{/userId}/followedWithRecentlyIllusts")
    @PermissionRequired(PermissionLevel.ANONYMOUS)
    @WithUserInfo
    public ResponseEntity<Result<List<ArtistWithRecentlyIllusts>>> queryFollowedWithRecentlyIllusts(
            @PathVariable Integer userId, @PathVariable String type, @RequestParam(defaultValue = "1") @Max(150) int page,
            @RequestParam(defaultValue = "30") @Max(30) int pageSize, @RequestHeader("Authorization") String token) {
        List<ArtistWithRecentlyIllusts> artists = businessService.queryFollowedWithRecentlyIllusts(userId, (page - 1) * pageSize, pageSize);
        return ResponseEntity.ok().body(new Result<>("获取带有近期画作的follow画师列表成功", artists));
    }

}
















