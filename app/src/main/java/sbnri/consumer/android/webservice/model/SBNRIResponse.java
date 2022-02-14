package sbnri.consumer.android.webservice.model;

import java.util.HashMap;

/**
 */

public class SBNRIResponse<T> {

    private boolean success;
    private boolean isOffline = false;
    private HashMap<String, Object> supportingData;
    private String msg;
    private String message;
    private String statusCode;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, Object> getSupportingData() {
        return supportingData;
    }

    public void setSupportingData(HashMap<String, Object> supportingData) {
        this.supportingData = supportingData;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOffline() {
        return isOffline;
    }

    public void setOffline(boolean offline) {
        isOffline = offline;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public SBNRIResponse<T> createOfflineResponse(T data) {
        this.data = data;
        this.isOffline = true;
        this.success = true;
        return this;
    }
}
