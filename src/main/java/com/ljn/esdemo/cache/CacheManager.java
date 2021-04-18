package com.ljn.esdemo.cache;

import com.ljn.esdemo.exception.BusinessException;

import java.util.*;

/**
 * <p>Description: 管理缓存</p>
 * 可扩展的功能：当cache到内存溢出时必须清除掉最早期的一些缓存对象，这就要求对每个缓存对象保存创建时间
 * 目前该缓存管理器仅仅支持IOS客户端访问时的登录状态管理，如果进行它用，请防止key冲突
 *
 * @author zhanglp
 */
public class CacheManager {
    private static HashMap cacheMap = new HashMap();

    //单实例构造方法   
    private CacheManager() {
        super();
    }

    //获取布尔值的缓存   
    public static boolean getSimpleFlag(String key) {
        try {
            return (Boolean) cacheMap.get(key);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static long getServerStartdt(String key) {
        try {
            return (Long) cacheMap.get(key);
        } catch (Exception ex) {
            return 0;
        }
    }

    //设置布尔值的缓存   
    public synchronized static boolean setSimpleFlag(String key, boolean flag) {
        if (flag && getSimpleFlag(key)) {//假如为真不允许被覆盖   
            return false;
        } else {
            cacheMap.put(key, flag);
            return true;
        }
    }

    public synchronized static boolean setSimpleFlag(String key, long serverbegrundt) {
        if (cacheMap.get(key) == null) {
            cacheMap.put(key, serverbegrundt);
            return true;
        } else {
            return false;
        }
    }


    //得到缓存。同步静态方法   
    private synchronized static Cache getCache(String key) {
        return (Cache) cacheMap.get(key);
    }

    //判断是否存在一个缓存   
    private synchronized static boolean hasCache(String key) {
        return cacheMap.containsKey(key);
    }

    //清除所有缓存   
    public synchronized static void clearAll() {
        cacheMap.clear();
    }

    //清除某一类特定缓存,通过遍历HASHMAP下的所有对象，来判断它的KEY与传入的TYPE是否匹配   
    public synchronized static void clearAll(String type) {
        Iterator i = cacheMap.entrySet().iterator();
        String key;
        ArrayList<String> arr = new ArrayList<String>();
        try {
            while (i.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) i.next();
                key = (String) entry.getKey();
                if (key.startsWith(type)) { //如果匹配则删除掉   
                    arr.add(key);
                }
            }
            for (int k = 0; k < arr.size(); k++) {
                clearOnly(arr.get(k));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //清除指定的缓存   
    public synchronized static void clearOnly(String key) {
        cacheMap.remove(key);
    }

    //载入缓存   
    public synchronized static void putCache(String key, Cache obj) {
        cacheMap.put(key, obj);
    }

    //获取缓存信息   
    public static Cache getCacheInfo(String key) {

        if (hasCache(key)) {
            Cache cache = getCache(key);
            if (cacheExpired(cache)) { //调用判断是否终止方法   
                cache.setExpired(true);
            }
            return cache;
        } else
            return null;
    }

    //载入缓存信息   
    public static void putCache(String key, Cache obj, long dt, boolean expired) {
        Cache cache = new Cache();
        cache.setKey(key);
        cache.setTimeOut(dt + System.currentTimeMillis()); //设置多久后更新缓存   
        cache.setValue(obj);
        cache.setExpired(expired); //缓存默认载入时，终止状态为FALSE   
        cacheMap.put(key, cache);
    }

    //重写载入缓存信息方法   
    public static void putCache(String key, Cache obj, long dt) {
        Cache cache = new Cache();
        cache.setKey(key);
        cache.setTimeOut(dt + System.currentTimeMillis());
        cache.setValue(obj);
        cache.setExpired(false);
        cacheMap.put(key, cache);
    }

    //判断缓存是否终止   
    public static boolean cacheExpired(Cache cache) {
        if (null == cache) { //传入的缓存不存在   
            return false;
        }
        long nowDt = System.currentTimeMillis(); //系统当前的毫秒数   
        long cacheDt = cache.getTimeOut(); //缓存内的过期毫秒数   
        if (cacheDt <= 0 || cacheDt > nowDt) { //过期时间小于等于零时,或者过期时间大于当前时间时，则为FALSE
            return false;
        } else { //大于过期时间 即过期   
            return true;
        }
    }

    //获取缓存中的大小   
    public static int getCacheSize() {
        return cacheMap.size();
    }

    //获取指定的类型的大小   
    public static int getCacheSize(String type) {
        int k = 0;
        Iterator i = cacheMap.entrySet().iterator();
        String key;
        try {
            while (i.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) i.next();
                key = (String) entry.getKey();
                if (key.indexOf(type) != -1) { //如果匹配则删除掉   
                    k++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return k;
    }

    //获取缓存对象中的所有键值名称   
    public static ArrayList<String> getCacheAllkey() {
        ArrayList a = new ArrayList();
        try {
            Iterator i = cacheMap.entrySet().iterator();
            while (i.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) i.next();
                a.add((String) entry.getKey());
            }
        } catch (Exception ex) {
        } finally {
            return a;
        }
    }

    //获取缓存对象中指定类型 的键值名称   
    public static ArrayList<String> getCacheListLikekey(String type) {
        ArrayList a = new ArrayList();
        String key;
        try {
            Iterator i = cacheMap.entrySet().iterator();
            while (i.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) i.next();
                key = (String) entry.getKey();
                if (key.indexOf(type) != -1) {
                    a.add(key);
                }
            }
        } catch (Exception ex) {
        } finally {
            return a;
        }
    }

    public static void main(String[] args) {

        //Demo1
        /*Cache c1 = new Cache();
        c1.setKey("China");
        c1.setValue("中华人民共和国");

        Cache c2 = new Cache();
        c2.setKey("American");
        c2.setValue("美利坚合众国");

        CacheManager.putCache("one", c1);
        CacheManager.putCache("two", c2);
        System.out.println("缓存大小：" + CacheManager.getCacheSize());
        System.out.println("key为one的缓存对象Value：" + CacheManager.getCacheInfo("one").getValue());

*/
        System.out.println("-----------------------------------");


        //Demo2
        Cache c3 = new Cache() {{
            setKey("小客车品牌列表");
        }};
        c3.setValue(new ArrayList<String>() {{
            add("奔驰");
            add("宝马");
            add("奥迪");
            add("现代");
        }});

        CacheManager.putCache("car", c3);

        /*打印*/
        List<String> list2 = new ArrayList<>();
        Object obCar = CacheManager.getCacheInfo("car").getValue();
        if (obCar instanceof List) {
            list2 = (List<String>) obCar;
        }
        for (String brand : list2) {
            System.out.println("Key为car的品牌：" + brand);
        }
        System.out.println("此时的缓存大小：" + CacheManager.getCacheSize());
        System.out.println("-----------------------------------");


        //Demo3
        /*Cache c4 = new Cache();
        c4.setKey("朗行-自动1.8T");

        Cache c5 = new Cache();
        c5.setKey("朗境-双离合2.0T");

        Cache c6 = new Cache();
        c6.setKey("夏朗-自动1.8T");

        Cache c7 = new Cache();
        c7.setKey("朗逸-双离合2.0T");

        Cache c8 = new Cache();
        c8.setKey("速腾-自动1.8T");

        Cache c9 = new Cache();
        c9.setKey("迈腾-双离合2.0T");

        Cache c10 = new Cache();
        c10.setKey("辉腾-双离合2.0T");

        Map<String, String> map1 = new HashMap<>();
        map1.put("全景天窗", "是");
        map1.put("发动机排量", "2.0L");
        map1.put("排放标准", "国5");
        c10.setValue(map1);

        Cache c11 = new Cache();
        c11.setKey("英菲尼迪-1.8T都市精英版");

        CacheManager.putCache("朗行", c4);
        CacheManager.putCache("朗境", c5);
        CacheManager.putCache("朗逸", c6);
        CacheManager.putCache("夏朗", c7);
        CacheManager.putCache("速腾", c8);
        CacheManager.putCache("迈腾", c9);
        CacheManager.putCache("辉腾", c10);

        //得到朗系家族的车产品
        Object carObj2 = CacheManager.getCacheListLikekey("朗");
        List<String> list3 = new ArrayList<String>();
        if (carObj2 instanceof List) {
            list3 = (List<String>) carObj2;
        }
        for (String product : list3) {
            System.out.println("朗系家族：" + product);
        }

        System.out.println("-----------------------------------");

        //得到X腾系列的车产品
        Object carObj3 = CacheManager.getCacheListLikekey("腾");
        List<String> list4 = new ArrayList<String>();
        if (carObj2 instanceof List) {
            list4 = (List<String>) carObj3;
        }
        for (String product : list4) {
            System.out.println("腾系家族：" + product);
        }

        System.out.println("-----------------------------------");
        System.out.println("此时的缓存大小：" + CacheManager.getCacheSize());*/

    }
}
 