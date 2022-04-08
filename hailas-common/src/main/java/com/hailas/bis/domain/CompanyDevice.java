package com.hailas.bis.domain;

import com.hailas.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModelProperty;
import com.hailas.common.annotation.Excel;
import lombok.Data;

/**
 * 公司设备对象 com_device
 * 
 * @author jose
 * @date 2022-03-31
 */
@Data
public class CompanyDevice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 设备类型 */
    @Excel(name = "设备类型")
    @ApiModelProperty("设备类型")
    private Integer type;

    /** 公司id */
    @Excel(name = "公司id")
    @ApiModelProperty("公司id")
    private Long companyId;

    /** 公司名称 */
    @Excel(name = "公司名称")
    @ApiModelProperty("公司名称")
    private String companyName;

    /** 设备名称 */
    @Excel(name = "设备名称")
    @ApiModelProperty("设备名称")
    private String name;

    /** 设备编号 */
    @Excel(name = "设备编号")
    @ApiModelProperty("设备编号")
    private String deviceId;

    /** 服务标识 */
    @Excel(name = "服务标识")
    @ApiModelProperty("服务标识")
    private String serverPort;

    /** 所在维度 */
    @Excel(name = "所在维度")
    @ApiModelProperty("所在维度")
    private String lat;

    /** 所在经度 */
    @Excel(name = "所在经度")
    @ApiModelProperty("所在经度")
    private String lan;

    /**  */
    private String deleted;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("companyId", getCompanyId())
            .append("name", getName())
            .append("deviceId", getDeviceId())
            .append("serverPort", getServerPort())
            .append("lat", getLat())
            .append("lan", getLan())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
