package com.test.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.test.cache.util.CacheProvider;

import java.util.concurrent.TimeUnit;

/**
 * — <br>
 *
 * @author: 刘恒 <br>
 * @date: 2019/5/10 <br>
 */
public class StringCacheService implements CacheProvider<String> {

    private static Cache<String, String> stringCache = Caffeine.newBuilder()
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build();

    @Override
    public String get(String key) {
        return stringCache.asMap().get(key);
    }

    @Override
    public void setWithExpire(String key, String value, int expire) {
        stringCache.asMap().put(key, value);
    }
}
