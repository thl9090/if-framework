/*
package com.yx.common.redis.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

*/
/**
 * redis默认不支持对象的序列化，需要自定义序列化对象
 * redisTemplate序列化支持类
 *//*

public class RedisObjectSerializer implements RedisSerializer<Object> {

    private Converter<Object, byte[]> serilazier = new SerializingConverter();
    private Converter<byte[], Object> deserilazier = new DeserializingConverter();


    private static final byte[] EMPTY_ARRAY = new byte[0];

    */
/**
     * 将对象序列化为字节数字，序列化失败返回空数组
     *
     * @param o
     * @return
     * @throws SerializationException
     *//*

    @Override
    public byte[] serialize(Object o) throws SerializationException {

        if (null == o) {
            return EMPTY_ARRAY;
        }
        try {
            return serilazier.convert(o);
        } catch (Exception e) {
            return EMPTY_ARRAY;
        }
    }

    */
/**
     * 将字节数组反列化成对象，序列化失败返回null
     *
     * @param bytes
     * @return
     * @throws SerializationException
     *//*

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {

        if (isEmpty(bytes)) {
            return null;
        }
        try {
            return deserilazier.convert(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    */
/**
     * 判断字符数字是否为空
     *
     * @param data
     * @return
     *//*

    private boolean isEmpty(byte[] data) {
        return data == null || data.length == 0;
    }



}
*/
