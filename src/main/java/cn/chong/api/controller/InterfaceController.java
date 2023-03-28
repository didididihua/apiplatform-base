package cn.chong.api.controller;

import cn.chong.api.annotation.AuthCheck;
import cn.chong.api.common.BaseResponse;
import cn.chong.api.common.DeleteRequest;
import cn.chong.api.common.ErrorCode;
import cn.chong.api.common.ResultUtils;
import cn.chong.api.constant.CommonConstant;
import cn.chong.api.exception.BusinessException;
import cn.chong.api.model.dto.interfaceInfo.*;
import cn.chong.common.model.entity.InterfaceInfoEntity;
import cn.chong.common.model.entity.User;
import cn.chong.common.model.enums.InterfaceStatusEnum;
import cn.chong.api.service.InterfaceInfoService;
import cn.chong.api.service.UserService;
import cn.chong.apimook.client.UserClient;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--02 23:28
 * @description
 */
@RestController
@RequestMapping("/interface")
@Slf4j
public class InterfaceController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private UserClient userClient;


    /**
     * 将接口api开启
     * @param idRequest 接口id
     * @param request HttpServletRequest
     * @return Boolean
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> onlineApi(@RequestBody IdRequest idRequest, HttpServletRequest request){

        //校验
        if (idRequest == null || idRequest.getApiId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //判断是否存在
        InterfaceInfoEntity entity = interfaceInfoService.getById(idRequest.getApiId());

        if(entity == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"该api接口不存在");
        }

        //验证请求是否可用
//        String useable = interfaceInfoService.valiedInterfaceUseable(idRequest);
//
//        if(ObjectUtil.isEmpty(useable)){
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "该api接口无法调用");
//        }

        //修改数据库api接口状态
        InterfaceInfoEntity interfaceInfoEntity = new InterfaceInfoEntity();
        interfaceInfoEntity.setId(idRequest.getApiId());
        interfaceInfoEntity.setStatus(InterfaceStatusEnum.ONLINE.getValue());
        interfaceInfoService.updateById(interfaceInfoEntity);

        return ResultUtils.success(true);
    }


    /**
     * 将api接口关闭
     * @param idRequest 接口id
     * @param request HttpServletRequest
     * @return Boolean
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> offlineApi(@RequestBody IdRequest idRequest, HttpServletRequest request){


        //校验
        if (idRequest == null || idRequest.getApiId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //判断是否存在
        InterfaceInfoEntity entity = interfaceInfoService.getById(idRequest.getApiId());

        if(entity == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"该api接口不存在");
        }

        //验证请求是否可用
//        String useable = interfaceInfoService.valiedInterfaceUseable(idRequest);
//
//        if(ObjectUtil.isEmpty(useable)){
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "该api接口无法调用");
//        }

        //修改数据库api接口状态
        InterfaceInfoEntity interfaceInfoEntity = new InterfaceInfoEntity();
        interfaceInfoEntity.setId(idRequest.getApiId());
        interfaceInfoEntity.setStatus(InterfaceStatusEnum.OFFLINE.getValue());
        interfaceInfoService.updateById(interfaceInfoEntity);

        return ResultUtils.success(true);
    }


    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfoEntity interfaceInfo = new InterfaceInfoEntity();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        // 校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);

        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserid(loginUser.getId().toString());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //获取当前用户
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfoEntity oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserid().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param interfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                            HttpServletRequest request) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfoEntity interfaceInfo = new InterfaceInfoEntity();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);

        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceInfoUpdateRequest.getId();

        // 判断是否存在
        InterfaceInfoEntity oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserid().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfoEntity> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfoEntity interfaceInfo = interfaceInfoService.getById(id);
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfoEntity>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfoEntity interfaceInfoQuery = new InterfaceInfoEntity();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfoEntity> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfoEntity> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<InterfaceInfoEntity>> listInterfaceInfoByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfoEntity interfaceInfoQuery = new InterfaceInfoEntity();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String content = interfaceInfoQuery.getDescription();

        // content 需支持模糊搜索
        interfaceInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfoEntity> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(content), "description", content);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfoEntity> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);

        //查出所有数据的数目
        long count = interfaceInfoService.count();
        interfaceInfoPage.setTotal(count);
        return ResultUtils.success(interfaceInfoPage);
    }

    @PostMapping("/invoke")
    public BaseResponse<Object> testInvokInterface(@RequestBody InterfaceInfoInvokeRequest invokeRequest, HttpServletRequest request){
        //检验：
        if(invokeRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //判断该接口状态
        interfaceInfoService.hasInterfaceInfo(invokeRequest.getId());

        //获取当前用户，并取得其ak,sk
        User loginUser = userService.getLoginUser(request);

        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();

        UserClient chongUserClient = new UserClient(accessKey, secretKey);
        log.info("ak:{}, sk:{}, param:{}", accessKey, secretKey,invokeRequest.getUserRequestParams());

        Gson gson = new Gson();
        cn.chong.apimook.entity.User json = gson.fromJson(invokeRequest.getUserRequestParams(), cn.chong.apimook.entity.User.class);

        String nameByPost = chongUserClient.getNameByPost(json);

        return ResultUtils.success(nameByPost);
    }


}
