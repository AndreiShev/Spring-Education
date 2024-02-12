package com.example.Redis.configuration.propeties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties
@ConditionalOnProperty(prefix = "app.cache")
public class AppCacheProperties {

    private final List<String> cacheNames = new ArrayList<>();

    private final Map<String, CacheProperties> caches = new HashMap<>();

    private final CacheType cacheType;


    @Data
    public static class CacheProperties {
        private Duration expire = Duration.ZERO;
    }

    public interface CashNames {
        String BOOk_BY_CATEGORY_NAME = "bookByCategoryName";
        String BOOK_BY_NAME_AND_AUTHOR = "bookByNameAndAuthor";
    }

    public enum CacheType {
        REDIS
    }

}
