package org.softbattle.klog_server.user.result;

/**
 * 常见请求状态枚举类
 * 方便后续拓展和修改
 * @author ygx
 */
public enum HttpStatusEnum {
    /**
     * 成功
     */
    SUCCESS("ok", "成功"),
    /**
     * 报错暂时不做区分
     */
    ERROR("Internal_Server_Error", "内部错误");

    private String stat;
    private String msg;

    HttpStatusEnum(String stat, String msg){
        this.stat = stat;
        this.msg = msg;
    }

    public String getStat(){
        return stat;
    }
    public String getMsg(){
        return msg;
    }
}
