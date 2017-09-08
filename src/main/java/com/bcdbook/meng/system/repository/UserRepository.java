package com.bcdbook.meng.system.repository;

import com.bcdbook.meng.system.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author summer
 * @Date 2017/8/12 下午1:21
 */
public interface UserRepository extends JpaRepository<User,String> ,JpaSpecificationExecutor<User> {

    /**
     * 根据用户名, 获取用户对象
     * @param username
     * @return
     */
    User findByUsername(String username);

    User findByUsernameAndUserPassword(String username, String userPassword);

    User findByMobile(String mobile);

    User findByEmail(String email);

    Page<User> findByUserType(Integer userType,Pageable pageable);


//    @Query("from table t where t.propertyOne in :param1 and (t.propertyTwo like :param2 or t.propertyThree like :param3 or t.propertyFour like :param4)")
//    Page<User> findByParams(@Param("username") String username, @Param("nickname") String nickname, @Param("mobile") String mobile, @Param("pageable") Pageable pageable);

//    Page<User> findByUserTypeAndUsernameLikeOrNicknameLikeOrMobileLike(Integer userType, String username, String nickname, String mobile,Pageable pageable);
}
