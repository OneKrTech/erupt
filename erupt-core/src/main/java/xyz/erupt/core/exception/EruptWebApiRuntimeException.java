package xyz.erupt.core.exception;

import lombok.Getter;

/**
 * @author YuePeng
 * date 2020-09-30
 */
@Getter
public class EruptWebApiRuntimeException extends RuntimeException {

    public EruptWebApiRuntimeException(Throwable throwable) {
        super(throwable);
    }

    public EruptWebApiRuntimeException(String message) {
        super(message);
    }

    public EruptWebApiRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
