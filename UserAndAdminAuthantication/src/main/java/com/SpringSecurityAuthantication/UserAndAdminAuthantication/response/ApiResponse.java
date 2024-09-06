package com.SpringSecurityAuthantication.UserAndAdminAuthantication.response;

public class ApiResponse<T> {
    private int code;
    private String message;
    private T object;

    public ApiResponse(int code, String message, T object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }

    // Getters and setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
