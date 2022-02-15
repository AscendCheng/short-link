package com.cyx.utils;

import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;

public class IdUtil {
    private static SnowflakeShardingKeyGenerator shardingKeyGenerator = new SnowflakeShardingKeyGenerator();

    /**
     * 雪花算法生成器,配置workId，避免重复
     *
     * 10进制 654334919987691526
     * 64位 0000100100010100101010100010010010010110000000000000000000000110
     *
     * {@link SnowflakeShardingKeyGenerator}
     *
     * @return
     */
    public static Comparable<?> geneSnowFlakeID(){
        return shardingKeyGenerator.generateKey();
    }
}
