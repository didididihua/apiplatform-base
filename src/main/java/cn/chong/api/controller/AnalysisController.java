package cn.chong.api.controller;

import cn.chong.api.common.BaseResponse;
import cn.chong.api.common.ResultUtils;
import cn.chong.api.constant.CommonConstant;
import cn.chong.api.model.vo.InterfaceInfoVO;
import cn.chong.api.service.InterfaceInfoService;
import cn.chong.api.service.UserInterfaceInfoService;
import cn.chong.common.model.entity.InterfaceInfoEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--08 0:00
 * @description
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 获得调用量前五的接口信息
     * @return 接口信息list
     */
    @GetMapping("/")
    public BaseResponse<List<InterfaceInfoVO>> getTopFiveInterface(){

        List<InterfaceInfoVO> list = userInterfaceInfoService.analysisInterfaceInfo(CommonConstant.TOP_INTERFACEINFO_NUM);

        return ResultUtils.success(list);
    }

}
