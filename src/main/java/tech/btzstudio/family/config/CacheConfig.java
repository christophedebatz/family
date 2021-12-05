package tech.btzstudio.family.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisCacheWriter redisCacheWriter;

    @Value("auth.token.cache.ttl.days")
    private Long tokenDaysTtl;

    @Bean
    @Primary
    public CacheManager redisCacheManager() {
        return new RedisCacheManager(
                this.redisCacheWriter,
                this.resolveCacheManagerConfiguration(this.tokenDaysTtl)
        );
    }

    private RedisCacheConfiguration resolveCacheManagerConfiguration(long ttlDays) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(ttlDays))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}
