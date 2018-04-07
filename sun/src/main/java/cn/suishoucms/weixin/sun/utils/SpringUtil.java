package cn.suishoucms.weixin.sun.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring IOC上下文工具类
 * 
 * @author Ryan Shaw
 * 
 */
public class SpringUtil implements ApplicationContextAware {

    /**
     * 当前IOC
     */
    private static ApplicationContext applicationContext;

    /**
     * 设置当前上下文环境，此方法由spring自动装配
     */
    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        applicationContext = arg0;
    }

    public static <T> T getBean(String name) {  
        return (T)applicationContext.getBean(name);  
    }  
    
    public static <T> T getBean(Class<T> clazz) {  

        return (T) applicationContext.getBean(clazz);  
    }  

}