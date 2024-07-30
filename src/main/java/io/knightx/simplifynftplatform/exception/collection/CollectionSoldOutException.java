package io.knightx.simplifynftplatform.exception.collection;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/26 上午9:47
 */
@ExceptionMapper(code = "400", msg = "Exception: Collection sold out failed, collection already sold out.")
public class CollectionSoldOutException extends RuntimeException {
    public CollectionSoldOutException(String message) {
        super(message);
    }
}
