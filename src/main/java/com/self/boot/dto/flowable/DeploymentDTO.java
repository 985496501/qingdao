package com.self.boot.dto.flowable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: jinyun
 * @date: 2021/1/15
 */
@Data
@AllArgsConstructor
public class DeploymentDTO {
    /**
     * 部署key
     */
    private String deploymentKey;

    /**
     * 部署name
     */
    private String deploymentName;

    /**
     * 部署的 租户id
     */
    private String tenantId;
}
