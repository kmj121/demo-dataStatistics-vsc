package com.ssm.demo.common;

public enum MessageCode {

    /** 成功 */
    CODE_SUCCESS(0),
    /** 参数错误*/
    CODE_PARAMETER_ERROR(9000),

    /** 个人信息不存在 */
    CODE_NO_PERSON(9001),
    /** 未选中任何数据 */
    CODE_NO_DATA(9002),

    /** 系统异常 */
    CODE_EXCEPTION(9999);

    private int code;
    MessageCode(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code){this.code = code;}
}
