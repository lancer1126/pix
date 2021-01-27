package com.lance.pix.biz.crawler.pixiv.controller;

import com.lance.pix.biz.crawler.pixiv.service.IllustRankService;
import com.lance.pix.common.po.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lancer1126
 * @Date 2021-1-21
 * @Description 同步抓取Pixiv的Rank
 */
@RestController
@RequestMapping("/pixiv")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PixivicSyncController {
    private final IllustRankService illustRankService;

    /**
     * 抓取Pixiv每日排行
     * @param date  日期
     * @return      ranksf
     */
    @GetMapping("/reSyncRank")
    public ResponseEntity<Result<String>> reSyncRank(@RequestParam String date) {
        illustRankService.pullAllRank(date);
        return ResponseEntity.ok().body(new Result<>("抓取排行成功"));
    }
}