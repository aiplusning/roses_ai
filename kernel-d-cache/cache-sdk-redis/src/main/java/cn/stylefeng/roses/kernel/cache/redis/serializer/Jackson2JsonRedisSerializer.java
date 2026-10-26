package cn.stylefeng.roses.kernel.cache.redis.serializer;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Jackson Redis 序列化器
 *
 * @author fengshuonan
 * @since 2025/12/30 16:28
 */
public class Jackson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final Class<T> clazz;

    private final ObjectMapper objectMapper;

    public Jackson2JsonRedisSerializer(Class<T> clazz) {
        this.clazz = clazz;
        this.objectMapper = new ObjectMapper();

        // 开启类型信息
        this.objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );
    }

    @Override
    public byte[] serialize(T object) throws SerializationException {
        if (object == null) {
            return new byte[0];
        }
        try {
            return objectMapper.writeValueAsString(object).getBytes(DEFAULT_CHARSET);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Could not serialize object: " + e.getMessage(), e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (Exception e) {
            throw new SerializationException("Could not deserialize object: " + e.getMessage(), e);
        }
    }
}