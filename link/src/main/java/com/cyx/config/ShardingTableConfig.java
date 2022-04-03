package com.cyx.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ShardingTableConfig.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/7
 */
public class ShardingTableConfig {
    /**
     * 存储数据表位置编号
     */
    private static final List<String> tableSuffixList = new ArrayList<>();

    private static Random random = new Random();

    //配置启用那些表的后缀
    static {
        tableSuffixList.add("0");
        tableSuffixList.add("a");
    }


    /**
     * 获取随机的后缀
     *
     * @return
     */
    public static String getRandomTableSuffix() {
        int index = random.nextInt(tableSuffixList.size());
        return tableSuffixList.get(index);
    }
}
