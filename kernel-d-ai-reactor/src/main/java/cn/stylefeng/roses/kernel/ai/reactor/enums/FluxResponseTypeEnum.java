package cn.stylefeng.roses.kernel.ai.reactor.enums;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.ai.reactor.constants.ChatStreamConstants;
import cn.stylefeng.roses.kernel.rule.base.ReadableEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * Flux响应枚举枚举类
 *
 * @author fengshuonan
 * @since 2026/1/21 11:53
 */
@Getter
public enum FluxResponseTypeEnum implements ReadableEnum<FluxResponseTypeEnum> {

    /**
     * 思考过程返回
     */
    THINKING(99, "思考过程返回"),

    /**
     * 正常返回
     */
    SUCCESS(100, "正常返回"),

    /**
     * 正当响应结束
     */
    SUCCESS_END(101, ChatStreamConstants.CHAT_STREAM_FINISH_SIGNAL),

    /**
     * 遇到错误
     */
    ERROR(-1, "遇到错误"),

    /**
     * 提示词重组
     */
    PROMPT(2010, "提示词重组"),

    /**
     * 组装聊天缓存
     */
    CHAT_HISTORY(2020, "聊天缓存组装"),

    /**
     * RAG知识检索成功
     */
    RAG(2030, "RAG知识检索"),

    /**
     * 解析用户附件的文本成功
     */
    USER_FILE(2040, "解析用户附件的文本"),

    /**
     * 结合互联网搜索成功
     */
    INTERNET(2050, "结合互联网搜索"),

    /**
     * 组装Function Call
     */
    FUNCTION_CALL_BUILD(2060, "组装Function Call"),

    /**
     * 历史记录的创建（同时包括用户历史消息和AI返回历史消息的创建）
     */
    HISTORY_ID_CREATED(3010, "历史记录的创建（同时包括用户历史消息和AI返回历史消息的创建）"),

    /**
     * 当前请求AI的调用过程结束，需要去调用Function Call调用
     */
    NEED_TO_FUNCTION_CALL(3020, "AI请求结束，需要调用Function Call"),

    /**
     * 读取到一个完整json结构
     */
    JSON_ITEM(3030, "读取到一个完整json结构");

    @EnumValue
    @JsonValue
    private final Integer code;

    private final String message;

    FluxResponseTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据code获取枚举，用在Controller请求中解析枚举类型
     *
     * @author fengshuonan
     * @since 2026/1/21 11:53
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static FluxResponseTypeEnum codeToEnum(String code) {
        if (null != code) {
            for (FluxResponseTypeEnum enumItem : FluxResponseTypeEnum.values()) {
                if (enumItem.getCode().toString().equals(code)) {
                    return enumItem;
                }
            }
        }
        return null;
    }

    /**
     * 编码转化成中文含义
     *
     * @author fengshuonan
     * @since 2026/1/21 11:54
     */
    public static String codeToMessage(String code) {
        if (null != code) {
            for (FluxResponseTypeEnum enumItem : FluxResponseTypeEnum.values()) {
                if (enumItem.getCode().toString().equals(code)) {
                    return enumItem.getMessage();
                }
            }
        }
        return "未知";
    }

    @Override
    public Object getKey() {
        return this.code;
    }

    @Override
    public Object getName() {
        return this.message;
    }

    @Override
    public FluxResponseTypeEnum parseToEnum(String originValue) {
        if (ObjectUtil.isEmpty(originValue)) {
            return null;
        }
        for (FluxResponseTypeEnum value : FluxResponseTypeEnum.values()) {
            if (value.code.toString().equals(originValue)) {
                return value;
            }
        }
        return null;
    }

}