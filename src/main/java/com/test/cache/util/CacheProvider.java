package com.test.cache.util;

/**
 * 缓存提供商接口 <br>
 *
 * @author: 刘恒 <br>
 * @date: 2019/5/10 <br>
 */
public interface CacheProvider<T> {

    /**
     * 缓存过期时间，默认1分钟
     **/
    default int getExpireSeconds() {
        return 60;
    }

    /**
     * 从缓存中获取数据
     **/
    T get(String key);

    /**
     * expire 大于0才会设置过期时间，小于等于0不会设置过期时间，具体实现看客户端
     **/
    void setWithExpire(String key, T value, int expire);
}
