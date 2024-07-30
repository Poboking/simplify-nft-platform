package io.knightx.simplifynftplatform.exception.hold;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/26 下午3:23
 */

@ExceptionMapper(code = "500", msg = "Exception: HoldCollection Convert to Detail Resp Dto Failed, As Result of SQL Error.")
public class HoldCollectionConvertDetailRespException extends RuntimeException {
    public HoldCollectionConvertDetailRespException(String message) {
        super(message);
    }
}
