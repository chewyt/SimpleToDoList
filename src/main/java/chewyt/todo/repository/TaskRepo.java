package chewyt.todo.repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepo {

    @Autowired
    // Give it a name in case of collision
    @Qualifier("soybean")

    private RedisTemplate<String, String> redisTemplate;

    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value, 5, TimeUnit.MINUTES);
        // Need to justify balace on maintaining cache data
    }

    public void saveinList(String key, String value) {

        redisTemplate.opsForList().rightPush(key, value);
    }

    public Optional<String> get(String key, Long index) {
        return Optional.ofNullable(redisTemplate.opsForList().index(key, index));
    }

    public Long checkSize(String key) {

        return redisTemplate.opsForList().size(key);

    }

    public void delete(String key, Long index){
        redisTemplate.opsForList().
    }

}
