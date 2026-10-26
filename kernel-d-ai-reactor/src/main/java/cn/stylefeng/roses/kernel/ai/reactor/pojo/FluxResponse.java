package cn.stylefeng.roses.kernel.ai.reactor.pojo;

import cn.stylefeng.roses.kernel.ai.reactor.enums.FluxResponseTypeEnum;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import lombok.Data;
import reactor.core.publisher.FluxSink;

/**
 * SSE返回数据的包装
 *
 * @author fengshuonan
 * @since 2026/1/21 11:52
 */
@Data
public class FluxResponse {

    /**
     * 响应状态码
     * <p>
     * 这个是有枚举的，详情见：FluxResponseTypeEnum
     */
    @ChineseDescription("响应状态码")
    private Integer code;

    /**
     * 响应信息
     * <p>
     * 响应成功的提示和响应错误的提示都用这个
     */
    @ChineseDescription("响应信息，响应成功的提示和响应错误的提示都用这个")
    private String message;

    /**
     * 附加的信息
     */
    @ChineseDescription("附加的信息")
    private Object attachmentObject;

    /**
     * 返回思考过程的推流
     *
     * @author fengshuonan
     * @since 2026/1/21 11:58
     */
    public static FluxResponse thinking(String message) {
        FluxResponse response = new FluxResponse();
        response.setCode(FluxResponseTypeEnum.THINKING.getCode());
        response.setMessage(message);
        return response;
    }

    /**
     * 返回正常的推流
     *
     * @author fengshuonan
     * @since 2026/1/21 11:56
     */
    public static FluxResponse success(String message) {
        FluxResponse response = new FluxResponse();
        response.setCode(FluxResponseTypeEnum.SUCCESS.getCode());
        response.setMessage(message);
        return response;
    }

    /**
     * 返回错误信息的推送流
     *
     * @author fengshuonan
     * @since 2026/1/21 11:56
     */
    public static FluxResponse error(String message) {
        FluxResponse response = new FluxResponse();
        response.setCode(FluxResponseTypeEnum.ERROR.getCode());
        response.setMessage(message);
        return response;
    }

    /**
     * 返回正常推流过程中的附加情况，例如联合RAG的搜索结果，联网搜索等
     *
     * @author fengshuonan
     * @since 2026/1/21 11:56
     */
    public static FluxResponse attachment(FluxResponseTypeEnum fluxResponseTypeEnum, Object attachmentObject) {
        FluxResponse response = new FluxResponse();
        response.setCode(fluxResponseTypeEnum.getCode());
        response.setMessage(fluxResponseTypeEnum.getMessage());
        response.setAttachmentObject(attachmentObject);
        return response;
    }

    /**
     * 推送给前端流信息
     *
     * @author fengshuonan
     * @since 2026/1/21 11:56
     */
    public static void sinkNext(FluxSink<FluxResponse> sink, FluxResponse fluxResponse) {
        if (sink.isCancelled()) {
            return;
        }
        sink.next(fluxResponse);
    }

    /**
     * 推送前端成功消息
     *
     * @author fengshuonan
     * @since 2026/1/21 11:56
     */
    public static void sinkSuccess(FluxSink<FluxResponse> sink, String successMessage) {
        if (sink.isCancelled()) {
            return;
        }
        sink.next(FluxResponse.success(successMessage));
    }

    /**
     * 返回失败消息
     *
     * @author fengshuonan
     * @since 2026/1/21 11:56
     */
    public static void sinkError(FluxSink<FluxResponse> sink, String errorMessage) {
        if (sink.isCancelled()) {
            return;
        }
        sink.next(FluxResponse.error(errorMessage));
    }

}
