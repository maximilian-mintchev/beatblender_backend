package com.app.server.messages.response;

import com.app.server.enums.ResponseStatus;

public class ResponseMessage {

    private ResponseStatus responseStatus;

    private String message;

    public ResponseMessage() {
    }

    public ResponseMessage(ResponseStatus responseStatus, String message) {
        this.responseStatus = responseStatus;
        this.message = message;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
