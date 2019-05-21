package com.test.cache.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 一般的缓存步骤，如果缓存中有，直接返回，如果缓存中没有，就从cacheFailoverProvider中获取，然后存到缓存中，最后返回 <br>
 *
 * @author: 刘恒 <br>
 * @date: 2019/5/10 <br>
 */
public class CacheThroughUtil {

    /**
     * T 是返回值 类型， K 是缓存类型，如果2个类型不一致，需要在CacheFailoverObjectProvider提供转换方法
     **/
    public static <T, K> T getObjectThroughCache(String key, CacheProvider<K> cacheProvider, CacheFailoverObjectProvider<T, K> cacheFailoverProvider) {
        if (cacheProvider == null)
            return null;

        if (cacheFailoverProvider == null)
            return null;

        if (StringUtils.isBlank(key))
            return null;

        //cache 中获取
        K value = cacheProvider.get(key);
        T res;

        if (value != null) {
            res = cacheFailoverProvider.cacheType2ReturnType(value);
            return res;
        }

        //cache 未命中, 从FailoverProvider中获取
        res = cacheFailoverProvider.provide();
        if (res == null)
            return null;

        value = cacheFailoverProvider.returnType2CacheType(res);

        //转换失败,就不保存到缓存中
        if (value == null)
            return null;

        //保存到缓存中
        cacheProvider.set(key, value);

        return res;
    }

    /**
     * T 是返回值 类型， K 是缓存类型，如果2个类型不一致，需要在CacheFailoverObjectProvider提供转换方法
     **/
    public static <T, K> List<T> getListThroughCache(String key, CacheProvider<K> cacheProvider, CacheFailoverListProvider<T, K> cacheFailoverProvider) {
        if (cacheProvider == null)
            return null;

        if (cacheFailoverProvider == null)
            return null;

        if (StringUtils.isBlank(key))
            return null;

        //cache 中获取
        K value = cacheProvider.get(key);
        List<T> res;

        if (value != null) {
            res = cacheFailoverProvider.cacheType2ReturnType(value);
            return res;
        }

        //cache 未命中, 从FailoverProvider中获取
        res = cacheFailoverProvider.provide();
        if (res == null)
            return null;

        value = cacheFailoverProvider.returnType2CacheType(res);

        //转换失败,就不保存到缓存中
        if (value == null)
            return null;

        //保存到缓存中
        cacheProvider.set(key, value);

        return res;
    }
}
