package com.xinyuan.ms.exception;

/**
 * @author hzx
 * @Description:
 * @date 2018/3/3015:58
 */
public class WorkLogException extends BaseException{
    public static final WorkLogException WORK_LOG_IS_REPEAT=new WorkLogException(1001,"名称重复");

    public static final WorkLogException WORK_LOG_ID_REPEAT=new WorkLogException(1002,"ID不存在");

    public static final WorkLogException WORK_LOG_NAME_REPEAT=new WorkLogException(1003,"名字name没有查找到结果");
    public WorkLogException(){}

    public WorkLogException(int status, String message){
        super(message,status);
    }
}
