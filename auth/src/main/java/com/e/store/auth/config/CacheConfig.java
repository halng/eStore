package com.e.store.auth.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

public class CacheConfig {

	@Value("${cache.redis.default}")
	private int durationDefault;

	@Value("${cache.redis.access-token}")
	private int durationAccessToken;

	@Value("${cache.redis.refresh-token}")
	private int durationRefreshToken;

	@Value("${cache.redis.user-info}")
	private int durationUserInfo;

	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
			.disableCachingNullValues()
			.entryTtl(Duration.ofMinutes(durationDefault))
			.serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	}

	@Bean
	public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
		return (builder) -> builder
			.withCacheConfiguration("token",
					RedisCacheConfiguration.defaultCacheConfig()
						.disableCachingNullValues()
						.entryTtl(Duration.ofMinutes(durationAccessToken)))
			.withCacheConfiguration("default",
					RedisCacheConfiguration.defaultCacheConfig()
						.disableCachingNullValues()
						.entryTtl(Duration.ofMinutes(durationUserInfo)))
			.withCacheConfiguration("refreshToken",
					RedisCacheConfiguration.defaultCacheConfig()
						.disableCachingNullValues()
						.entryTtl(Duration.ofMinutes(durationRefreshToken)));
	}

}
