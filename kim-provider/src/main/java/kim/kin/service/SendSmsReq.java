package kim.kin.service;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 短信发送REQ
 */
public class SendSmsReq implements Serializable {
    /**
     * 接收手机号码，多个手机号码用英文逗号分隔，最多500个，必填；
     */
    @NotBlank(message = "手机号码不能为空")
    private String phones;
    /**
     * 短信内容，最多350个汉字，必填,内容中不要出现【】[]这两种方括号，该字符为签名专用
     */
    @NotBlank(message = "短信内容不能为空")
    private String content;
    /**
     * 短信签名,不带【】
     * 惠花
     * 趣惠花
     * 惠众惠
     * 快捷优亿
     */
    @NotBlank(message = "签名不能为空")
    private String smsSign;
    /**
     * finance fireway huij-pay
     */
    private String sourceChannel;
    /**
     * 短信类型
     * YX :营销
     * TZ： 通知
     */
    private String smsType;
    /**
     * 模板编号
     */
    private String tempId="NO_TEMP_ID";

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSourceChannel() {
        return sourceChannel;
    }

    public void setSourceChannel(String sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    public String getSmsSign() {
        return smsSign;
    }

    public void setSmsSign(String smsSign) {
        this.smsSign = smsSign;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    @Override
    public String toString() {
        return "SendSmsReq{" +
                "phones='" + phones + '\'' +
                ", content='" + content + '\'' +
                ", smsSign='" + smsSign + '\'' +
                ", sourceChannel='" + sourceChannel + '\'' +
                ", smsType='" + smsType + '\'' +
                ", tempId='" + tempId + '\'' +
                '}';
    }
}
