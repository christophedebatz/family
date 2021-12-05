package tech.btzstudio.family.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheFactory {

    @Autowired
    private CacheManager cacheManager;

    public Cache authCache() {
        Cache cache = cacheManager.getCache("auth");

        if (null == cache) {
            throw new RuntimeException("\"auth\" cache pool not exists.");
        }

        return cache;
    }
    
}
