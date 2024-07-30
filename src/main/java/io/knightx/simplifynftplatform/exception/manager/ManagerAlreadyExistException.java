package io.knightx.simplifynftplatform.exception.manager;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/12 下午3:32
 */
@ExceptionMapper(code = "400", msg = "Manager already exist")
public class ManagerAlreadyExistException extends RuntimeException {
    public ManagerAlreadyExistException(String message) {
        super(message);
    }
}
