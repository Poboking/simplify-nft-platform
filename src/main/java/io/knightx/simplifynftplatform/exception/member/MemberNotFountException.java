package io.knightx.simplifynftplatform.exception.member;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午10:16
 */
@ExceptionMapper(code = "400", msg = "Exception: Member Not Fount.")
public class MemberNotFountException extends RuntimeException {
    public MemberNotFountException(String msg) {
        super(msg);
    }
}
