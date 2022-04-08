package com.hailas.common.core.domain;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hailas.common.annotation.Excel;
import com.hailas.common.utils.SecurityUtils;

/**
 * Entity基类
 * 
 * @author ruoyi
 */
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 搜索值 */
    private String searchValue;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

    private String deleted;

    /** 公司ID */
    @Excel(name = "公司编号", type = Excel.Type.IMPORT)
    private Long companyId;

    /** 请求参数 */
    private Map<String, Object> params;

    public String getSearchValue()
    {
        return searchValue;
    }

    public void setSearchValue(String searchValue)
    {
        this.searchValue = searchValue;
    }

    public String getCreateBy()
    {
        return createBy;
    }

    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getUpdateBy()
    {
        return updateBy;
    }

    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }

    public void prepareCreate()
    {
        try {
            this.createBy=SecurityUtils.getLoginUser().getUsername();
        }
        catch (Exception e)
        {
            try {
//                this.createBy=SecurityUtils.getLoginMember().getUserId().toString();
            }
            catch (Exception e1) {}
        }
        this.createTime = new Date();
        if (this.companyId==null)
        {
            this.companyId= SecurityUtils.getCompanyId();
        }
//        this.updateTime = new Date();
    }

    public void prepareUpdate()
    {
        this.updateBy=SecurityUtils.getLoginUser().getUsername();
        this.updateTime = new Date();
        if (this.companyId==null)
        {
            this.companyId= SecurityUtils.getCompanyId();
        }
    }
    public void setCurrentCompany()
    {
        this.companyId= SecurityUtils.getCompanyId();
    }

    public static <T> T toDomain(Object obj,Class<T> clazz)
    {
        try {
            T o = clazz.getDeclaredConstructor().newInstance();

            List thisFieldsList = getFields(obj.getClass());

            int a=1;
            for (Object fields:thisFieldsList) {
                Field[] f = (Field[]) fields;
                for (Field field : f) {

                    Field field1 = getField(clazz,field.getName());
                    if (field1!=null)
                    {
                        try {
                            field1.setAccessible(true);
                            field.setAccessible(true);
                            field1.set(o, field.get(obj));
                        }
                        catch (Exception e)
                        {
//                            e.printStackTrace();
                        }
                    }
                    System.out.println(field);
                }
            }

            return o;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static ArrayList<Field[]> getFields(Class clazz)
    {
        List fieldsList = new ArrayList<Field[]>();
        while (clazz != null&&!clazz.equals(Object.class)) {
            Field[] declaredFields = clazz.getDeclaredFields();
            fieldsList.add(declaredFields);

            clazz =  clazz.getSuperclass();
        }
        return (ArrayList<Field[]>) fieldsList;
    }

    private static Field getField(Class clazz,String name)
    {
        Field field = null;
        while (clazz != null&&!clazz.equals(Object.class))
        {
            try {
                field = clazz.getDeclaredField(name);
            }
            catch (Exception e)
            {

            }
            if (field==null)
            {
                clazz =  clazz.getSuperclass();
            }
            else
            {
                return field;
            }
        }
        return null;
    }

}
