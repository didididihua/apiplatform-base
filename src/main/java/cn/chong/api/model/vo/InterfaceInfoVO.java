package cn.chong.api.model.vo;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--07 23:58
 * @description
 */

import cn.chong.common.model.entity.InterfaceInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO extends InterfaceInfoEntity {

    /**
     * 调用次数
     */
    private Integer totalNum;

    private static final long serialVersionUID = 1L;
}

