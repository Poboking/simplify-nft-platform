package io.knightx.simplifynftplatform.exception.member;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/24 下午3:40
 */
@ExceptionMapper(code = "500", msg = "Exception: The member is not yet on the chain, Please wait a moment to try.")
public class MemberNotOnChainException extends RuntimeException {
    public MemberNotOnChainException(String msg) {
        super(msg);
    }
}
