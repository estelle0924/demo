package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.bean.DocInfo;
import com.example.demo.mapper.IDocMapper;
import com.example.demo.service.IDocService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @Author: estelle
 * @Description:
 * @Date: Created in 16:20 2019/1/3
 * @Modified By:
 */
@Service
public class DocServiceImpl implements IDocService {

    String indexPath = "DocService";
    Analyzer analyzer = new StandardAnalyzer();//分词器
    @Autowired
    private IDocMapper mapper;

    @Override
    public Integer OpenTask() throws Exception {
        //1.创建索引写入器
        Directory d = FSDirectory.open(new File(indexPath));//索引需要存放的位置
        //创建索引写入器配置对象
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_4_10_0, analyzer);
        IndexWriter writer = new IndexWriter(d, conf);
        //2.写入文档信息
        //添加文档 定义字段的存储规则
        FieldType type = new FieldType();
        type.setIndexed(true);//是否要索引
        type.setStored(true);//是否需要存储
        //type.setTokenized(false);//字段是否分词
        FieldType noType = new FieldType();
        noType.setIndexed(true);//是否要索引
        noType.setStored(true);//是否需要存储
        noType.setTokenized(false);//字段是否分词
        List<DocInfo> list = mapper.selectDishes();
        //System.out.println("查询数据条数:{}"+list.size());
        for (DocInfo docInfo : list) {
            Term term = new Term("docId", docInfo.getDocId()+"");//更新的条件
            Document document = new Document();//数据库中的一条数据
            //new Field("字段名","字段内容","字段的配置属性")
            document.add(new Field("docId", docInfo.getDocId()+"", noType));
            document.add(new Field("classPath", docInfo.getClassPath(), noType));//该条记录中的字段 title:doc1
            document.add(new Field("author", docInfo.getAuthor(), type));//作者
            document.add(new Field("subhead", docInfo.getSubhead(), type));
            document.add(new Field("subject", docInfo.getSubject(), type));
            document.add(new Field("textPC", docInfo.getTextPC(), type));
            document.add(new Field("textMobile", docInfo.getTextMobile(), type));
            document.add(new Field("classId", docInfo.getClassId()+"", noType));
            //IndexableField indexableField=document.getField(docInfo.getClassId()+"");
            String caozuo="";
            Date date=new Date();
            try{

                Integer result=testSearch("docId",docInfo.getDocId()+"");//根据id查询
                //System.out.println("result:{}"+result);
                if (result>0){
                    caozuo="更新";
                    writer.updateDocument(term, document);
                    System.out.println("更新索引");
                }else{
                    caozuo="添加";
                    writer.addDocument(document);
                    //System.out.println("添加索引");
                }

            }catch(Exception e){
                writer.addDocument(document);
                e.printStackTrace();
                //System.out.println("读取索引失败,首次添加索引");
            }
            //mapper.into("开始时间{}"+date+"结束时间{}"+new Date()+caozuo+"数据一条操作");
        }
        //需要把添加的记录保存
        writer.commit();
        writer.close();

        return list.size();
    }

    public int testSearch(String content,String parse) throws Exception {
        //1.创建索引写入器
        Directory d = FSDirectory.open(new File(indexPath));
        //打开索引目录
        IndexReader r = DirectoryReader.open(d);
        IndexSearcher searcher = new IndexSearcher(r);
        QueryParser parser = new QueryParser(content, analyzer);
        Query query = parser.parse(parse);//查询hello
        //search(查询对象,符合条件的前n条记录)
        TopDocs search = searcher.search(query, 10000);//n:前几个结果
        //System.out.println(JSON.toJSON(search));
        //System.out.println("符合条件的记录有多少个:" + search.totalHits);
        ScoreDoc[] scoreDocs = search.scoreDocs;
        return scoreDocs.length;
    }
}