package rs.ac.uns.ftn.informatika.jpa;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

@Configuration
@EnableCaching
public class CacheConfig {

    // Define a cache manager for in-memory caching (ConcurrentMapCacheManager)
    @Bean
    public ConcurrentMapCacheManager cacheManager() {
        return new ConcurrentMapCacheManager("trendingPosts", "trendingUsers");
    }
}