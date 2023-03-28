package cn.chong.api.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @author yupi
 */
@Data
public class InterfaceInfoDeleteRequest implements Serializable {

    /**
     * api id
     */
    private long apiId;

    private static final long serialVersionUID = 1L;
}