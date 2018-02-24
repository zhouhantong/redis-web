import com.zy.zht.bean.Student;
import com.zy.zht.dao.StudentMapper;
import com.zy.zht.services.StudentServices;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Administrator on 2018/1/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/applicationContext.xml",
        "classpath*:config/spring-mvc.xml"})
//很多情况下单元测试离不开事务，下面的注解指明使用的事务管理器
//如果defaultRollback为true，测试运行结束后，默认回滚事务，不影响数据库
@TransactionConfiguration( transactionManager = "transactionManager", defaultRollback = true )
@Transactional //指定默认所有测试方法的事务特性
public class MockTest {
    @Autowired
    private StudentServices services;
    @Autowired
    private StudentMapper mapper;

   /* @BeforeClass
    public void beforeTestClass() {
        PowerMockito.mockStatic(LoggerFactory.class);
        Logger logger = PowerMockito.mock(Logger.class);
        PowerMockito.when(LoggerFactory.getLogger(Mockito.any(Class.class))).thenReturn(logger);
        MockitoAnnotations.initMocks(this);
    }*/
    @Test
    public void update() {
        Student student=Mockito.mock(Student.class,Mockito.CALLS_REAL_METHODS);
       // Student student=new Student();
        student.setStuId(1);
        student.setStuName("金玉凤");
        student.setStuAge(25);
        student.setStuSex(2);
        student.setStuComment("测试");
        System.out.println(student.getStuId());
        Student student1=services.selectByPrimaryKey(student.getStuId());
        System.out.println(student1.getStuName());
        Mockito.when(services.selectByPrimaryKey(student.getStuId())).thenReturn(student1);
        //Mockito.when(services.updateByPrimaryKey(student)).thenReturn(1);
    }
}
