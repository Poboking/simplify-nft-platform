package io.knightx.simplifynftplatform.exception.collection;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/24 下午10:21
 */
@ExceptionMapper(code = "400", msg = "Exception: Collection Content Already Exist.")
public class CollectionContentAlreadyExistException extends RuntimeException {
    public CollectionContentAlreadyExistException(String message) {
        super(message);
    }
}
