package com.deificindia.indifun.interfaces;

public abstract class IndiFunResult<T> {

    private IndiFunResult() {}

    public static final class Success<T> extends IndiFunResult<T> {
        public T data;

        public Success(T data) {
            this.data = data;
        }
    }

    public static final class Error<T> extends IndiFunResult<T> {
        public Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }
    }
}
