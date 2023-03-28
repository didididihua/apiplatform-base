package cn.chong.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 接口信息表
 * 
 * @author tangchongjie
 * @email 1514176166@qq.com
 * @date 2023-03-01 20:24:06
 */
@Data
@TableName("interface_info")
public class InterfaceInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 接口地址
	 */
	private String url;
	/**
	 * 请求头
	 */
	private String requestHeader;
	/**
	 * 响应头
	 */
	private String responseHeader;

	/**
	 * 请求参数
	 */
	private String requestParam;
	/**
	 * 创建人
	 */
	private String userid;
	/**
	 * 接口状态（0 - 关闭， 1 - 开启））
	 */
	private Integer status;
	/**
	 * 请求类型
	 */
	private String method;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 是否删除(0-未删, 1-已删)
	 */
	private Integer isDelete;

}
