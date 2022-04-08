package com.hailas.common.core.domain.entity;

import com.hailas.common.annotation.Excel;
import com.hailas.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 公司列对象 com_company
 * 
 * @author jose
 * @date 2022-03-24
 */
@Data
public class Company extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公司id */
    private Long id;

    /** 平台名称 */
    @Excel(name = "平台名称")
    @ApiModelProperty("平台名称")
    private String platformName;

    /** 公司名称 */
    @Excel(name = "公司名称")
    @ApiModelProperty("公司名称")
    private String name;

    /** 负责人 */
    @Excel(name = "负责人")
    @ApiModelProperty("负责人")
    private String leader;

    /** 联系电话 */
    @Excel(name = "联系电话")
    @ApiModelProperty("联系电话")
    private String phone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    @ApiModelProperty("邮箱")
    private String email;

    /** 公司状态（0正常 1停用） */
    @Excel(name = "公司状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty("公司状态")
    private String status;

    /** $column.columnComment */
    private String deleted;


    /** 用户账号 */
    @Excel(name = "用户账号")
    @ApiModelProperty("用户账号")
    private String username;


    /** 用户密码 */
    @Excel(name = "用户密码")
    @ApiModelProperty("用户密码")
    private String password;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("platformName", getPlatformName())
            .append("name", getName())
            .append("remark", getRemark())
            .append("leader", getLeader())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
