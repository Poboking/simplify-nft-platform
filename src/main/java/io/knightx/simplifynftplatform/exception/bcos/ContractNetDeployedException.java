package io.knightx.simplifynftplatform.exception.bcos;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/17 下午3:44
 */
@ExceptionMapper(code = "600", msg = "Exception: The Solidity Contract Net Yet Deployed.")
public class ContractNetDeployedException extends RuntimeException{
    public ContractNetDeployedException(String msg){
        super(msg);
    }
}
