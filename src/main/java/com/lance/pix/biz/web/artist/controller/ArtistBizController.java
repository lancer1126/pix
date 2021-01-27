package com.lance.pix.biz.web.artist.controller;

import com.lance.pix.basic.auth.annotation.PermissionRequired;
import com.lance.pix.basic.auth.constant.PermissionLevel;
import com.lance.pix.biz.userinfo.annotation.WithUserInfo;
import com.lance.pix.biz.web.artist.service.ArtistBizService;
import com.lance.pix.common.po.Illustration;
import com.lance.pix.common.po.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * @Author lancer1126
 * @Date 2021-1-27
 * @Description ArtistBizController
 */
@RestController
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArtistBizController {
    private final ArtistBizService artistBizService;

    @GetMapping("/artists/{artistId}/illusts/{type}")
    @PermissionRequired(PermissionLevel.ANONYMOUS)
    @WithUserInfo
    public ResponseEntity<Result<List<Illustration>>>
        queryIllustrationsByArtistId(@PathVariable Integer artistId, @PathVariable String type,
                                     @RequestParam(defaultValue = "1") @Max(333) int page, @RequestParam(defaultValue = "30") int pageSize,
                                     @RequestHeader(value = "Authorization", required = false) String token) {
        List<Illustration> illustrationList = artistBizService.queryIllustrationsByArtistId(artistId, type, (page - 1) * pageSize, pageSize);
        return ResponseEntity.ok().body(new Result<>("获取画师画作列表成功", illustrationList));
    }
}












