package io.knightx.simplifynftplatform.exception.member;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/17 下午10:33
 */
@ExceptionMapper(code = "500", msg = "Exception: The Member BlockChain Address Info is Deficiency.")
public class MemberAddressDeficiencyException extends RuntimeException{
    public MemberAddressDeficiencyException(String msg){
        super(msg);
    }
}
