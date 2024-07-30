package io.knightx.simplifynftplatform.exception.bcos;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/17 下午3:31
 */
@ExceptionMapper(code = "600", msg = "Exception: The FiscoBcos Config Error, Gain BlockChain Account Address Failed.")
public class GainBlockChainAccountException extends RuntimeException {
    public GainBlockChainAccountException(String msg) {
        super(msg);
    }
}
