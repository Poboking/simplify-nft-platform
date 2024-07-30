package io.knightx.simplifynftplatform.bcos.service.utils;

import io.knightx.simplifynftplatform.exception.bcos.EvmException;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/17 下午4:42
 */
public class StatusUtil {
    public static boolean checkTransactionStatus(String status){
        if ("0x0".equals(status)) {
            return true;
        }else {
            throw new EvmException(strToHexInt(status));
        }
        
    }

    public static int strToHexInt(String hex){
        if (hex.startsWith("0x")) {
            hex = hex.substring(2);
        }
        return Integer.parseInt(hex, 16);
    }
}
