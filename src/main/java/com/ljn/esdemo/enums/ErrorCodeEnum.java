package com.ljn.esdemo.enums;

/**
 * The class Error code enum.
 *
 * @author kml
 */
public enum ErrorCodeEnum {
    /**
     * Global 400 error code enum.
     */
    GLOBAL_400(400, "参数异常"),
    /**
     * Global 401 error code enum.
     */
    GLOBAL_401(401, "未登录"),
    /**
     * Global 401 error code enum.
     */
    GLOBAL_403(403, "无访问权限"),
    /**
     * Global 500 error code enum.
     */
    GLOBAL_500(500, "服务器异常"),
    /**
     * Global 404 error code enum.
     */
    GLOBAL_404(404, "找不到指定资源"),


    /********************* 用户相关 *************************/
    /**
     * User 1000 用戶名已被使用
     */
    USER_1000(1000,"用户名已被使用"),
    /**
     * User 1001 短信验证码不正确
     */
    USER_1001(1001,"短信验证码不正确"),

    USER_10011(10011,"验证码发送太频繁"),

    USER_10012(10012,"验证码错误"),
    
    USER_10013(10013,"短信验证码发送异常"),

    /**
     * User 1002 账户被禁用
     */
    USER_1002(1002,"账户被禁用"),

    USER_1003(1003, "密码不正确"),

    USER_1004(1004, "账号不存在"),

    USER_1005(1005, "该手机号已被注册"),
	
    /**
     * Bankcard银行卡
     */
	Bankcard_2001(2001,"重复绑卡,请换卡重试"),

	
    /**
     * WithholdOrder
     */
	WithholdOrder_3001(3001,"暂无订单"),
	WithholdOrder_3002(3002,"订单数量异常"),
	WithholdOrder_3003(3003,"已经存在成功订单"),
	
	RefundOrder_3011(3011,"暂无订单"),
	RefundOrder_3012(3012,"订单数量异常"),
	RefundOrder_3013(3013,"退款提交失败"),
	RefundOrder_3014(3014,"退款修改失败"),
	RefundOrder_3015(3015,"已存在退款订单"),
	
	Order_3100(3100,"订单类型异常"),
	Order_3101(3101,"银行卡数量异常"),
	
	Pay_4000(4000,"快钱申请验证码错误"),
	Pay_4001(4001,"快钱校验验证码错误"),
	Pay_4002(4002,"快钱协议支付错误"),
	Pay_4003(4003,"快钱支付回调错误"),


    Pay_5002(5002,"快钱协议支付错误");



    private int code;
    private String msg;

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Msg string.
     *
     * @return the string
     */
    public String msg() {
        return msg;
    }

    /**
     * Code int.
     *
     * @return the int
     */
    public int code() {
        return code;
    }

    /**
     * Gets enum.
     *
     * @param code the code
     * @return the enum
     */
    public static ErrorCodeEnum getEnum(int code) {
        for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
            if (ele.code() == code) {
                return ele;
            }
        }
        return null;
    }
}
