package com.ff.modealapp.network;


public abstract class JSONResult<DataT> {

    private String result;
    private String message;
    private DataT data;

    public JSONResult() {
    }


    public JSONResult(String result, String message, DataT data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return "success".equals(this.result) ? true : false;
    }

    public DataT getData() {
        return data;
    }

    public void setData(DataT data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JSONResult [result=" + result + ", message=" + message + ", data=" + data + "]";
    }
}