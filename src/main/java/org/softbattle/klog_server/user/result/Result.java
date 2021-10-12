package org.softbattle.klog_server.user.result;

import com.baomidou.mybatisplus.core.metadata.IPage;

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
     * 无data预定义结果
     * @param hse
     */
    public Result(HttpStatusEnum hse){
        super.put(hse.getStat(), hse.getMsg());
    }

    /**
     * 带data预定义结果
     * @param hse
     * @param data
     */
    public Result(HttpStatusEnum hse, Object data){
        super.put(hse.getStat(), hse.getMsg());
        super.put(DATA, data);
    }

    /**
     * 无data自定义结果
     * @param stat
     * @param msg
     */
    public Result(String stat, String msg){
        super.put(STATUS, stat);
        super.put(MESSAGE, msg);
    }

    /**
     * 带data自定义结果
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
     * 成功
     * 不带data
     * @return
     */
    public static Result success(){
        return new Result(HttpStatusEnum.SUCCESS);
    }

    /**
     * 成功
     * 带data
     * @param data
     * @return
     */
    public static Result success(Object data){
        Result r = new Result();
        r.put(STATUS, "ok");
        r.put(DATA, data);
        return r;
    }


    public static Result error(){
        return new Result(HttpStatusEnum.ERROR);
    }

    public static Result error(Object data){
        return new Result(HttpStatusEnum.ERROR);
    }

}
