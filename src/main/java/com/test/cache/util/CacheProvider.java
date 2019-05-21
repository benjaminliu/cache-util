package com.test.cache.util;

/**
 * 缓存提供商接口 <br>
 *
 * @author: 刘恒 <br>
 * @date: 2019/5/10 <br>
 */
public interface CacheProvider<T> {

    /**
     * 从缓存中获取数据
     **/
    T get(String key);

    /**
     * 将数据保存到缓存中， 如果需要设置缓存过期时间，请在实现类中自行设置
     **/
    void set(String key, T value);

    //以下部分觉得没啥用，用户自己决定是否要设置过期时间

//    /**
//     * 缓存过期时间，默认1分钟
//     **/
//    default int getExpireSeconds() {
//        return 60;
//    }


//    /**
//     * expire 大于0才会设置过期时间，小于等于0不会设置过期时间，具体实现看客户端
//     **/
//    void setWithExpire(String key, T value, int expire);
}
