package com.cyx.strategy;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * CustomDBPreciseShardingAlgorithm.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/1
 */
public class CustomTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    /**
     * 获取分片后的库或表
     *
     * @param availableTargetNames 分库时->分片库集合databaseNames，分表时->分片表集合tableNames
     * @param preciseShardingValue 分片属性：logicTableName 逻辑表名称, String columnName 分片键（字段）, value 从sql中解析的分片键的值
     * @return
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> preciseShardingValue) {
        // 获取逻辑表
        String tableName = availableTargetNames.iterator().next();

        String value = preciseShardingValue.getValue();

        // 获取分片键的值上的库表信息
        String codePrefix = value.substring(value.length() - 1);

        return tableName.concat("_").concat(codePrefix);

    }
}
