package tk.lenkyun.foodbook.foodbook.Client.Utils;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;

import java.util.ArrayList;

/**
 * Created by lenkyun on 19/10/2558.
 * Refer from : http://crunchify.com/how-to-create-a-simple-in-memory-cache-in-java-lightweight-cache/
 */
public class ContentCache<K, V> {
    protected LRUMap cache;
    private long timeToLive, maxItems;

    public ContentCache(long timeToLive, final long cacheCheckInterval, int maxItems) {
        this.timeToLive = timeToLive * 1000;
        this.maxItems = maxItems;
        this.cache = new LRUMap(maxItems);

        if (timeToLive > 0 && cacheCheckInterval > 0) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(cacheCheckInterval * 1000);
                        } catch (InterruptedException ex) {
                        }
                        cleanup();
                    }
                }
            });

            thread.setDaemon(true);
            thread.start();
        }
    }

    public void put(K key, V value) {
        synchronized (cache) {
            cache.put(key, new ContentAccessObject(value));
        }
    }

    public V get(K key) {
        synchronized (cache) {
            ContentAccessObject contentAccessObject = (ContentAccessObject) cache.get(key);
            if (contentAccessObject == null) {
                return null;
            } else {
                contentAccessObject.lastAccessed = System.currentTimeMillis();
                return contentAccessObject.content;
            }
        }
    }

    public void remove(K key) {
        synchronized (cache) {
            cache.remove(key);
        }
    }

    public void cleanup() {
        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey = null;

        synchronized (cache) {
            MapIterator itr = cache.mapIterator();

            deleteKey = new ArrayList<K>((cache.size() / 2) + 1);
            K key = null;
            ContentAccessObject c = null;

            while (itr.hasNext()) {
                key = (K) itr.next();
                c = (ContentAccessObject) itr.getValue();

                if (c != null && (now > c.lastAccessed + timeToLive)) {
                    deleteKey.add(key);
                }
            }
        }
        for (K key : deleteKey) {
            remove(key);

            Thread.yield();
        }

    }

    protected class ContentAccessObject {
        public long lastAccessed = System.currentTimeMillis();
        public V content;

        public ContentAccessObject(V uri) {
            content = uri;
        }
    }
}
