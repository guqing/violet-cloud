package xyz.guqing.violet.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * RedisTokenStoreConfig
 *
 * @author guqing
 * @date 2020-8-6
 */
//@Configuration
public class RedisTokenStoreConfig {

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    @Bean
//    public RedisTokenStore redisTokenStore (){
//        return new RedisTokenStore(redisConnectionFactory);
//    }
}
