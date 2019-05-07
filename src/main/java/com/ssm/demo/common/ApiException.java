package com.ssm.demo.common;

/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/4/26
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private MessageCode code;
    private String messageKey;
    private String[] argv = null;

    public ApiException(MessageCode code) {
        this.code = code;
        this.messageKey = code.name();
    }

    public ApiException(MessageCode code, String... argv) {
        this.code = code;
        this.messageKey = code.name();
        this.argv = argv;
    }

    public MessageCode getCode() {
        return code;
    }

    public void setCode(MessageCode code) {
        this.code = code;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String[] getArgv() {
        return argv;
    }

    public void setArgv(String[] argv) {
        this.argv = argv;
    }

    @Override
    public String getMessage() {
        return Util.getMessage(messageKey, argv);
    }
}
