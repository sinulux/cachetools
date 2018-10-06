package com.sinosoft.vms.rtmptools.config;

import com.sinosoft.vms.rtmptools.service.AuthUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 实现ApplicationListener接口，为了spring在初始化完成后调用一次
 */
@Component
public class ApplicationInitConfig implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(ApplicationInitConfig.class);

    @Autowired
    private AuthUserService authUserService;

    /**
     * Spring容器将所有的Bean都初始化完成之后调用的方法
     * 在web 项目中（spring mvc），系统会存在两个容器，一个是root application context
     * 另一个就是我们自己的 projectName-servlet context（作为root application context的子容器）
     * 这种情况下，就会造成onApplicationEvent方法被执行两次
     * 为了避免上面提到的问题，我们可以只在root application context初始化完成后调用逻辑代码，其他的容器的初始化完成，则不做任何处理
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        //root application context 没有parent
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            // 调一个方法
            long startTime = System.currentTimeMillis();
            authUserService.getAuthUser("");
            long endTime = System.currentTimeMillis();
            logger.info("初始化登陆信息完成,共耗时：{}毫秒" ,endTime - startTime);
        }
    }

}
