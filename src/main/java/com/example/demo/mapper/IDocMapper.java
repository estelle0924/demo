package com.example.demo.mapper;

import com.example.demo.bean.DocInfo;
import org.apache.ibatis.annotations.Mapper;

import java.awt.*;
import java.util.List;

@Mapper
public interface IDocMapper {//餐饮信息数据

    /**
     * 查询餐饮数据信息
     * @return
     */
    List<DocInfo> selectDishes();

    Integer insertDate();

    Integer into(String name);

    Integer update();

}