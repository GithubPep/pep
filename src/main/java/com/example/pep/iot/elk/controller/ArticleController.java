package com.example.pep.iot.elk.controller;

import com.example.pep.iot.elk.index.Article;
import com.example.pep.iot.elk.req.ArticleReq;
import com.example.pep.iot.elk.service.ArticleService;
import com.example.pep.iot.response.ResultVO;
import org.springframework.web.bind.annotation.*;

import javax.smartcardio.ATR;
import java.util.List;

/**
 * @author LiuGang
 * @since 2022-04-19 15:08
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    private ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/add-list")
    public ResultVO<?> addArticle(@RequestBody List<ArticleReq> list) {
        articleService.addList(list);
        return ResultVO.ok();
    }

    @PostMapping("/query-all")
    public ResultVO<?> queryAll() {
        List<Article> articles = articleService.queryAll();
        return ResultVO.ok(articles);
    }

    @GetMapping("/query-by-userId")
    public ResultVO<List<Article>> queryByCondition(@RequestParam String id) {
        List<Article> articles = articleService.queryByUserId(id);
        return ResultVO.ok(articles);
    }

    @PostMapping("/query-by-time")
    public ResultVO<List<Article>> queryByTimeRange(@RequestBody ArticleReq articleReq) {
        List<Article> articles = articleService.queryByTimeRange(articleReq);
        return ResultVO.ok(articles);
    }


    @PostMapping("update-by-id")
    public ResultVO<List<Article>> updateById(@RequestBody ArticleReq articleReq) {
        articleService.updateById(articleReq);
        return ResultVO.ok();
    }

    @PostMapping("query-by-content")
    public ResultVO<List<Article>> queryByContent(@RequestBody ArticleReq articleReq) {
        List<Article> articles = articleService.queryByContent(articleReq);
        return ResultVO.ok(articles);
    }

    @PostMapping("query-by-content-title")
    public ResultVO<List<Article>> queryByContentAndTitle(@RequestBody ArticleReq articleReq) {
        List<Article> articles = articleService.queryByContentAndTitle(articleReq);
        return ResultVO.ok(articles);
    }

    @PostMapping("query-by-phrase-match")
    public ResultVO<List<Article>> phraseMatch(@RequestBody ArticleReq articleReq) {
        List<Article> articles = articleService.phraseMatch(articleReq);
        return ResultVO.ok(articles);
    }

    @PostMapping("query-by-prefix-match")
    public ResultVO<List<Article>> prefixMatch(@RequestBody ArticleReq articleReq){
        List<Article> articles = articleService.prefixMatch(articleReq);
        return ResultVO.ok(articles);
    }

    @PostMapping("query-by-prefix-phrase-match")
    public ResultVO<List<Article>> prefixPhraseMatch(@RequestBody ArticleReq articleReq){
        List<Article> articles = articleService.prefixPhraseMatch(articleReq);
        return ResultVO.ok(articles);
    }



}
