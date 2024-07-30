package io.knightx.simplifynftplatform.exception.bcos;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/17 下午3:33
 */
@ExceptionMapper(code = "600",msg = "Exception:The FiscoBcos Config Error, The Config Not configured yet.")
public class BlockChainConfigurationException extends RuntimeException{
    public BlockChainConfigurationException(String msg){
        super(msg);
    }
}
