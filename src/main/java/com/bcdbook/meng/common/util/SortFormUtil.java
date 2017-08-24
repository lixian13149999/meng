package com.bcdbook.meng.common.util;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author summer
 * @Date 2017/8/24 下午2:52
 */
public class SortFormUtil {

    public static Map<String,Integer> parseSortForm(String sortForm){
        if(StringUtils.isEmpty(sortForm)){
            return null;
        }

        //转字符串为list集合
        List<HashMap> sortFormList = JSON.parseArray(sortForm, HashMap.class);
        Map<String,Integer> sortFormMaps = new HashMap<String,Integer>();
        //封装list为单个map集合,此过程可以过滤掉重复的值
        for (Map<String,Integer> sortFormMap : sortFormList) {
            for (String key:sortFormMap.keySet()) {
                sortFormMaps.put(key,sortFormMap.get(key));
            }
        }

        return sortFormMaps;
    }
}
