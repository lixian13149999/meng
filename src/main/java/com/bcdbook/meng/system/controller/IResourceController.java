package com.bcdbook.meng.system.controller;

import com.bcdbook.meng.common.constant.SwaggerTagsConstant;
import com.bcdbook.meng.common.enums.ResultEnum;
import com.bcdbook.meng.common.exception.CommonException;
import com.bcdbook.meng.common.result.Result;
import com.bcdbook.meng.common.util.ResultUtil;
import com.bcdbook.meng.common.util.SortFormUtil;
import com.bcdbook.meng.system.DTO.IResourceDTO;
import com.bcdbook.meng.system.form.IResourceForm;
import com.bcdbook.meng.system.model.IResource;
import com.bcdbook.meng.system.model.Role;
import com.bcdbook.meng.system.service.IResourceService;
import com.bcdbook.meng.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author summer
 * @Date 2017/8/16 下午8:54
 */
@Controller
@RequestMapping("/resource")
@Slf4j
@Api(tags = {SwaggerTagsConstant.IRESOURCE})
public class IResourceController {

    @Autowired
    private IResourceService iResourceService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/save")
    @ResponseBody
    @ApiOperation(value = "添加资源", notes = "添加iresource信息到数据库中")
    public Result save(@Valid IResourceForm iResourceForm, BindingResult bindingResult) {
        /**
         * 参数合法性校验
         */
        if(bindingResult.hasErrors()){
            log.error("[添加资源] 参数不正确, iResourceForm = {}, errorMessage = {}"
                    ,iResourceForm
                    ,bindingResult.getFieldError().getDefaultMessage());

            throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        //复制iResourceForm对象到iResource
        IResource iResource = new IResource();
        BeanUtils.copyProperties(iResourceForm,iResource);

        //保存资源
        IResource result = iResourceService.save(iResource);

        return ResultUtil.success(result);
    }

    @GetMapping("/menu/list")
    public String menuList(){
        //- TODO 查询菜单列表
        return null;
    }

    @GetMapping("/button/list")
    public String buttonList(){
        //- TODO 查询按钮列表
        return null;
    }

    @ResponseBody
    @GetMapping("/listAll")
    public Result listAll(){
        iResourceService.listAll();
        //- TODO 查询所有的菜单和按钮
        return ResultUtil.success();
    }

    /**
     * @author summer
     * @date 2017/9/13 下午9:32
     * @param userId
     * @return com.bcdbook.meng.common.result.Result
     * @description 根据用户的id,获取其所含的资源的对象集合
     * 去重,并排序
     */
    @ResponseBody
    @GetMapping("/authorization")
    public Result authorization(@Valid String userId){

        if (StringUtils.isEmpty(userId)){
            throw new CommonException(ResultEnum.PARAM_IS_EMPTY);
        }
        //根据用户id获取角色集合
        List<Role> roleList =  roleService.listRoleByUserId(userId);
        //封装角色的id集合
        List<String> roleIdList = roleList
                .stream()
                .map(e -> e.getId())
                .collect(Collectors.toList());
        //根据角色的id集合,获取资源的id集合
        List<String> iResourceIdList = iResourceService.listIResourceIdByRoleIdList(roleIdList);
        //去重
        List<String> outIResourceIdList = new ArrayList<String>(new HashSet<String>(iResourceIdList));

        List<IResourceDTO> iResourceDTOList = iResourceService.listIResourceByIdList(outIResourceIdList);

        return ResultUtil.success(iResourceDTOList);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(){
        //- TODO 删除菜单/按钮
        return null;
    }

    @PostMapping("/update")
    @ResponseBody
    @ApiOperation(value = "修改资源", notes = "修改iresource信息")
    public Result update(@Valid IResourceForm iResourceForm, BindingResult bindingResult){
        /**
         * 参数合法性校验
         */
        if(bindingResult.hasErrors()){
            log.error("[更新资源] 参数不正确, iResourceForm = {}, errorMessage = {}"
                    ,iResourceForm
                    ,bindingResult.getFieldError().getDefaultMessage());
            throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        //复制iResourceForm对象到iResource
        IResource iResource = new IResource();
        BeanUtils.copyProperties(iResourceForm,iResource);

        //执行更新操作
        IResource result = iResourceService.update(iResource);

        return ResultUtil.success(result);
    }

    @PostMapping("/sort")
    @ResponseBody
    @ApiOperation(value = "排序", notes = "对资源进行排序")
    @ApiImplicitParam(name = "sortForm", value = "资源排序值,结构为[{id:sort},{id:sort}],不需要key值" ,required = true,example = "[{'2f640b82-239b-45e7-9999-65c63ab45e51':3},{'1a16f35a-4c9e-457a-9761-539ef2acbaad':1}]")
    public Result sort(@RequestParam("sortForm") String sortForm){
        //获取id和顺序值的字符串
        if(StringUtils.isEmpty(sortForm)){
            return ResultUtil.success();
        }

//        //转字符串为list集合
//        List<HashMap> sortFormList = JSON.parseArray(sortForm, HashMap.class);
//        Map<String,Integer> sortFormMaps = new HashMap<String,Integer>();
//        //封装list为单个map集合,此过程可以过滤掉重复的值
//        for (Map<String,Integer> sortFormMap: sortFormList) {
//            for (String key:sortFormMap.keySet()) {
//                sortFormMaps.put(key,sortFormMap.get(key));
//            }
//        }

        //解析传入的排序字符串,并封装成map集合
        Map<String,Integer> sortFormMaps = SortFormUtil.parseSortForm(sortForm);

        //执行排序操作
        boolean result = iResourceService.sort(sortFormMaps);

        return result?ResultUtil.success():ResultUtil.error(ResultEnum.ERROR);
    }

}
