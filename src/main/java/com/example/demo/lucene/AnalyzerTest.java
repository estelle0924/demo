package com.example.demo.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.testng.annotations.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @Author: estelle
 * @Description:
 * @Date: Created in 14:19 2019/1/4
 * @Modified By:
 */
public class AnalyzerTest {

    String en = "good morning boy";
    String ch = "你好 恭喜发财 东方明珠三生三世十里桃花";

    @Test
    public void analyzerMethod(Analyzer analyzer, String content) throws Exception {
        TokenStream tokenStream = analyzer.tokenStream("content", content);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            System.out.println(tokenStream);
        }
    }

    @Test
    public void testIKAnalyzer() throws Exception {
        analyzerMethod(new IKAnalyzer(), ch);
    }
}