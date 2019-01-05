package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.bean.DocInfo;
import com.example.demo.mapper.IDocMapper;
import com.example.demo.service.IDocService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: estelle
 * @Description:
 * @Date: Created in 15:29 2019/1/3
 * @Modified By:
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private IDocMapper mapper;

    String indexPath = "DocService";

    @RequestMapping(value = "/say/{id}",method= RequestMethod.GET)
    @ResponseBody
    public List say(@PathVariable String id) throws Exception{
        Date date=new Date();
        //1.创建索引写入器
        Directory d = FSDirectory.open(new File(indexPath));
        //创建分词器
        Analyzer analyzer = new IKAnalyzer();
        //打开索引目录
        IndexReader r = DirectoryReader.open(d);
        //创建索引查询对象
        IndexSearcher searcher = new IndexSearcher(r);

        QueryParser parser = new QueryParser("docId", analyzer);
        Query query = parser.parse(id);//查询测试数据
        //search(查询对象,符合条件的前n条记录)
        TopDocs search = searcher.search(query, 10000);//n:前几个结果
        System.out.println("符合条件的记录有多少个:" + search.totalHits);
        List<DocInfo> list=new ArrayList<DocInfo>();
        ScoreDoc[] scoreDocs = search.scoreDocs;
        List<Document> list1=new ArrayList<>();
        for (int i = 0; i < scoreDocs.length; i++) {
            DocInfo docInfo=new DocInfo();
            int docId = scoreDocs[i].doc;//文档编号
            System.out.println("文档编号 docId--->" + docId);
            Document document = searcher.doc(docId);
            list1.add(document);
            System.out.println(JSON.toJSON(document));
            docInfo.setDocId(Integer.valueOf(document.get("docId")));
            docInfo.setClassPath(document.get("classPath"));
            docInfo.setAuthor(document.get("author"));
            docInfo.setSubhead(document.get("subhead"));
            docInfo.setSubject(document.get("subject"));
            list.add(docInfo);
            System.out.println("文章内容 subject:--->" + document.get("subject"));
            /*document.add(new Field("id", docInfo.getClassId()+"", noType));
            document.add(new Field("classPath", docInfo.getClassPath(), noType));//该条记录中的字段 title:doc1
            document.add(new Field("author", docInfo.getAuthor(), type));//作者
            document.add(new Field("subhead", docInfo.getSubhead(), type));
            document.add(new Field("subject", docInfo.getSubject(), type));
            System.out.println("*******************************");
            System.out.println("分数:" + scoreDocs[i].score);//相关度的排序
            System.out.println("标题内容 title:--->" + document.get("content"));*/
        }
        System.out.println(JSON.toJSON(list));
        mapper.into("查询开始时间:{}"+date+"结束时间"+new Date()+"查询一条数据");
        return list;
    }



    @RequestMapping(value = "/sayTest",method= RequestMethod.GET)
    @ResponseBody
    public List sayTest() throws Exception{
        List list=mapper.selectDishes();
        return list;
    }

}