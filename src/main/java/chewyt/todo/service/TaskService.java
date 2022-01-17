package chewyt.todo.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import chewyt.todo.repository.TaskRepo;


@Service
public class TaskService {

    @Autowired
    TaskRepo repo;
    
    public boolean hasKey(String key){
        Optional<String> opt = repo.get(key);
        return opt.isPresent();
    }

    public List<String> get(String key){
        
        List<String> list  =new LinkedList<>();
        for (Long index = 0L ; index <  repo.checkSize(key); index++) {
            
            Optional<String> opt = repo.get(key,index);
            if (opt.isPresent()) {
                list.add(opt.get());
            }
        }
        return list;
    }

    public void saveList(String user,List<String> values){
        
        for (String value : values) {
            repo.saveinList(user, value);
        }        
    }
 

}
