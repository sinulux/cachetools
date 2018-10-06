package com.sinosoft.vms.rtmptools.service;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.embedded.CaffeineCacheBuilder;
import com.sinosoft.vms.rtmptools.dao.AuthUserDao;
import com.sinosoft.vms.rtmptools.entity.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AuthUserService {

    private Logger logger = LoggerFactory.getLogger(AuthUserService.class);
    /** 缓存标识,用于鉴别是否存在缓存 */
    private static final String CACHE_FLAG = "AUTH_USER_LIST";

    /** 缓存容器,限制创建缓存个数100，超时30分钟 */
    private Cache<String, String> cache= CaffeineCacheBuilder
            .createCaffeineCacheBuilder()
            .limit(100)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .buildCache();


    @Autowired
    private AuthUserDao authUserDao;
    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public String getAuthUser(String name){
        String cacheFlag = cache.get(CACHE_FLAG);
        String password = "";
        if(cacheFlag != null){
            logger.info("从缓存加载登陆信息-{}-{}" ,atomicInteger.getAndIncrement(),cacheFlag);
            password = cache.get(name);
            if(password == null){
                logger.info("错误的用户名-{}" ,name);
                password = "";
            }
        }else{
            logger.info("从数据库加载用信息-{}" ,atomicInteger.getAndIncrement());
            List<AuthUser> authUserList = authUserDao.getAuthUserList();
            for(AuthUser authUser:authUserList){
                cache.put(authUser.getUserName(),authUser.getUserPassword());
                // 重新存储缓存的同时返回当前请求的用户密码
                if(name.equals(authUser.getUserName())){
                    password = authUser.getUserPassword();
                }
            }
            cache.put(CACHE_FLAG,CACHE_FLAG);
        }

        return password;
    }

}
