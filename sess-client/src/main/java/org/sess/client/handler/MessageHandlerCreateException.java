package org.sess.client.handler;

public class MessageHandlerCreateException extends MessageHandlerException {

    public MessageHandlerCreateException() {
    }

    public MessageHandlerCreateException(String message) {
        super(message);
    }

    public MessageHandlerCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageHandlerCreateException(Throwable cause) {
        super(cause);
    }

    public MessageHandlerCreateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
