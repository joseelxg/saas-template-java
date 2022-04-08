package com.hailas.framework.aspectj;

import com.hailas.common.annotation.DataCompany;
import com.hailas.common.annotation.DataScope;
import com.hailas.common.core.domain.BaseEntity;
import com.hailas.common.core.domain.entity.SysRole;
import com.hailas.common.core.domain.entity.SysUser;
import com.hailas.common.core.domain.model.LoginUser;
import com.hailas.common.utils.SecurityUtils;
import com.hailas.common.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author jose
 * @date 2022/3/24 5:55 下午
 * @description
 */
@Aspect
@Component
public class DataCompanyAspect {

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_COMPANY = "dataCompany";

    @Before("@annotation(controllerDataCompany)")
    public void doBefore(JoinPoint point, DataCompany controllerDataCompany) throws Throwable
    {
        handleDataCompany(point);
    }

    protected void handleDataCompany(final JoinPoint joinPoint)
    {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，则不过滤数据
            if (StringUtils.isNotNull(currentUser) )
            {
                dataScopeFilter(joinPoint, currentUser);
            }
        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param user 用户
     *
     */
    public static void dataScopeFilter(JoinPoint joinPoint, SysUser user)
    {
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof BaseEntity)
        {
            BaseEntity baseEntity = (BaseEntity) params;
            baseEntity.setCompanyId(user.getCompanyId());
        }

    }



}


