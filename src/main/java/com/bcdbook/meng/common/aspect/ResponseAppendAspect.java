package com.bcdbook.meng.common.aspect;

import com.bcdbook.meng.common.annotation.ResponseAppend;
import com.bcdbook.meng.common.constant.ResponseAppendItemConstant;
import com.bcdbook.meng.common.enums.ResultEnum;
import com.bcdbook.meng.common.exception.CommonException;
import com.bcdbook.meng.system.DTO.IResourceDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.thymeleaf.util.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author summer
 * @Date 2017/9/13 上午10:47
 * 自动添加参数到返回值中的注解
 */
@Aspect
@Component
@Slf4j
public class ResponseAppendAspect {

    /**
     * @author summer
     * @date 2017/9/13 下午1:22
     * @param joinPoint
     * @param responseAppend
     * @return void
     * @description 定义一个切点,用于拦截含有添加返回值注解的方法
     */
    @After(value="@annotation(responseAppend)")
    public void addPostItem(JoinPoint joinPoint, ResponseAppend responseAppend){

        //获取请求中的所有参数
        Object[] args = joinPoint.getArgs();
        ModelMap map = null;//定义ModelMap对象,用于封装返回数据
        HttpServletRequest request = null;
        for (Object obj : args) {
            //如果数据类型为ModelMap类型,则赋予之前定义的ModelMap对象
            if(obj instanceof ModelMap){
                map = (ModelMap) obj;
            }else if (obj instanceof HttpServletRequest){
                request = (HttpServletRequest) obj;
            }
        }

        //参数合法性校验
        if(map==null || request==null){
            throw new CommonException(ResultEnum.PARAM_IS_EMPTY);
        }

        String[] appendItems = responseAppend.appendItems();
        //判断数据是否为空
        if(ArrayUtils.isEmpty(appendItems)){
            log.warn("[自动添加数据] 需要添加的数据为空");
        }else{
            for (String item : appendItems) {
                switch (item){
                    case ResponseAppendItemConstant.ONLINE_MENU:
                        List<IResourceDTO> iResourceDTOList = new ArrayList<>();

                        map.put(ResponseAppendItemConstant.ONLINE_MENU,"menuValue");
                        break;
                    case ResponseAppendItemConstant.ONLINE_USER:
                        map.put(ResponseAppendItemConstant.ONLINE_USER,"userValue");
                        break;
                }
            }
        }

        map.put("test","testValue");
    }
}
