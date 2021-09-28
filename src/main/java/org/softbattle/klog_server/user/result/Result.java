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
     * 预定义错误
     * 不带data
     * @param hse
     */
    public Result(HttpStatusEnum hse){
        super.put(hse.getStat(), hse.getMsg());
    }

    /**
     * 预定义错误
     * 带data
     * @param hse
     * @param data
     */
    public Result(HttpStatusEnum hse, Object data){
        super.put(hse.getStat(), hse.getMsg());
        super.put(DATA, data);
    }

    /**
     * 自定义错误
     * 不带data
     * @param stat
     * @param msg
     */
    public Result(String stat, String msg){
        super.put(STATUS, stat);
        super.put(MESSAGE, msg);
    }

    /**
     * 自定义错误
     * 带data
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
     * 分页查询结果
     * @param page
     */
    public Result(IPage<?> page){
        super.put(STATUS, HttpStatusEnum.SUCCESS.getStat());
        super.put(MESSAGE, HttpStatusEnum.SUCCESS.getMsg());
        super.put(DATA, page.getRecords());
        final Integer total = (int) page.getTotal();
        //这里有待商榷
        super.put("total", total);
    }

    public static Result success(){
        return new Result(HttpStatusEnum.SUCCESS);
    }

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
