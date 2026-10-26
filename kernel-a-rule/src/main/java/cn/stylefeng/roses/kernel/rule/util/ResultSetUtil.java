package cn.stylefeng.roses.kernel.rule.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ResultSet工具类
 *
 * @author fengshuonan
 * @since 2025/12/29 18:28
 */
@Slf4j
public class ResultSetUtil {

    /**
     * 将ResultSet转为List<Map<String,Object>>
     *
     * @author fengshuonan
     * @since 2025/12/29 18:29
     */
    public static List<Map<String, Object>> toList(ResultSet resultSet) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    row.put(columnName, resultSet.getObject(i));
                }
                resultList.add(row);
            }
        } catch (SQLException e) {
            log.error("ResultSet转List<Map<String,Object>>错误！", e);
            return resultList;
        }
        return resultList;
    }

}