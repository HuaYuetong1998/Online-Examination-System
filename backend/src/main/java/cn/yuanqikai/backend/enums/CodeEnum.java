package cn.yuanqikai.backend.enums;

/**
 * @author yuanqikai
 * @date 2021-03-19 14:12
 */
public interface CodeEnum<T> {
    String getCode();

    String getMsg();

    default T msg(String msg) {
        throw new IllegalArgumentException("请实现该方法");
    }

    default String getDetail() {
        StringBuffer detailBuffer = new StringBuffer();
        detailBuffer.append("code:").append(this.getCode()).append("  msg:").append(this.getMsg());
        return detailBuffer.toString();
    }
}