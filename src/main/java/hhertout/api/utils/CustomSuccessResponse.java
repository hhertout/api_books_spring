package hhertout.api.utils;

public class CustomSuccessResponse {
    private String message;

    public CustomSuccessResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
