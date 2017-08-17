//package com.bcdbook.meng.tools.repository;
//
//import com.bcdbook.meng.tools.enums.LogTypeEnums;
//import com.bcdbook.meng.tools.model.Log;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * @Author summer
// * @Date 2017/8/15 下午2:04
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class LogRepositoryTest {
//
//    @Autowired
//    private LogRepository logRepository;
//
//    @Test
//    public void saveTest(){
//        Log logger = new Log(LogTypeEnums.INFO.getCode(),"summer","GET","username","192.160.1.1","/login","NullPointException",200,"ajax");
//        Log logger1 = logRepository.save(logger);
//        Assert.assertNotNull(logger1);
//    }
//}