package com.test.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.test.cache.entity.FlightInfo;
import com.test.cache.util.CacheProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * — <br>
 *
 * @author: 刘恒 <br>
 * @date: 2019/5/13 <br>
 */
public class FlightInfosCacheProvider implements CacheProvider<List<FlightInfo>> {

    private static Cache<String, List<FlightInfo>> stringCache = Caffeine.newBuilder()
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build();


    @Override
    public List<FlightInfo> get(String key) {
        return stringCache.asMap().get(key);
    }

    @Override
    public void set(String key, List<FlightInfo> value) {
        stringCache.asMap().put(key, value);
    }
}
