package org.sess.client.handler;

public class MessageHandlerException extends RuntimeException {

    public MessageHandlerException() {
    }

    public MessageHandlerException(String message) {
        super(message);
    }

    public MessageHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageHandlerException(Throwable cause) {
        super(cause);
    }

    public MessageHandlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
