package com.test.cache.util;

//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;

/**
 * 如果缓存没有命中，提供获取数据的方法
 * T 返回值类型，  K 缓存类型, 如果2个类型不一致，需要实现转换方法 returnType2CacheType 和 cacheType2ReturnType<br>
 *
 * @author: 刘恒 <br>
 * @date: 2019/5/10 <br>
 */
public interface CacheFailoverObjectProvider<T, K> {

    T provide();

    /**
     * 从T转成K, 返回值类型 转 缓存类型, 默认 认为2个类型一样,直接强转,如不一样,需要实现
     **/
    default K returnType2CacheType(T t) {
        return (K) t;
    }

    /**
     * 从K转成T, 缓存类型 转 返回值类型, 默认 认为2个类型一样,直接强转,如不一样,需要实现
     **/
    default T cacheType2ReturnType(K k) {
        return (T) k;
    }

//    /**
//     * 是否需要转换,如果2个泛型类型相等，就不用转换
//     **/
//    default boolean needConvert() {
//        Type[] types = getClass().getGenericInterfaces();
//
//        for (Type type : types) {
//            if (type.getTypeName().contains("CacheFailoverObjectProvider")) {
//
//                Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
//                Type typeT = actualTypeArguments[0];
//                Type typeK = actualTypeArguments[1];
//
//                return typeT != typeK;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 从T转成K, 返回值类型 转 缓存类型
//     **/
//    default K returnType2CacheType(T t) {
//        if (needConvert() == false)
//            return (K) t;
//
//        return returnType2CacheTypeReal(t);
//    }
//
//    /**
//     * 从K转成T, 缓存类型 转 返回值类型
//     **/
//    default T cacheType2ReturnType(K k) {
//        if (needConvert() == false)
//            return (T) k;
//
//        return cacheType2ReturnTypeReal(k);
//    }
//
//    /**
//     * 从T转成K, 返回值类型 转 缓存类型, 需要实现
//     **/
//    K returnType2CacheTypeReal(T t);
//
//    /**
//     * 从K转成T, 缓存类型 转 返回值类型, 需要实现
//     **/
//    T cacheType2ReturnTypeReal(K k);
}
