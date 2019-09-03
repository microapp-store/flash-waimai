package cn.enilu.elm.api.utils;

import java.util.HashMap;

/**
 * Created  on 2018/1/3 0003.
 *
 * @author zt
 */
public class Maps {
    private Maps() {
    }

    public static <K, V> HashMap<K, V> newHashMap(K k, V v) {
        HashMap map = new HashMap();
        map.put(k, v);
        return map;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k, V v, Object... extraKeyValues) {
        if(extraKeyValues.length % 2 != 0) {
            throw new IllegalArgumentException();
        } else {
            HashMap map = new HashMap();
            map.put(k, v);

            for(int i = 0; i < extraKeyValues.length; i += 2) {
                k = (K) extraKeyValues[i];
                v = (V) extraKeyValues[i + 1];
                map.put(k, v);
            }

            return map;
        }
    }
}
