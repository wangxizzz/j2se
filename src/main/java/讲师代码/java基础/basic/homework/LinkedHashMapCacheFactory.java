/**
 * 
 */
package 讲师代码.java基础.basic.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 题目3：使用LinkedHashMap实现一个LRU缓存
 *
 * @author Guanying Piao
 *
 * 2018-07-24
 */
public class LinkedHashMapCacheFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkedHashMapCacheFactory.class);
    
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;        
    
    private LinkedHashMapCacheFactory() {}
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        
        private int cacheSize = 0;
        private float loadFactor = DEFAULT_LOAD_FACTOR;
        
        public <K, V> Map<K, V> build() {
            if (cacheSize == 0) {
                throw new IllegalArgumentException("You must set cacheSize");
            }
            Map<K, V> cache = new LinkedHashMap<K, V> ((int) Math.ceil(cacheSize / loadFactor) + 1, loadFactor, true) {
                private static final long serialVersionUID = 1L;
                @Override
                protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                    return size() > cacheSize;
                }
            };
            return Collections.synchronizedMap(cache);
        }
        
        public Builder setCacheSize(int cacheSize) {
            if (cacheSize < 1) {
                throw new IllegalArgumentException("Illegal cacheSize: " + cacheSize);
            }
            this.cacheSize = cacheSize;
            return this;
        }
        public Builder setLoadFactor(float loadFactor) {
            if (loadFactor < 0 || loadFactor > 1) {
                throw new IllegalArgumentException("Illegal loadFacotry: " + loadFactor);
            }
            this.loadFactor = loadFactor;
            return this;
        }

    }

    public static void main(String[] args) {
        Map<String, String> cache = LinkedHashMapCacheFactory.builder().setCacheSize(100).build();
        for (int i = 0; i < 200; i++) {
            cache.put("key" + i, "value" + i);
        }
        LOGGER.info("cache current size is:{}", cache.size());
    }
    
}
