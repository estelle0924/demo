package com.example.demo.lucene;

import com.alibaba.fastjson.JSON;
import org.testng.annotations.Test;

/**
 * @Author: estelle
 * @Description:
 * @Date: Created in 17:51 2019/1/5
 * @Modified By:
 */
public class TestClass {

    @Test
    public void testVoid(){
        testInte(1,2,3,4);
        String url=Class.class.getClass().getResource("/").getPath();
        System.out.println(url);
    }

    public void testInte(Integer... integers){
        for (int i=0;i<integers.length;i++){
            System.out.println(i+":"+JSON.toJSON(integers[i]));
        }
    }
}