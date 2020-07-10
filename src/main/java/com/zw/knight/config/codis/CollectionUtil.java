package com.zw.knight.config.codis;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.zip.CRC32;

/**
 * 简单的集合工具类
 *
 * @author chenchao
 * @version 1.0
 * @date 2016年6月2日 下午2:43:54
 * @throws
 * @see
 */

public class CollectionUtil {

    /**
     * 从map中随机获取key
     *
     * @param map
     * @return key
     */
    public static <K, V> K getRandomKeyFromMap(Map<K, V> map) {
        if (null == map || map.isEmpty()) {
            return null;
        }
        List<K> keyList = new ArrayList<K>(map.keySet());
        K randomKey = keyList.get(new Random().nextInt(keyList.size()));
        return randomKey;
    }

    /**
     * 从map中随机获取value
     *
     * @param map
     * @return
     */
    public static <K, V> V getRandomValueFromMap(Map<K, V> map) {
        if (null == map || map.isEmpty()) {
            return null;
        }
        List<K> keyList = new ArrayList<K>(map.keySet());
        K randomKey = keyList.get(new Random().nextInt(keyList.size()));
        V value = map.get(randomKey);
        return value;
    }

    /**
     * 从map中随机获取entry
     *
     * @param map
     * @return
     */
    public static <K, V> Map.Entry<K, V> getRandomEntryFromMap(Map<K, V> map) {
        if (null == map || map.isEmpty()) {
            return null;
        }
        List<Map.Entry<K, V>> entryList = new ArrayList<Map.Entry<K, V>>(map.entrySet());
        Map.Entry<K, V> randomEntry = entryList.get(new Random().nextInt(entryList.size()));
        return randomEntry;
    }

    /**
     * 从map中获取entry（crc32）
     *
     * @param map
     * @return
     */
    public static <K, V> Map.Entry<K, V> getCRC32EntryFromMap(Map<K, V> map, String key) {
        if (null == map || map.isEmpty() || StringUtils.isBlank(key)) {
            return null;
        }
        Map<K, V> sortMap = new TreeMap<K, V>();
        sortMap.putAll(map);
        List<Map.Entry<K, V>> entryList = new ArrayList<Map.Entry<K, V>>(sortMap.entrySet());

        CRC32 crc32 = new CRC32();
        crc32.update(key.getBytes());
        long mod = Math.abs(crc32.getValue()) % entryList.size();
        Map.Entry<K, V> crc32Entry = entryList.get((int) mod);
        return crc32Entry;
    }

    /**
     * 从map中获取entry
     *
     * @param map
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K extends Comparable, V> Map.Entry<K, V> getModEntryFromMap(Map<K, V> map,
        long key) {
        if (null == map || map.isEmpty()) {
            return null;
        }
        List<Map.Entry<K, V>> entryList = new ArrayList<>(map.entrySet());
        Collections.sort(entryList, Comparator.comparing(entry -> entry.getKey()));
        long mod = key % entryList.size();
        Map.Entry<K, V> entry = entryList.get((int) mod);
        return entry;
    }
}
