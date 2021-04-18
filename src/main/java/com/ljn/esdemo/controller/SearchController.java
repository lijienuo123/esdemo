package com.ljn.esdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class SearchController {

	/**
     * 8.0版本开始将完全移除TransportClient，使用RestHighLevelClient取代
     */
    @Resource
    RestHighLevelClient restHighLevelClient;

    @GetMapping("/index")
    public SearchResponse index() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest rq = new SearchRequest()
                //索引
                .indices("account")
                //各种组合条件
                .source(sourceBuilder);

        //请求
        System.out.println(rq.source().toString());
        return restHighLevelClient.search(rq, RequestOptions.DEFAULT);
    }

    @GetMapping("/query")
    public String query() throws IOException {

        MatchQueryBuilder matchQueryBuilder = QueryBuilders
                .matchQuery("name", "afred");

        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders
                .multiMatchQuery("入门", "name", "lastname");

        //查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(matchQueryBuilder)
                .must(multiMatchQueryBuilder);


        //分页
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(boolQueryBuilder)
                .from(0)
                .size(100)
                .timeout(new TimeValue(60, TimeUnit.SECONDS));

        //查询
        SearchRequest searchRequest = new SearchRequest()
                .allowPartialSearchResults(true)
				//在es7中使用_doc作为默认的type,并且es8中将会被移除
            	//.types("doc")
                .indices("account")
                .source(sourceBuilder);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("结果总数："+ response.getHits().getTotalHits().value);
        SearchHits hits = response.getHits();  //SearchHits提供有关所有匹配的全局信息，例如总命中数或最高分数：
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            log.info("search -> {}",hit.getSourceAsString());
        }
        return Arrays.toString(searchHits);
    }

    /*@GetMapping("/add")
    public String add() throws IOException, JSONException {
        Map<String, Object> source = new LinkedHashMap<>();
        source.put("name", "嘤嘤嘤?");
        source.put("lastname", "嘤嘤嘤!");
        source.put("job_description", "嘤嘤嘤");
        JSONObject jsonObject = new JSONObject(new LinkedHashMap<>());
        jsonObject.put("type", "person");
        jsonObject.put("source", new JSONObject(source));
        IndexRequest indexRequest = new IndexRequest("account").source(jsonObject.toJSONString(), XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        log.info("search -> {}", response.toString());
        return response.toString();
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest()
                .index("account")
                .id(id)
                .timeout(new TimeValue(60, TimeUnit.SECONDS));
        DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        log.info("search -> {}", response.toString());
        return response.toString();
    }

    @GetMapping("/update")
    public String update() throws IOException {
        Map<String, Object> source = new LinkedHashMap<>();
        source.put("name", "111?");
        source.put("lastname", "222!");
        source.put("job_description", "333");
        JSONObject jsonObject = new JSONObject(new LinkedHashMap<>());
        jsonObject.put("source", new JSONObject(source));
        UpdateRequest updateRequest = new UpdateRequest("account", "ad5ManMB1VCSKbBy8PTJ")
                .doc(jsonObject, XContentType.JSON)
                .retryOnConflict(3)
                .timeout(new TimeValue(60, TimeUnit.SECONDS));
        UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        log.info("search -> {}", response.toString());
        return response.toString();
    }
*/
}