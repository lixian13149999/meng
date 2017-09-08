package com.bcdbook.meng.system.repository;

import com.bcdbook.meng.system.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author summer
 * @Date 2017/8/12 下午1:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void saveTest(){
        User user = new User();
        user.setUsername("summer11");
        user.setUserPassword("123321");
//        user.setCertificationStatus(1);
//        user.setUserStatus(2);
        User result = userRepository.save(user);
        Assert.assertNotNull(result);

        User user2 = new User();
        user2.setUsername("summer12");
        user2.setUserPassword("123321");
//        user.setCertificationStatus(1);
//        user.setUserStatus(2);
        User result2 = userRepository.save(user2);
        Assert.assertNotNull(result);


        log.info("[测试-添加用户]");
    }

    @Test
    public void deleteTest(){
        User user = userRepository.findByUsername("summer");

        userRepository.delete(user);
        log.info("[测试-删除用户]");
    }

    @Test
    public void updateTest(){
//        User user = new User();
//        user.setId("92077e8b-5cf7-4f10-a762-61b691724583");
//        user.setUsername("summer");
//        user.setUserPassword("123321");
//        userRepository.save(user);
        User user = userRepository.findOne("92077e8b-5cf7-4f10-a762-61b691724583");
        user.setUsername("summer");
        userRepository.save(user);
    }

    @Test
    public void findTest(){
        User user = userRepository.findOne("92077e8b-5cf7-4f10-a762-61b691724583");
        log.info(user.toString());
    }

    @Test
    public void findUserByUsernameAndUserPasswordTest(){
        User user = userRepository.findByUsernameAndUserPassword("summer","123321");
        log.info(user.toString());
    }

    @Test
    public void findAllByUserTypeTest(){
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        PageRequest pageRequest = new PageRequest(0,2,sort);

//        Page<User> userPage = userRepository.findByUserTypeOrderByUpdateTimeDesc(1,pageRequest);
        Page<User> userPage = userRepository.findByUserType(1,pageRequest);
        Long total = userPage.getTotalElements();
        Assert.assertNotNull(userPage);
    }

    @Test
    public void findByUserTypeAndUsernameLike(){
        PageRequest pageRequest = new PageRequest(0, 20,new Sort(Sort.Direction.DESC,"updateTime"));
//        Page<User> userPage = userRepository.findByUserTypeAndUsernameLike(1, "", pageRequest);

//        Assert.assertNotNull(userPage);
    }

    @Test
    public void findByUserTypeAndParameterTest(){
//        Page<User> userPage = userRepository.findByUserTypeAndUsernameLikeOrNicknameLikeOrMobileLike(1,"%summer%","%summer%","%summer%",pageRequest);
//        PageRequest pageRequest = new PageRequest(0, 20,new Sort(Sort.Direction.DESC,"updateTime"));
//        Integer userType = 1;
//        String parameter = "summer";
//
//        Specification<User> userTypeSpecification = new Specification<User>() {
//            @Override
//            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                return criteriaBuilder.equal(root.get("userType"),userType);
//            }
//        };
//
//        Specification<User> parameterSpacifiction = new Specification<User>(){
//            @Override
//            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                Predicate preUsername = criteriaBuilder.like(root.get("username"),"%"+parameter+"%");
//                Predicate preNickname = criteriaBuilder.like(root.get("nickname"),"%"+parameter+"%");
//                Predicate preMobile = criteriaBuilder.like(root.get("mobile"),"%"+parameter+"%");
//
//                return criteriaBuilder.or(preUsername,preNickname,preMobile);
//            }
//        };
////        Assert.assertNotNull(userPage);
//        Page<User> userPage =  userRepository.findAll(Specifications.where(userTypeSpecification).and(parameterSpacifiction),pageRequest);
//
//        Assert.assertNotNull(userPage);
    }

//    @Test
//    public void testSpecificaiton2() {
//        //第一个Specification定义了两个or的组合
//        Specification<Student> s1 = new Specification<Student>() {
//            @Override
//            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                Predicate p1 = criteriaBuilder.equal(root.get("id"),"2");
//                Predicate p2 = criteriaBuilder.equal(root.get("id"),"3");
//                return criteriaBuilder.or(p1,p2);
//            }
//        };
//        //第二个Specification定义了两个or的组合
//        Specification<Student> s2 = new Specification<Student>() {
//            @Override
//            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                Predicate p1 = criteriaBuilder.like(root.get("address"),"zt%");
//                Predicate p2 = criteriaBuilder.like(root.get("name"),"foo%");
//                return criteriaBuilder.or(p1,p2);
//            }
//        };
//        //通过Specifications将两个Specification连接起来，第一个条件加where，第二个是and
//        List<Student> stus = studentSpecificationRepository.findAll(Specifications.where(s1).and(s2));
//
//        Assert.assertEquals(1,stus.size());
//        Assert.assertEquals(3,stus.get(0).getId());
//    }
}