package io.knightx.simplifynftplatform.exception.hold;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/26 上午11:40
 */
@ExceptionMapper(code = "400", msg = "Exception: Hold collection already sold out.")
public class HoldCollectionAlreadySoldOutException  extends RuntimeException{
    public HoldCollectionAlreadySoldOutException(String message) {
        super(message);
    }
}
