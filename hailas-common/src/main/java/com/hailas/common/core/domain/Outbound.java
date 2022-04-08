package com.hailas.common.core.domain;

import com.hailas.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModelProperty;
import com.hailas.common.annotation.Excel;
import lombok.Data;

/**
 * 农资出库对象 tb_outbound
 * 
 * @author jose
 * @date 2022-03-07
 */
@Data
public class Outbound extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 出库编号 */
    @Excel(name = "出库编号")
    @ApiModelProperty("出库编号")
    private String no;

    /** 农资类型：1=种子；2=肥料；3=农药 */
    @Excel(name = "农资类型：1=种子；2=肥料；3=农药")
    @ApiModelProperty("农资类型：1=种子；2=肥料；3=农药")
    private Integer type;

    /** 入库id */
    private Long inboundId;

    /** 入库编号 */
    private String inboundNo;

    /** 备案id */
    @Excel(name = "备案id")
    @ApiModelProperty("备案id")
    private Long recordId;

    /** 农资名称 */
    @Excel(name = "农资名称")
    @ApiModelProperty("农资名称")
    private String recordName;

    /** 数量 */
    @Excel(name = "数量")
    @ApiModelProperty("数量")
    private Long num;

    /** 已用数量 */
    @Excel(name = "已用数量")
    @ApiModelProperty("已用数量")
    private Long useNum;

    /** 已用数量 */
    @Excel(name = "退回数量")
    @ApiModelProperty("退回数量")
    private Long backNum;

    /** $column.columnComment */
    private String deleted;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("no", getNo())
            .append("type", getType())
            .append("inboundId", getInboundId())
            .append("recordId", getRecordId())
            .append("num", getNum())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
