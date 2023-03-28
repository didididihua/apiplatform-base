package cn.chong.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户调用接口关系表
 * 
 * @author tangchongjie
 * @email 1514176166@qq.com
 * @date 2023-03-04 16:30:16
 */
@Data
@TableName("user_interface_info")
public class UserInterfaceInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
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
