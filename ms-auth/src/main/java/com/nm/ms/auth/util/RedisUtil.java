package com.nm.ms.auth.util;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil<T> {

    private final RedisTemplate<String, T> redisTemplate;
    private final ValueOperations<String, T> valueOperations;

    public void putValue(String key, T value, long timeout, TimeUnit unit) {
        valueOperations.set(key, value, timeout, unit);
    }

    public T getValue(String key) {
        return valueOperations.get(key);
    }

    public void setExpire(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

}