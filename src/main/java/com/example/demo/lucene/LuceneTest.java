package com.example.demo.lucene;

import com.alibaba.fastjson.JSON;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.testng.annotations.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;
import java.io.File;

/**
 * @Author: estelle
 * @Description:
 * @Date: Created in 13:41 2019/1/4
 * @Modified By:
 */
public class LuceneTest {
    String content1 = "你好,世界";
    String content2 = "你好,Java世界";
    String content3 = "你好,lucene世界";
    String indexPath = "sxx";
    Analyzer analyzer = new IKAnalyzer();//分词器

    @Test
    public void testCreateIndex() throws Exception {
        File file=new File(indexPath);
        //SimpleFSDirectory simpleFSDirectory=new SimpleFSDirectory();根据位置创建索引
        //1.创建索引写入器
        Directory d = SimpleFSDirectory.open(file);;//索引需要存放的位置
        //创建索引写入器配置对象
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_4_10_0, analyzer);
        IndexWriter writer = new IndexWriter(d, conf);
        //2.写入文档信息
        //添加文档 定义字段的存储规则
        FieldType type = new FieldType();
        type.setIndexed(true);//是否要索引
        type.setStored(true);//是否需要存储
        Document document1 = new Document();//数据库中的一条数据
        //new Field("字段名","字段内容","字段的配置属性")
        document1.add(new Field("title", "doc1", type));//该条记录中的字段 title:doc1
        document1.add(new Field("content", content1, type));//content: hello world
        IndexableField indexableField=document1.getField("doc1");
        if (indexableField!=null){
            document1.removeField("doc1");
        }
        writer.addDocument(document1);
        System.out.println(JSON.toJSON(document1));
        Document document2 = new Document();
        document2.add(new Field("title", "doc2", type));
        document2.add(new Field("content", content2, type));
        writer.addDocument(document2);
        System.out.println(JSON.toJSON(document2));
        Document document3 = new Document();
        document3.add(new Field("title", "doc3", type));
        document3.add(new Field("content", content3, type));
        writer.addDocument(document3);
        System.out.println(JSON.toJSON(document3));
        //需要把添加的记录保存
        writer.commit();
        writer.close();
        testSearch();
    }

    @Test
    public void testUpdate() throws Exception {
        //创建索引写入器
        Directory d = FSDirectory.open(new File(indexPath));
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_0, analyzer);
        IndexWriter writer = new IndexWriter(d, config);
        //更新对象
        Term term = new Term("title", "doc2");//更新的条件
        Document updateDoc = new Document();//更新之后的文档对象
        FieldType type = new FieldType();
        type.setIndexed(true);
        type.setStored(true);
        updateDoc.add(new Field("title", "doc2", type));
        updateDoc.add(new Field("content", "你好,黄河之水天上来吧我要更新内容啦", type));
        writer.updateDocument(term, updateDoc);
        //提交更新内容 释放资源
        writer.commit();
        writer.close();
        testSearch();
    }

    //索引查询过程
    @Test
    public void testSearch() throws Exception {
        //1.创建索引写入器
        Directory d = FSDirectory.open(new File(indexPath));
        //打开索引目录
        IndexReader r = DirectoryReader.open(d);
        IndexSearcher searcher = new IndexSearcher(r);
        QueryParser parser = new QueryParser("content", analyzer);
        Query query = parser.parse("你好");//查询hello
        //search(查询对象,符合条件的前n条记录)
        TopDocs search = searcher.search(query, 10000);//n:前几个结果
        System.out.println("符合条件的记录有多少个:" + search.totalHits);
        ScoreDoc[] scoreDocs = search.scoreDocs;
        Document doc = null;
        for (int i = 0; i < scoreDocs.length; i++) {
            System.out.println("*******************************");
            System.out.println("分数:" + scoreDocs[i].score);//相关度的排序
            int docId = scoreDocs[i].doc;//文档编号
            Document document = searcher.doc(docId);
            System.out.println("文档编号 docId--->" + docId);
            System.out.println("标题内容 title:--->" + document.get("title"));
            System.out.println("正文内容 content:--->" + document.get("content"));
        }
    }

    @Test
    public void searchIndex() throws Exception {
        //1.创建索引写入器
        Directory d = FSDirectory.open(new File(indexPath));
        //创建分词器
        Analyzer analyzer = new IKAnalyzer();//分词器
        //打开索引目录
        IndexReader r = DirectoryReader.open(d);
        //创建索引查询对象
        IndexSearcher searcher = new IndexSearcher(r);
        QueryParser parser = new QueryParser("content", analyzer);

        Query query = parser.parse("hello");//查询hello
        //search(查询对象,符合条件的前n条记录)
        TopDocs search = searcher.search(query, 10000);//n:前几个结果
        System.out.println("符合条件的记录有多少个:" + search.totalHits);

        ScoreDoc[] scoreDocs = search.scoreDocs;
        for (int i = 0; i < scoreDocs.length; i++) {
            System.out.println("*******************************");
            System.out.println("分数:" + scoreDocs[i].score);//相关度的排序
            int docId = scoreDocs[i].doc;//文档编号
            Document document = searcher.doc(docId);
            System.out.println("文档编号 docId--->" + docId);
            System.out.println("标题内容 title:--->" + document.get("content"));
        }
    }


}