package io.knightx.simplifynftplatform.exception.collection;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午10:00
 */
@ExceptionMapper(code = "400", msg = "Exception:Collection Name Already Exist.")
public class CollectionNameAlreadyExistException extends RuntimeException {
    public CollectionNameAlreadyExistException(String message) {
        super(message);
    }
}
