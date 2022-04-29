package com.example.pep.iot.elk.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import com.example.pep.iot.elk.index.Article;
import com.example.pep.iot.elk.repository.ArticleRepository;
import com.example.pep.iot.elk.req.ArticleReq;
import com.example.pep.iot.utils.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.*;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchPhrasePrefixQuery;
import static org.elasticsearch.index.query.QueryBuilders.prefixQuery;

/**
 * @author LiuGang
 * @since 2022-04-19 15:09
 */
@Slf4j
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    private ArticleService(ArticleRepository articleRepository, ElasticsearchRestTemplate elasticsearchTemplate) {
        this.articleRepository = articleRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public void addList(List<ArticleReq> list) {
        List<Article> articleList = StreamUtils.stream(list).map(item -> {
            Article article = BeanUtil.copyProperties(item, Article.class);
            article.setHidden(false);
            article.setPubTime(LocalDateTime.now());
            return article;
        }).collect(Collectors.toList());
        articleRepository.saveAll(articleList);
    }

    public List<Article> queryAll() {
        Iterable<Article> articles = articleRepository.findAll();
        return ListUtil.toList(articles);
    }


    /**
     * 不进行计算分数的term查询
     */
    public List<Article> queryByUserId(String userId) {
        ConstantScoreQueryBuilder queryBuilder = QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("userId", userId));
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryBuilder);
        SearchHits<Article> searchHits = elasticsearchTemplate.search(nativeSearchQuery, Article.class);
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    /**
     * 时间范围查询
     */
    public List<Article> queryByTimeRange(ArticleReq articleReq) {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("pubTime").gt(articleReq.getStartTime()).lt(articleReq.getEndTime());
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(rangeQueryBuilder);
        SearchHits<Article> searchHits = elasticsearchTemplate.search(nativeSearchQuery, Article.class);
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public void updateById(ArticleReq articleReq) {
        UpdateQuery.Builder builder = UpdateQuery.builder(articleReq.getId());
        Document document = Document.create();
        document.put("userId", articleReq.getUserId());
        builder.withDocument(document);
        elasticsearchTemplate.update(builder.build(), IndexCoordinates.of(Article.TYPE));
    }

    public List<Article> queryByContent(ArticleReq articleReq) {
        String content = articleReq.getContent();
        //最小包含 还可以用 operation 只可以选择 AND OR
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("content", content).minimumShouldMatch("67%");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(matchQueryBuilder);
        SearchHits<Article> searchHits = elasticsearchTemplate.search(nativeSearchQuery, Article.class);
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<Article> queryByContentAndTitle(ArticleReq articleReq) {
        String content = articleReq.getContent();
        String title = articleReq.getTitle();
        DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();
        MatchQueryBuilder contentMatchQueryBuilder = QueryBuilders.matchQuery("content", content);
        MatchQueryBuilder titleMatchQueryBuilder = QueryBuilders.matchQuery("title", title);
        disMaxQueryBuilder.add(contentMatchQueryBuilder).add(titleMatchQueryBuilder);
        //dis_max只取最大的query分数,加上tie_breaker将其他的query也计算进去
        disMaxQueryBuilder.tieBreaker();
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(disMaxQueryBuilder);
        SearchHits<Article> searchHits = elasticsearchTemplate.search(nativeSearchQuery, Article.class);
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<Article> multiMatch() {
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("content", "content^2", "title");
        multiMatchQueryBuilder
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                .tieBreaker(0.3f)
                .minimumShouldMatch("2/3");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(multiMatchQueryBuilder);
        SearchHits<Article> searchHits = elasticsearchTemplate.search(nativeSearchQuery, Article.class);
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<Article> phraseMatch(ArticleReq articleReq) {
        MatchPhraseQueryBuilder contentPhraseQuery = QueryBuilders.matchPhraseQuery("content", articleReq.getContent());
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(contentPhraseQuery);
        SearchHits<Article> searchHits = elasticsearchTemplate.search(nativeSearchQuery, Article.class);
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<Article> prefixMatch(ArticleReq articleReq) {
        PrefixQueryBuilder prefixQuery = prefixQuery("content", articleReq.getContent());
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(prefixQuery);
        SearchHits<Article> searchHits = elasticsearchTemplate.search(nativeSearchQuery, Article.class);
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    /**
     * 搜索推荐
     */
    public List<Article> prefixPhraseMatch(ArticleReq articleReq) {
        MatchPhrasePrefixQueryBuilder phrasePrefixQueryBuilder = matchPhrasePrefixQuery("content", articleReq.getContent());
        phrasePrefixQueryBuilder
                .slop(1)
                .maxExpansions(2);
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(phrasePrefixQueryBuilder);
        SearchHits<Article> searchHits = elasticsearchTemplate.search(nativeSearchQuery, Article.class);
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
