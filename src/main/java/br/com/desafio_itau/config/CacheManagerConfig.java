package br.com.desafio_itau.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheManagerConfig {
    @Bean
    public CacheManager cacheManager() {
        return new SimpleCacheManager();
    }
}
