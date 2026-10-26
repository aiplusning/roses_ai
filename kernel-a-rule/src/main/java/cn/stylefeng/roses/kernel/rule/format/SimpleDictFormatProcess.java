package cn.stylefeng.roses.kernel.rule.format;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.base.SimpleFieldFormatProcess;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 针对简单字典类型进行字段格式化
 *
 * @author fengshuonan
 * @since 2025/11/25 16:08
 */
public class SimpleDictFormatProcess implements SimpleFieldFormatProcess {

    @Override
    public boolean canFormat(Object originValue) {
        return originValue instanceof String;
    }

    @Override
    public Object formatProcess(Object originValue) {

        // 解析原始字符串
        String originString = (String) originValue;

        try {
            List<SimpleDict> simpleDicts = JSON.parseArray(originString, SimpleDict.class);
            if (ObjectUtil.isEmpty(simpleDicts)) {
                return null;
            }

            List<String> nameList = simpleDicts.stream().map(SimpleDict::getName).toList();
            if (ObjectUtil.isEmpty(nameList)) {
                return null;
            }

            return StrUtil.join(",", nameList);
        } catch (Exception e) {
            return null;
        }

    }

}
