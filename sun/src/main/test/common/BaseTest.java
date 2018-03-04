package common;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)//此处调用Spring单元测试类
@WebAppConfiguration  //调用javaWEB的组件，比如自动注入
@ContextConfiguration(locations = {"classpath*:/config/spring-root.xml","classpath*:/WEB-INF/spring-servlet.xml"})//加载spring容器
public abstract class BaseTest extends TestCase   {

}
