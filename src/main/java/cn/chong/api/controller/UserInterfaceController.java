package cn.chong.api.controller;

import cn.chong.api.annotation.AuthCheck;
import cn.chong.api.common.BaseResponse;
import cn.chong.api.common.ErrorCode;
import cn.chong.api.common.ResultUtils;
import cn.chong.api.constant.CommonConstant;
import cn.chong.api.exception.BusinessException;
import cn.chong.api.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import cn.chong.common.model.entity.UserInterfaceInfoEntity;
import cn.chong.api.service.UserInterfaceInfoService;
import cn.chong.api.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 接口调用次数统计
 * @author: tangchongjie
 * @creattime: 2023--03--04 16:57
 * @description
 */
@RestController
@RequestMapping("/userInterface")
public class UserInterfaceController {

    @Resource
    private UserInterfaceInfoService userInterfaceService;

    @Resource
    private UserService userService;


    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<UserInterfaceInfoEntity> getUserInterfaceById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfoEntity userInterface = userInterfaceService.getById(id);
        return ResultUtils.success(userInterface);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param userInterfaceQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<UserInterfaceInfoEntity>> listUserInterface(UserInterfaceInfoQueryRequest userInterfaceQueryRequest) {
        UserInterfaceInfoEntity userInterfaceQuery = new UserInterfaceInfoEntity();
        if (userInterfaceQueryRequest != null) {
            BeanUtils.copyProperties(userInterfaceQueryRequest, userInterfaceQuery);
        }
        QueryWrapper<UserInterfaceInfoEntity> queryWrapper = new QueryWrapper<>(userInterfaceQuery);
        List<UserInterfaceInfoEntity> userInterfaceList = userInterfaceService.list(queryWrapper);
        return ResultUtils.success(userInterfaceList);
    }

    /**
     * 分页获取列表
     *
     * @param userInterfaceQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Page<UserInterfaceInfoEntity>> listUserInterfaceByPage(UserInterfaceInfoQueryRequest userInterfaceQueryRequest, HttpServletRequest request) {
        if (userInterfaceQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfoEntity userInterfaceQuery = new UserInterfaceInfoEntity();
        BeanUtils.copyProperties(userInterfaceQueryRequest, userInterfaceQuery);
        long current = userInterfaceQueryRequest.getCurrent();
        long size = userInterfaceQueryRequest.getPageSize();

        String sortField = userInterfaceQueryRequest.getSortField();
        String sortOrder = userInterfaceQueryRequest.getSortOrder();

        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserInterfaceInfoEntity> queryWrapper = new QueryWrapper<>(userInterfaceQuery);

        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<UserInterfaceInfoEntity> userInterfacePage = userInterfaceService.page(new Page<>(current, size), queryWrapper);

        return ResultUtils.success(userInterfacePage);
    }

}
