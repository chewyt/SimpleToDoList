package chewyt.todo.config;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.RedisServer.INFO;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


import chewyt.todo.TodoApplication;

@Configuration
public class toDoConfiguration {

    private final Logger logger = Logger.getLogger(TodoApplication.class.getName());
    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;
    @Value("${spring.redis.database}")
    private Optional<Integer> redisDatabase;


    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort.get());
        config.setDatabase(redisDatabase.get());
        
        String redisPassword = System.getenv("REDIS_PW");
        System.out.println("redisPassword: "+redisPassword);
        if (null!=redisPassword) {
            logger.log(Level.INFO, "Setting Redis Password");
            // config.setPassword(redisPassword);
        } 
        
        return new JedisConnectionFactory(config);
    }

    @Bean("soybean")
    public RedisTemplate<String, String> redisTemplate() {
        final RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        // template.setHashKeySerializer(new StringRedisSerializer());
        // RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
        // template.setHashValueSerializer(/* new GenericJackson2JsonRedisSerializer() */serializer);
        template.setValueSerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

}