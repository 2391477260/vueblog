package com.markerhub.common.lang;

import lombok.Data;
import java.io.Serializable;

@Data
public class Result implements Serializable {
    private  int code;// 200正常，非200表示异常
    private  String msg;
    private Object data;

    public  static Result succ(Object data){
        return succ(200,"操作成功",data);
    }

    public  static Result succ(int code,String msg,Object data){
        Result r= new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return  r;
    }
    public  static Result fail(String msg){
        return  fail(400,msg,null);
    }

    public  static Result fail(String msg,Object data){
        return  fail(400,msg,data);
    }

    public  static Result fail(int code,String msg,Object data){
        Result r= new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return  r;
    }
    private void setData(Object data) {
        this.data=data;
    }

    private void setMsg(String msg) {
        this.msg=msg;
    }

    private void setCode(int code) {
        this.code=code;
    }
}
