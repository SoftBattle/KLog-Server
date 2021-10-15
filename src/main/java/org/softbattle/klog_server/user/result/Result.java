package org.softbattle.klog_server.user.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 返回json格式结果
 * @author ygx
 */
public class Result extends HashMap<String, Object> implements Serializable {

    public static final String STATUS = "stat";
    public static final String MESSAGE = "msg";
    public static final String DATA = "data";


    public Result(){}


    /**
     * 无data结果
     * @param stat
     * @param msg
     */
    public Result(String stat, String msg){
        super.put(STATUS, stat);
        super.put(MESSAGE, msg);
    }

    /**
     * 带data结果
     * @param stat
     * @param msg
     * @param data
     */
    public Result(String stat, String msg, Object data){
        super.put(STATUS, stat);
        super.put(MESSAGE, msg);
        if (data != null){
            super.put(DATA, data);
        }
    }

    /**
     * 预定义成功
     * @return
     */
    public static Result success(){
        Result r = new Result();
        r.put(STATUS, "ok");
        r.put(MESSAGE, "成功");
        return r;
    }

    /**
     * 自定义成功
     * @param data
     * @return
     */
    public static Result success(String msg, Object data){
        Result r = new Result();
        r.put(STATUS, "ok");
        r.put(MESSAGE, msg);
        r.put(DATA, data);
        return r;
    }

    public static Result success(String stat, String msg){
        Result r = new Result();
        r.put(STATUS, stat);
        r.put(MESSAGE, msg);
        return r;
    }

    /**
     * 预定义失败
     * @return
     */
    public static Result error(){
        Result r = new Result();
        r.put(STATUS, "Internal_Server_Error");
        r.put(MESSAGE, "内部错误");
        return r;
    }

    /**
     * 自定义失败
     * @param stat
     * @param msg
     * @return
     */
    public static Result error(String stat, String msg){
        Result r = new Result();
        r.put(STATUS, stat);
        r.put(MESSAGE, msg);
        return r;
    }

}
