package com.example.admanage.system_login.data;

/**
 * A generic class that holds a result success w/ data or an error exception.
 * 一个泛型类，它保存一个带有数据或错误异常的结果成功。
 * 登录结果的实体类：
 * 结果为信息在后台验证的结果（与数据库等数据源的数据进行验证）
 */
public class Result<T> {
    // hide the private constructor to limit subclass types (Success, Error)
    private Result() {
    }

    @Override
    public String toString() {
        if (this instanceof Result.Success) {
            Success success = (Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof Result.Error) {
            Error error = (Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success sub-class
    public final static class Success<T> extends Result {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public final static class Error extends Result {
        private Exception error;
        private String error_string;
        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }

        public Error(String error_string) {
            this.error_string = error_string;
        }

        public String getError_string() {
            return error_string;
        }
    }
}