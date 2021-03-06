package com.neuedu.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> {
    private int status;
    private String msg;
    private T data;

    private ServerResponse(){}

    private ServerResponse(int status){
        this.status=status;
    }

    private ServerResponse(int status,String msg){
        this.status=status;
        this.msg=msg;
    }

    private ServerResponse(int status,T data){
        this.status=status;
        this.data=data;
    }

    private ServerResponse(int status,T data,String msg){
        this.status=status;
        this.msg=msg;
        this.data=data;
    }

    @JsonIgnore
    public  boolean isSucess(){
        return this.status==0;
    }

    public static <T> ServerResponse<T> createServerResponseBySucess(){
        return new ServerResponse<>(0);
    }

    public static <T> ServerResponse<T> createServerResponseBySucess(T data){
        return new ServerResponse<>(0,data);
    }

    public static <T> ServerResponse<T> createServerResponseBySucess(String msg){
        return new ServerResponse<>(0,msg);
    }

    public static <T> ServerResponse<T> createServerResponseBySucess(T data,String msg){
        return new ServerResponse<>(0,data,msg);
    }

    public static <T> ServerResponse<T> createServerResponseByFail(int status,String msg){
        return new ServerResponse<>(status,msg);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
