package io.knightx.simplifynftplatform.bcos.service.utils;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/17 下午3:29
 */
public class StringUtil {
    /**
     * 分割字符串
     * @param input 要分割的输入字符串
     * @return 分割后的字符串数组
     */
    public static String[] splitString(String input) {
        if (input == null || input.isEmpty()) {
            return new String[0];
        }
        return input.split(";");
    }

    /**
     * 接收16进制字符串, 转换为Long
     * @param hex 参入16进制字符串
     * @return BigInt
     */
    public static BigInteger hexToBigInt(String hex) {
        // 处理异常情况
        if (hex == null || hex.isEmpty()) {
            throw new IllegalArgumentException("The hex string cannot be null or empty.");
        }
        // 去除前缀
        if (hex.startsWith("0x")) {
            hex = hex.substring(2);
        }
        // 将十六进制字符串转换为 BigInteger
        return new BigInteger(hex, 16);
    }


    public static String buildContent(String... params){
        StringBuilder builder = new StringBuilder();
        Arrays.stream(params).forEach(param -> builder.append(param).append(";"));
        return builder.toString();
    }
}
