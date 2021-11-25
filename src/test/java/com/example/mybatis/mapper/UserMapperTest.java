package com.example.mybatis.mapper;

import com.example.mybatis.domain.Sex;
import com.example.mybatis.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private SqlSession sqlSession; // 类似与JDBC中的Connection，数据库连接
    private UserMapper userMapper;
    @BeforeEach
    void setUp() throws IOException {
        // 通过读取MyBatis配置文件可以生成SqlSessionFactory，这个对象表示数据库的所有关键信息
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        // 通过SqlSessionFactory获得一个打开的数据库连接
        sqlSession = sqlSessionFactory.openSession();
        // mybatis利用映射文件帮我们生成了这个接口的实现类
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @AfterEach
    void tearDown() {
        sqlSession.close(); // 关闭数据库连接
    }

    @Test
    void save() {
        User user = new User();
        user.setEmail("1");
        user.setPassword("123");
        user.setBirthday(LocalDate.now());
        user.setSex(Sex.M);
        user.setLoginCount(0);
        user.setLastLoginTime(null);
        userMapper.save(user);
        sqlSession.commit(); // 提交事务，增删改都需要提交事务才能生效
    }

    @Test
    void deleteByid() {
        userMapper.deleteByid(2);
        sqlSession.commit();
    }

    @Test
    void updateById() {
        User user = new User();
        user.setId(3);
        user.setEmail("3");
        user.setPassword("123456789");
        user.setBirthday(LocalDate.now());
        user.setSex(Sex.M);
        user.setLoginCount(0);
        user.setLastLoginTime(null);
        userMapper.updateById(user);
        sqlSession.commit(); // 提交事务，增删改都需要提交事务才能生效
    }

    @Test
    void findOne() {
        System.out.println(userMapper.findOne(3));
    }

    @Test
    void findAll() {
        System.out.println(userMapper.findAll());
    }
}