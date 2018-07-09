package com.xinyuan.ms.exception;


/**
 * 自定义异常
 *
 * @author hwz
 * @since 2018-03-07
 */
public class  UserException extends BaseException {
    public static final UserException USER_NAME_IS_REPEAT  = new UserException(1001,"用户名重复");
    public static final UserException PASSWORD_IS_INCORRECT = new UserException(1006,"账号或密码不正确");
    public static final UserException PARAMETER_ANOMALY = new UserException(501,"参数有误");

    public UserException() {

    }

    public UserException(int status , String message) {
        super(message,status);
    }

}
