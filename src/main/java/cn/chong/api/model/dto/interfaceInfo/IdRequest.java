package cn.chong.api.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @author tangchongjie
 */
@Data
public class IdRequest implements Serializable {

    /**
     * api id
     */
    private long apiId;

    private static final long serialVersionUID = 1L;
}