package com.test.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.test.cache.util.CacheProvider;

import java.util.concurrent.TimeUnit;

/**
 * — <br>
 *
 * @author: 刘恒 <br>
 * @date: 2019/5/13 <br>
 */
public class IntegerCacheProvider implements CacheProvider<Integer> {

    private static Cache<String, Integer> stringCache = Caffeine.newBuilder()
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build();

    @Override
    public Integer get(String key) {
        return stringCache.asMap().get(key);
    }

    @Override
    public void setWithExpire(String key, Integer value, int expire) {
        stringCache.asMap().put(key, value);
    }
}
