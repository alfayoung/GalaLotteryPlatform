package lottery.backend;

import lottery.backend.database.model.UserTuple;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import lottery.backend.database.dao.MybatisDAO;

import lottery.backend.database.model.UserEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.imageio.ImageIO;

public class MybatisTest {
    //定义 SqlSession
    MybatisDAO dao;
    @Before
    public void initBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        dao = (MybatisDAO)context.getBean("mybatisDAO");
    }
//    @Test
    public void testSelectEnrolledUsers() {
        dao.selectEnrolledUsers();
    }
//    @Test
    public void testInsertUser() {
        dao.registerUser("jAccount1", "U1");
        dao.registerUser("jAccount6", "U6");
    }
//    @Test
    public void testGetAndSetPrizeWinners() {
        List<UserTuple> res = dao.setAndGetPrizeWinners("SMALL", 3);
        for (UserTuple x : res) {
            System.out.println(x.getName());
        }
    }
    @Test
    public void checkUserExists() {
        System.out.println(dao.checkUserExists("jAccount1"));
        System.out.println(dao.checkUserExists("jAccount5"));
        System.out.println(dao.checkUserExists("jAccount6"));
        System.out.println(dao.checkUserExists("jAt5"));
    }
//    @Test
    public void testSetEnrolledTrue() {
        dao.setEnrolledTrue("jAccount1");
        dao.setEnrolledTrue("jAccount6");
        dao.setEnrolledTrue("jAccount3");
    }
}