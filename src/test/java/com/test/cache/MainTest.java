package com.test.cache;

import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.test.cache.entity.FlightInfo;
import com.test.cache.util.CacheFailoverListProvider;
import com.test.cache.util.CacheFailoverObjectProvider;
import com.test.cache.util.CacheProvider;
import com.test.cache.util.CacheThroughUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

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
@Slf4j
public class MainTest {

    private static Cache<String, String> stringCache = Caffeine.newBuilder()
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build();

    @Test
    public void testCacheList() {

        String key = "list001";
//        StringCacheService stringCacheService = new StringCacheService();

        CacheProvider<String> stringCacheService = new CacheProvider<String>() {
            @Override
            public String get(String key) {
                return stringCache.asMap().get(key);
            }

            @Override
            public void set(String key, String value) {
                stringCache.asMap().put(key, value);
            }
        };

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

        if (list != null) {
            log.info(JSON.toJSONString(list));
        }


        List<FlightInfo> list1 = CacheThroughUtil.getListThroughCache(key, stringCacheService, cacheFailoverProvider);

        if (list1 != null) {
            log.info(JSON.toJSONString(list));
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

        if (list3 != null) {
            log.info(JSON.toJSONString(list));
        }


        List<FlightInfo> list32 = CacheThroughUtil.getListThroughCache(key3, stringFlightInfosCacheProvider, cacheFailoverListProvider);

        if (list32 != null) {
            log.info(JSON.toJSONString(list));
        }
    }

    @Test
    public void testCacheObject() {
        String key = "asdf001";

        StringCacheService stringCacheService = new StringCacheService();


        CacheFailoverObjectProvider<String, String> cacheFailoverObjectProvider = new CacheFailoverObjectProvider<String, String>() {
            @Override
            public String provide() {
                return "123";
            }
        };

        String value = CacheThroughUtil.getObjectThroughCache(key, stringCacheService, cacheFailoverObjectProvider);

        log.info(value);

        String value1 = CacheThroughUtil.getObjectThroughCache(key, stringCacheService, cacheFailoverObjectProvider);

        log.info(value1);


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

        log.info(value11);

        String value12 = CacheThroughUtil.getObjectThroughCache(key1, stringIntegerCacheProvider, cacheFailoverObjectProvider1);
        log.info(value12);

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

        log.info("{}", value21);

        Integer value22 = CacheThroughUtil.getObjectThroughCache(key2, stringCacheService, cacheFailoverObjectProvider2);

        log.info("{}", value22);
    }
}
