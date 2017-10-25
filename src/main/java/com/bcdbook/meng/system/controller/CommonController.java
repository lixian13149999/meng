package com.bcdbook.meng.system.controller;

import com.bcdbook.meng.common.constant.CookieConstant;
import com.bcdbook.meng.common.constant.RedisConstant;
import com.bcdbook.meng.common.constant.SessionResourceConstant;
import com.bcdbook.meng.common.constant.SwaggerTagsConstant;
import com.bcdbook.meng.common.enums.ResultEnum;
import com.bcdbook.meng.common.exception.CommonException;
import com.bcdbook.meng.common.result.Result;
import com.bcdbook.meng.common.service.CommonRedisService;
import com.bcdbook.meng.common.util.CookieUtil;
import com.bcdbook.meng.common.util.MD5Util;
import com.bcdbook.meng.common.util.ResultUtil;
import com.bcdbook.meng.system.DTO.IResourceDTO;
import com.bcdbook.meng.system.DTO.UserDTO;
import com.bcdbook.meng.system.form.LoginUserForm;
import com.bcdbook.meng.system.model.User;
import com.bcdbook.meng.system.service.IResourceService;
import com.bcdbook.meng.system.service.RoleService;
import com.bcdbook.meng.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author summer
 * @date 2017/8/13 下午5:36
 * 全局的Controller,用于登录登出等操作
 */
@Controller
@RequestMapping("/")
@Slf4j
@Api(tags = {SwaggerTagsConstant.COMMON})
public class CommonController {

    //注入用户的service
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private IResourceService iResourceService;

    //注入redis模板
//    @Resource(name = RedisTemplateConstent.USER_AUTHORIZE_REDIS_TEMPLATE)
//    @Autowired
//    private StringRedisTemplate redisTemplate;
    @Autowired
    private CommonRedisService commonRedisService;

    @GetMapping("/login")
    @ApiOperation(value = "登录(登录页面)", notes = "获取登录页面")
    public String getLogin(){
        return "login";
    }



//    @Api：修饰整个类，描述Controller的作用
//    @ApiOperation：描述一个类的一个方法，或者说一个接口
//    @ApiParam：单个参数描述
//    @ApiModel：用对象来接收参数
//    @ApiProperty：用对象接收参数时，描述对象的一个字段
//    @ApiResponse：HTTP响应其中1个描述
//    @ApiResponses：HTTP响应整体描述
//    @ApiIgnore：使用该注解忽略这个API
//    @ApiError ：发生错误返回的信息
//    @ApiParamImplicitL：一个请求参数
//    @ApiParamsImplicit 多个请求参数

    /**
     * 执行登录操作的方法
     * @param loginUserForm
     * @return
     */
    @PostMapping("/login")
    @ResponseBody

    @ApiOperation(value = "登录(用户名+密码)"
            , notes = "通过用户名和密码执行登录操作的方法, 通过cookie和redis实现分布式数据自动存储")
//    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名", paramType = "form",required = true)
//            ,@ApiImplicitParam(name = "password", value = "密码" ,paramType = "form",required = true)})
//    @ApiImplicitParam(name = "loginUserForm", value = "登录用户" ,required = true)
    public Result doLogin(@Valid LoginUserForm loginUserForm,BindingResult bindingResult,
                          HttpServletResponse response){
        /*
         * 参数合法性校验
         */
        if(bindingResult.hasErrors()){
            log.error("[用户登录] 参数不正确, loginUserForm = {}, errorMessage = {}"
                    ,loginUserForm
                    ,bindingResult.getFieldError().getDefaultMessage());

            throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        /**
         * 匹配数据库中的用户
         * 做mysql数据库的校验
         */
        String username = loginUserForm.getUsername();
        String password = MD5Util.getMD5Code(loginUserForm.getPassword());
        User user = userService.findByUsernameAndUserPassword(username,password);
        if(user==null){
            //- TODO 返回错误信息或跳转到错误页面
            log.warn("[用户登录] 用户名或密码不正确");
            return ResultUtil.error(ResultEnum.USERNAME_OR_PASSWORD_NOT_MATCHING);
        }


        /**
         * 添加相关资源信息到redis数据库
         */
        //获取,是否选择保持登录
        Boolean keepOnline = loginUserForm.getKeepOnline() == null ? false : loginUserForm.getKeepOnline();

        //2. 设置token到redis中
        //获取随机码
        String tokenValue = UUID.randomUUID().toString();
        /*
         * 判断是否是长效登录
         */
        int expire = keepOnline ? RedisConstant.LONG_EXPIRE : RedisConstant.EXPIRE;
        //执行添加操作(到redis数据库)
        /*
         * 添加token到redis数据库
         */
        commonRedisService.set(String.format(RedisConstant.TOKEN_PREFIX, tokenValue), //key
                user.getUsername(), //value
                expire, //long(失效时间)
                TimeUnit.SECONDS);
        /*
         * 添加用户名到redis数据库,
         * 为了方便调用,这里单独加入了一个用户名,
         * 其实直接用user对象存储,再从user中获取也是可以的
         */
        commonRedisService.set(String.format(SessionResourceConstant.ONLINE_USER_NAME,tokenValue),
                user.getUsername(),
                expire,
                TimeUnit.SECONDS);
        /*
         * 添加用户到redis数据库中
         */
        commonRedisService.set(String.format(SessionResourceConstant.ONLINE_USER,tokenValue),
                user.toString(),
                expire,
                TimeUnit.SECONDS);


        /**
         * 设置Token到cookie
         */
        Integer maxAge = keepOnline ? CookieConstant.LONG_MAX_AGE : CookieConstant.MAX_AGE;
        CookieUtil.set(response, CookieConstant.TOKEN_NAME, tokenValue, maxAge);

        /**
         * 把长效登录状态保存到cookie
         */
        //设置cookie的保持登录状态
        CookieUtil.set(response, CookieConstant.KEEP_ONLINE, keepOnline.toString(), maxAge);


        //根据用户的id,获取角色的id集合
        List<String> roleIdList = roleService.listRoleIdByUserId(user.getId());
        //根据角色的id集合,获取序列化好的资源集合
        List<IResourceDTO> iResourceDTOList = iResourceService.listIResourceByRoleIdList(roleIdList);


        commonRedisService.set(String.format(SessionResourceConstant.PARSE_I_RESOURCE,tokenValue),
                iResourceDTOList.toString(),
                expire,
                TimeUnit.SECONDS);

        return ResultUtil.success();
    }

    @GetMapping("/signup")
    @ApiOperation(value = "注册(获取页面)"
            , notes = "获取注册页面的方法")
    public String signUp(){
        return null;
    }

    @PostMapping("/signup/username")
    @ResponseBody
    @ApiOperation(value = "注册(用户名+密码)"
            , notes = "非后台用户注册的方法,用户名+密码")
    public Result signUpWithUsername(@RequestParam String username, @RequestParam String password){
        if(StringUtils.isEmpty(username)
                ||StringUtils.isEmpty(password)){
            throw new CommonException(ResultEnum.PARAM_IS_EMPTY.getCode(),"用户名或密码为空");
        }

        User dbUser =  userService.findByUsername(username);
        if(dbUser!=null){
            return ResultUtil.error(ResultEnum.DATA_IS_EXIST.getCode(),"用户名已经被使用");
        }

        User user = new User();
        user.setUsername(username);
        user.setUserPassword(MD5Util.getMD5Code(password));
        user.setNickname(username);

        UserDTO result = userService.save(user);

        return ResultUtil.success(result);
    }

    @PostMapping("/signup/phone")
    @ResponseBody
    @ApiOperation(value = "注册(手机号+验证码)"
            , notes = "非后台用户注册的方法,使用手机号和验证码的方式注册")
    public Result signUpWithPhone(){
        return null;
    }


    @GetMapping
    @ResponseBody
    public String index() throws Exception {
//        String s = redisTemplate.opsForValue().get("token_f8f85541-10de-40e2-9206-6af89afc1493");
//        log.info(s);
//        redisTemplate.opsForValue()
//                .set("summer","token_3496fc56-764a-4729-8a30-44d1a1329df2",200,TimeUnit.SECONDS);
//        log.info(redisTemplate.getExpire("token_3496fc56-764a-4729-8a30-44d1a1329df2").toString());
//        log.info(redisTemplate.expire("token_3496fc56-764a-4729-8a30-44d1a1329df2",100,TimeUnit.SECONDS).toString());
//        log.info(redisTemplate.expire("token_3496fc56-764a-4729-8a30-44d1a1329df2",10000,TimeUnit.SECONDS).toString());

        return "success";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        throw new CommonException(ResultEnum.PARAM_ERROR);
//        return "abc";
    }

    @GetMapping("/socket")
    public String getInput(){
        return "/common/chart";
    }
    @GetMapping("/index")
    public String getIndex(){
        return "index";
    }
}
