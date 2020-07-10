package com.zw.knight.exception;


/**
 * @author chenchao
 * @version 1.0
 * @date 2016年4月5日 下午8:56:03
 * @throws
 * @see
 */

public class ServiceException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2329764297824028070L;

    /**
     * 业务异常状态enum
     */
    private ErrorStatus errorStatus;

    /**
     * 业务错误码
     */
    private int code;

    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }

    public ServiceException(String message) {
        super(message);
        this.errorStatus = ErrorStatus.DEFAULT_SERVICE_EXCEPTION;
    }

    public ServiceException(ErrorStatus errorStatus) {
        super(errorStatus.message());
        this.errorStatus = errorStatus;
    }

    public ServiceException(ErrorStatus errorStatus, String message) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(int errCode) {
        super("ERR_" + errCode);
        this.code = errCode;
    }

    // 直接抛出原有的错误
    public ServiceException(Throwable cause) {
        super(cause);
        this.errorStatus = ErrorStatus.DEFAULT_SERVICE_EXCEPTION;
    }

    // 直接抛出原有的错误
    public ServiceException(Throwable cause, int errCode) {
        super(cause);
        this.code = errCode;
    }

    // 直接抛出原有的错误
    public ServiceException(Throwable cause, int errCode, String message) {
        super(message, cause);
        this.code = errCode;
    }

    // 直接抛出原有的错误
    public ServiceException(Throwable cause, String message) {
        super(message, cause);
    }

    public int getCode() {
        return code;
    }
}
