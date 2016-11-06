package com.leafgraph.tshimizu.sysdev.memstashd.util;

/**
 * Created by takahiro on 2016/11/07.
 */
public class ConnectionCloseByUserException extends Exception {

    public ConnectionCloseByUserException() {
    }

    public ConnectionCloseByUserException(String message) {
        super(message);
    }

    public ConnectionCloseByUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionCloseByUserException(Throwable cause) {
        super(cause);
    }

    public ConnectionCloseByUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
