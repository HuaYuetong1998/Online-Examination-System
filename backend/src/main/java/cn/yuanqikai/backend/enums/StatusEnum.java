package cn.yuanqikai.backend.enums;

/**
 * @author yuanqikai
 * @date 2021-03-19 15:48
 */
public enum StatusEnum implements CodeEnum<StatusEnum>{

    SUCCESS("0", "操作成功"),
    FAIL("-1", "操作失败"),
    EXCEPTION("-2", "异常");

    private String code;
    private String msg;
    private String desc;

    private StatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.desc = msg;
    }

    public static boolean success(String code) {
        return SUCCESS.getCode().equals(code);
    }

    public static boolean fail(String code) {
        return FAIL.getCode().equals(code);
    }

    public static boolean exception(String code) {
        return EXCEPTION.getCode().equals(code);
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public StatusEnum msg(String msg) {
        this.msg = msg;
        return this;
    }
}
