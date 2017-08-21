package com.bcdbook.meng.common.controller;

import com.bcdbook.meng.common.enums.ResultEnum;
import com.bcdbook.meng.common.result.Result;
import com.bcdbook.meng.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author summer
 * @Date 2017/8/21 下午7:53
 */
@Controller
@Slf4j
public class CommonErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @ResponseBody
    @RequestMapping(value=ERROR_PATH)
    public Result handleError(){
        log.error("[错误页面] 所访问的页面不存在");
        return ResultUtil.error(ResultEnum.PAGE_NOT_EXIST);
//        return "pages/404";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
