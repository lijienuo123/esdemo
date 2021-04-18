package com.ljn.esdemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljn.esdemo.EsdemoApplication;
import com.ljn.esdemo.entity.User;
import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

/**
 * @author ljn
 * @version 1.0
 * @date 2021/3/7 0007 16:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsdemoApplication.class, webEnvironment
        = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EsController {
    // 服务端日志

    private static Logger logger = Logger.getLogger(EsController.class);
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    // 创建索引
    @Test
    public void test() throws IOException {
        // 1、创建索引请求
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("kuang_index");
        // 2、执行请求
        CreateIndexResponse createIndexResponse =
                restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);

        System.out.println(createIndexResponse);
    }

    // 测试index是否存在
    @Test
    public void existIndex() throws IOException {
        GetIndexRequest kuang_index = new GetIndexRequest("kuang_index");
        boolean exists = restHighLevelClient.indices().exists(kuang_index, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    // 删除index
    @Test
    public void deleteIndex() throws IOException {
        GetIndexRequest kuang_index = new GetIndexRequest("kuang_index");
        boolean exists = restHighLevelClient.indices().exists(kuang_index, RequestOptions.DEFAULT);
        if (exists) {
            DeleteIndexRequest deleteIndex = new DeleteIndexRequest("kuang_index");
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndex, RequestOptions.DEFAULT);
            System.out.println(delete.isAcknowledged());
        }
    }

    // 添加数据
    @Test
    public void addData() throws IOException {
        User user = new User(1, "李皆诺");
        IndexRequest kuang_index = new IndexRequest("kuang_index");
        kuang_index.id("1");
        kuang_index.source(JSON.toJSONString(user), XContentType.JSON);

        // 客户发送请求
        IndexResponse indexResponse = restHighLevelClient.index(kuang_index, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());
    }

    //  指定索引index的指定id是否存在
    @Test
    public void existIndexById() throws IOException {
        GetRequest kuang_index = new GetRequest("kuang_index", "1");
        // 用于判断的时候，不需要获取上下文
        //kuang_index.fetchSourceContext(new FetchSourceContext(false));
        //kuang_index.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(kuang_index, RequestOptions.DEFAULT);
        System.out.println(exists);
        if (exists) {
            GetResponse documentFields = restHighLevelClient.get(kuang_index, RequestOptions.DEFAULT);
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            System.out.println(documentFields.getSourceAsString());
            User u = JSONObject.parseObject(documentFields.getSourceAsString(), User.class);
            logger.debug("Query request=" + u );
            System.out.println(u.getId());
        }
    }

    // 修改指定索引index的指定id文档
    @Test
    public void updateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("kuang_index", "1");
        // 用于判断的时候，不需要获取上下文
        updateRequest.fetchSource(new FetchSourceContext(false));

        updateRequest.doc(JSON.toJSONString(new User().setName("李皆诺yyy00")), XContentType.JSON);

        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(update.status());
        System.out.println(update.toString());
        System.out.println(update.getGetResult());
    }

    // 删除指定索引index的指定id文档
    @Test
    public void deleteDocument() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("kuang_index", "1");

        // 若deleteRequest不存在 该怎么办？

        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.status());
        System.out.println(deleteResponse.toString());
    }


}
