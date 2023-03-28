package cn.chong.apimook.service;


import cn.chong.common.model.dto.SearchRequest;
import org.springframework.stereotype.Service;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--23 19:10
 * @description
 */

public interface InterfaceService {
    /**
     *
     * @param searchRequest 搜索关键词
     * @return json字符串
     */
    String getPictureUrl(SearchRequest searchRequest);
}
