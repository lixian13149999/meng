package com.bcdbook.meng.system.service.impl;

import com.bcdbook.meng.system.DTO.UserDTO;
import com.bcdbook.meng.system.converter.User2UserDTOConverter;
import com.bcdbook.meng.system.model.User;
import com.bcdbook.meng.system.model.UserRole;
import com.bcdbook.meng.system.module.UserRoleKey;
import com.bcdbook.meng.system.repository.UserRepository;
import com.bcdbook.meng.system.repository.UserRoleRepository;
import com.bcdbook.meng.system.service.RoleService;
import com.bcdbook.meng.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author summer
 * @Date 2017/8/13 上午9:52
 * UserService的实现类
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    //引入user的资源对象
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleService roleService;

    /**
     * @author summer
     * @date 2017/8/22 下午4:51
     * @param userDTO
     * @return com.bcdbook.meng.system.DTO.UserDTO
     * @description 添加用户的方法
     */
//    @Override
//    public UserDTO save(UserDTO userDTO) {
//
//        if(userDTO==null){
//            return null;
//        }
//
//        User user = User2UserDTOConverter.reconvert(userDTO);
//
//        User resultUser = userRepository.save(user);
//
//        return User2UserDTOConverter.convert(resultUser);
//    }

    /**
     * @author summer
     * @date 2017/8/24 上午11:23
     * @param user
     * @return com.bcdbook.meng.system.DTO.UserDTO
     * @description 保存用户的方法
     */
    @Override
    public UserDTO save(User user) {

        if(user==null){
            return null;
        }

        User result = userRepository.save(user);

        UserDTO userDTO = User2UserDTOConverter.convert(user);

        return userDTO;
    }

    /**
     * @author summer
     * @date 2017/8/22 下午4:50
     * @param userId
     * @return com.bcdbook.meng.system.model.User
     * @description 根据用户的id获取用户的详细信息
     */
    @Override
    public User findOne(String userId) {

        if(StringUtils.isEmpty(userId)){
            return null;
        }
        return userRepository.findOne(userId);
    }

    @Override
    public User findByUsername(String username) {
        if(StringUtils.isEmpty(username)){
            return null;
        }

        return userRepository.findByUsername(username);
    }

    /**
     * @author summer
     * @date 2017/8/22 下午4:51
     * @param username
     * @param userPassword
     * @return com.bcdbook.meng.system.model.User
     * @description 根据用户名和密码查询用户
     */
    @Override
    public User findByUsernameAndUserPassword(String username, String userPassword) {

        if(StringUtils.isEmpty(username)
                ||StringUtils.isEmpty(userPassword)){
            log.warn("[用户登录] 用户名或密码为空");
            return null;
        }

        return userRepository.findByUsernameAndUserPassword(username,userPassword);
    }

    /**
     * @author summer
     * @date 2017/8/24 上午11:23
     * @param mobile
     * @return com.bcdbook.meng.system.model.User
     * @description 根据手机号,查询用户的方法
     */
    @Override
    public User findByMobile(String mobile) {

        return userRepository.findByMobile(mobile);
    }

    /**
     * @author summer
     * @date 2017/8/24 上午11:23
     * @param email
     * @return com.bcdbook.meng.system.model.User
     * @description 根据邮箱查询用户的方法
     */
    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    /**
     * @author summer
     * @date 2017/8/24 下午2:28
     * @param roleId
     * @return java.util.List<java.lang.String>
     * @description 根据角色id查询用户的id集合
     */
    @Override
    public List<String> listUserIdByRoleId(String roleId){

        //根据用户id获取用户角色中间表对象的集合
        List<UserRole> userRoleList = userRoleRepository.findByUserRoleKey_RoleId(roleId);
        //判断集合的合法性, 为空则直接返回空
        if(userRoleList==null||userRoleList.isEmpty()){
            return null;
        }

        //根据获取到的中间对象的结合, 封装一个角色id的集合对象
        List<String> userIdList = userRoleList
                .stream()
                .map(
                        e -> e.getUserRoleKey().getUserId()
                )
                .collect(
                        Collectors.toList()
                );

        return userIdList;
    }

    /**
     * @author summer
     * @date 2017/8/24 下午7:12
     * @param userType
     * @param parameter
     * @param pageable
     * @return java.util.List<com.bcdbook.meng.system.DTO.UserDTO>
     * @description 根据用户类型,用户的关键字段(用户名/昵称/手机号)获取用户集合
     */
    @Override
    public Page<UserDTO> listUserByUserTypeAndKey(Integer userType, String parameter, Pageable pageable) {
        //分页查询user对象
        Page<User> userPage = null;

        //如果不传入参数,则直接根据用类型进行分类查询,
        //如果传入参数,则封装参数进行模糊匹配
        if(StringUtils.isEmpty(parameter)){
            //分页查询user对象
            userPage = userRepository.findByUserType(userType,pageable);
        }else{
            //封装用户类型的查询条件
            Specification<User> userTypeSpecification = new Specification<User>() {
                @Override
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.equal(root.get("userType"),userType);
                }
            };
            //封装关键字参数的查询条件
            Specification<User> parameterSpacifiction = new Specification<User>(){
                @Override
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Predicate preUsername = criteriaBuilder.like(root.get("username"),"%"+parameter+"%");
                    Predicate preNickname = criteriaBuilder.like(root.get("nickname"),"%"+parameter+"%");
                    Predicate preMobile = criteriaBuilder.like(root.get("mobile"),"%"+parameter+"%");

                    return criteriaBuilder.or(preUsername,preNickname,preMobile);
                }
            };
            //执行带关键字参数的高级查询
            userPage =  userRepository.findAll(Specifications.where(userTypeSpecification).and(parameterSpacifiction),pageable);
        }

        //转换User对象为UserDTO
        List<UserDTO> userDTOList = User2UserDTOConverter.convert(userPage.getContent());
        Page<UserDTO> userDTOPage = new PageImpl<UserDTO>(userDTOList,pageable,userPage.getTotalElements());

        return userDTOPage;
    }

    /**
     * @author summer
     * @date 2017/9/18 下午1:42
     * @param userId
     * @param roleId
     * @return com.bcdbook.meng.system.model.UserRole
     * @description 根据传入的用户id和角色id
     * 给用户添加角色
     */
    @Override
    public Boolean putRole(String userId, String roleId) {
        //参数合法性校验
        if(StringUtils.isEmpty(userId)||StringUtils.isEmpty(roleId)){
            return false;
        }
        //验证值是否存在
        if((findOne(userId)==null) || (roleService.findOne(roleId)==null)){
            return false;
        }
        
        //创建用户/角色对象
        UserRole userRole = new UserRole(new UserRoleKey(userId,roleId));
        //执行关系保存
        UserRole result = userRoleRepository.save(userRole);
        return result!=null;
    }

    /**
     * @author summer
     * @date 2017/9/18 下午1:44
     * @param userId
     * @param roleId
     * @return com.bcdbook.meng.system.model.UserRole
     * @description 根据传入的用户id和角色id
     * 去除用户的角色
     */
    @Override
    public Boolean removeRole(String userId, String roleId) {
        //参数合法性校验
        if(StringUtils.isEmpty(userId)||StringUtils.isEmpty(roleId)){
            return false;
        }

        UserRole userRole = new UserRole(new UserRoleKey(userId,roleId));
        userRoleRepository.delete(userRole);

        return true;
    }
}
