package com.cyx.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ShardingDBConfig.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/7
 */
public class ShardingDBConfig {

    /**
     * 存储数据库位置编号
     */
    private static final List<String> dbPrefixList = new ArrayList<>();

    private static Random random = new Random();

    //配置启用那些库的前缀
    static {
        dbPrefixList.add("0");
        dbPrefixList.add("1");
        dbPrefixList.add("a");
    }


    /**
     * 获取随机的前缀
     *
     * @return
     */
    public static String getRandomDBPrefix(String code) {
        int hash = code.hashCode();
        int index = Math.abs(hash) % dbPrefixList.size();
        return dbPrefixList.get(index);
    }
}
