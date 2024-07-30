package io.knightx.simplifynftplatform.exception.hold;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午5:09
 */
@ExceptionMapper(code = "500", msg = "Exception: HoldCollection Not Found.")
public class HoldCollectionNotFountException extends RuntimeException {
    public HoldCollectionNotFountException(String msg) {
        super(msg);
    }
}
