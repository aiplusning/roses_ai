package cn.stylefeng.roses.kernel.file.api.format;

import cn.hutool.core.convert.Convert;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.file.api.FileInfoApi;
import cn.stylefeng.roses.kernel.file.api.pojo.AntdvFileInfo;
import cn.stylefeng.roses.kernel.rule.format.BaseSimpleFieldFormatProcess;
import com.alibaba.fastjson2.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 针对文件id的包装处理，返回文件可以访问的地址和文件的名称
 * <p>
 * 通过缓存加速获取文件信息
 *
 * @author fengshuonan
 * @since 2023/11/13 17:49
 */
public class FileInfoFormatProcess extends BaseSimpleFieldFormatProcess {

    @Override
    public Class<?> getItemClass() {
        return Object.class;
    }

    @Override
    public Object simpleItemFormat(Object businessId) {

        FileInfoApi fileInfoApi = SpringUtil.getBean(FileInfoApi.class);

        // 目前businessId有3种情况
        // Long，String，数组字符串，下面分别进行判断

        // 1. 如果是Long类型
        if (businessId instanceof Long) {
            return fileInfoApi.buildAntdvFileInfo(Convert.toLong(businessId));
        }

        // 2. 如果是字符串，先判断是单纯字符串还是数组字符串
        else if (businessId instanceof String) {

            String fileIdOrFileIdArrayString = Convert.toStr(businessId);

            // 2.1 如果是单纯字符串，则直接按long返回
            if (!fileIdOrFileIdArrayString.contains("[") && !fileIdOrFileIdArrayString.contains("]")) {
                return fileInfoApi.buildAntdvFileInfo(Convert.toLong(fileIdOrFileIdArrayString));
            }

            // 2.2 如果是json，List<Long>这种类型
            else {
                List<AntdvFileInfo> fileList = new ArrayList<>();
                List<Long> fileIdList = JSON.parseArray(fileIdOrFileIdArrayString, Long.class);
                for (Long fileId : fileIdList) {
                    fileList.add(fileInfoApi.buildAntdvFileInfo(fileId));
                }
                return fileList;
            }
        }

        return null;
    }

}
