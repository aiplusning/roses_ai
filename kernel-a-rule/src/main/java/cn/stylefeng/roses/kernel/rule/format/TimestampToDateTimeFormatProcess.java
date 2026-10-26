package cn.stylefeng.roses.kernel.rule.format;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * 用来处理Long类型时间戳，转化为日期时间格式
 * <p>
 * 日期时间为如下格式：
 * 2025-12-26 15:28:28
 *
 * @author fengshuonan
 * @since 2025/12/26 15:25
 */
public class TimestampToDateTimeFormatProcess extends BaseSimpleFieldFormatProcess {

    @Override
    public Class<?> getItemClass() {
        return Long.class;
    }

    @Override
    public Object simpleItemFormat(Object businessId) {

        if (businessId == null) {
            return null;
        }

        Long timestamp = Convert.toLong(businessId);
        if (timestamp == null) {
            return null;
        }

        return DateUtil.formatDateTime(new Date(timestamp));
    }

}
