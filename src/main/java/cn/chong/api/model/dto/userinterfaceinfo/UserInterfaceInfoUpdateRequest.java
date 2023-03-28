package cn.chong.api.model.dto.userinterfaceinfo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 调用用户 id
     */
    private Long userId;
    /**
     * 接口 id
     */
    private Long interfaceinfoId;
    /**
     * 总调用次数
     */
    private Integer totalNum;
    /**
     * 剩余调用次数
     */
    private Integer leftNum;
    /**
     * 0-正常， 1-禁用
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除
     */
    private Integer isDelete;
}