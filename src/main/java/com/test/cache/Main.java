package com.test.cache;

import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.test.cache.entity.FlightInfo;
import com.test.cache.util.CacheFailoverListProvider;
import com.test.cache.util.CacheFailoverObjectProvider;
import com.test.cache.util.CacheThroughUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * — <br>
 *
 * @author: 刘恒 <br>
 * @date: 2019/5/10 <br>
 */
public class Main {


    private static Cache<String, Integer> integerCache = Caffeine.newBuilder()
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build();


    public static void main(String[] args) {

//        testCacheObject();

        testCacheList();
    }

    private static void testCacheList() {

        String key = "list001";
        StringCacheService stringCacheService = new StringCacheService();

        CacheFailoverListProvider<FlightInfo, String> cacheFailoverProvider = new CacheFailoverListProvider<FlightInfo, String>() {
            @Override
            public List<FlightInfo> provide() {
                List<FlightInfo> list = new ArrayList<>();
                FlightInfo f1 = new FlightInfo();
                f1.setFlightNo("CA123");
                f1.setDepCode("PEK");
                f1.setArrCode("PVG");
                f1.setLocalDate(LocalDate.of(2019, 5, 13));
                list.add(f1);

                FlightInfo f2 = new FlightInfo();
                f2.setFlightNo("HU7705");
                f2.setDepCode("WUH");
                f2.setArrCode("XIY");
                f2.setLocalDate(LocalDate.of(2019, 06, 1));
                list.add(f2);
                return list;
            }

            @Override
            public String returnType2CacheType(List<FlightInfo> flightInfos) {
                if (flightInfos == null)
                    return null;
                return JSON.toJSONString(flightInfos);
            }

            @Override
            public List<FlightInfo> cacheType2ReturnType(String s) {
                if (StringUtils.isBlank(s))
                    return null;

                return JSON.parseArray(s, FlightInfo.class);
            }
        };

        List<FlightInfo> list = CacheThroughUtil.getListThroughCache(key, stringCacheService, cacheFailoverProvider);

        if (list == null) {

        }


        List<FlightInfo> list1 = CacheThroughUtil.getListThroughCache(key, stringCacheService, cacheFailoverProvider);

        if (list1 == null) {

        }


        FlightInfosCacheProvider stringFlightInfosCacheProvider = new FlightInfosCacheProvider();

        CacheFailoverListProvider<FlightInfo, List<FlightInfo>> cacheFailoverListProvider = new CacheFailoverListProvider<FlightInfo, List<FlightInfo>>() {
            @Override
            public List<FlightInfo> provide() {
                List<FlightInfo> list = new ArrayList<>();
                FlightInfo f1 = new FlightInfo();
                f1.setFlightNo("CA123");
                f1.setDepCode("PEK");
                f1.setArrCode("PVG");
                f1.setLocalDate(LocalDate.of(2019, 5, 13));
                list.add(f1);

                FlightInfo f2 = new FlightInfo();
                f2.setFlightNo("HU7705");
                f2.setDepCode("WUH");
                f2.setArrCode("XIY");
                f2.setLocalDate(LocalDate.of(2019, 06, 1));
                list.add(f2);
                return list;
            }
        };

        String key3 = "list003";

        List<FlightInfo> list3 = CacheThroughUtil.getListThroughCache(key3, stringFlightInfosCacheProvider, cacheFailoverListProvider);

        if (list3 == null) {

        }


        List<FlightInfo> list32 = CacheThroughUtil.getListThroughCache(key3, stringFlightInfosCacheProvider, cacheFailoverListProvider);

        if (list32 == null) {

        }
    }

    private static void testCacheObject() {
        String key = "asdf001";

        StringCacheService stringCacheService = new StringCacheService();


        CacheFailoverObjectProvider<String, String> cacheFailoverObjectProvider = new CacheFailoverObjectProvider<String, String>() {
            @Override
            public String provide() {
                return "123";
            }
        };

        String value = CacheThroughUtil.getObjectThroughCache(key, stringCacheService, cacheFailoverObjectProvider);

        System.out.println(value);

        String value1 = CacheThroughUtil.getObjectThroughCache(key, stringCacheService, cacheFailoverObjectProvider);

        System.out.println(value1);


        IntegerCacheProvider stringIntegerCacheProvider = new IntegerCacheProvider();

        CacheFailoverObjectProvider<String, Integer> cacheFailoverObjectProvider1 = new CacheFailoverObjectProvider<String, Integer>() {
            @Override
            public String provide() {
                return "234";
            }

            @Override
            public Integer returnType2CacheType(String s) {
                return Integer.parseInt(s);
            }

            @Override
            public String cacheType2ReturnType(Integer integer) {
                return String.valueOf(integer);
            }
        };

        String key1 = "asdf002";
        String value11 = CacheThroughUtil.getObjectThroughCache(key1, stringIntegerCacheProvider, cacheFailoverObjectProvider1);

        System.out.println(value11);

        String value12 = CacheThroughUtil.getObjectThroughCache(key1, stringIntegerCacheProvider, cacheFailoverObjectProvider1);
        System.out.println(value12);

        CacheFailoverObjectProvider<Integer, String> cacheFailoverObjectProvider2 = new CacheFailoverObjectProvider<Integer, String>() {
            @Override
            public Integer provide() {
                return 345;
            }

            @Override
            public String returnType2CacheType(Integer integer) {
                return String.valueOf(integer);
            }

            @Override
            public Integer cacheType2ReturnType(String s) {
                return Integer.parseInt(s);
            }
        };


        String key2 = "asdf003";
        Integer value21 = CacheThroughUtil.getObjectThroughCache(key2, stringCacheService, cacheFailoverObjectProvider2);

        System.out.println(value21);

        Integer value22 = CacheThroughUtil.getObjectThroughCache(key2, stringCacheService, cacheFailoverObjectProvider2);

        System.out.println(value22);
    }
}
