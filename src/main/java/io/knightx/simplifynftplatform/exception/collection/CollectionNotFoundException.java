package io.knightx.simplifynftplatform.exception.collection;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午9:56
 */
@ExceptionMapper(code = "400", msg = "Exception: Collection Not Found.")
public class CollectionNotFoundException extends RuntimeException {
    public CollectionNotFoundException(String message) {
        super(message);
    }
}
