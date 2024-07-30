package io.knightx.simplifynftplatform.exception.hold;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午10:33
 */
@ExceptionMapper(code = "400", msg = "Exception:HoldException Already exist.")
public class HoldCollectionAlreadyExistException extends RuntimeException {
    public HoldCollectionAlreadyExistException(String message) {
        super(message);
    }
}
