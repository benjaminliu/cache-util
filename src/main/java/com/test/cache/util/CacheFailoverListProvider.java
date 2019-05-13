package com.test.cache.util;

import java.util.List;

/**
 * 如果缓存没有命中，提供获取数据的方法
 * T 返回list的 元素类型，  K 缓存类型, 如果2个类型不一致，需要实现转换方法 returnType2CacheType 和 cacheType2ReturnType<br>
 *
 * @author: 刘恒 <br>
 * @date: 2019/5/10 <br>
 */
public interface CacheFailoverListProvider<T, K> {

    List<T> provide();

    /**
     * 从 List<T> 转成 K, 返回值元素类型list 转 缓存类型, 默认无需转换,如果需要转换,请自己实现
     **/
    default K returnType2CacheType(List<T> ts) {
        return (K) ts;
    }

    /**
     * 从 K 转成 List<T>, 缓存类型 转 返回值元素类型list, 默认无需转换,如果需要转换,请自己实现
     **/
    default List<T> cacheType2ReturnType(K k) {
        return (List<T>) k;
    }
}
