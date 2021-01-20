package com.lance.pix.biz.web.illust.controller;

import com.lance.pix.basic.auth.annotation.PermissionRequired;
import com.lance.pix.basic.auth.constant.PermissionLevel;
import com.lance.pix.biz.userinfo.annotation.WithUserInfo;
import com.lance.pix.biz.web.illust.service.RankService;
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
 * @Date 2020-12-6
 * @Description 首页的排行
 */
@RestController
@RequestMapping("/ranks")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RankController {

    private final RankService rankService;

    @GetMapping
    @WithUserInfo
    @PermissionRequired(PermissionLevel.ANONYMOUS)
    public ResponseEntity<Result<List<Illustration>>> queryByDateAndMode(@RequestParam String date, @RequestParam String mode,
                                                                   @RequestParam(defaultValue = "1") @Max(30) int page, @RequestParam(defaultValue = "30") int pageSize,
                                                                   @RequestHeader(value = "Authorization", required = false) String token) {
        List<Illustration> rank = rankService.queryByDateAndMode(date, mode, page, pageSize);
        return ResponseEntity.ok().body(new Result<>("获取排行成功",rank));
    }
}