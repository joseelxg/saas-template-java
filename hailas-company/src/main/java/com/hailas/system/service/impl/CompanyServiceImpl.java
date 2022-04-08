package com.hailas.system.service.impl;

import java.util.List;

import com.hailas.common.constant.UserConstants;
import com.hailas.common.core.domain.entity.SysDept;
import com.hailas.common.core.domain.entity.SysUser;
import com.hailas.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.hailas.common.utils.SecurityUtils;
import com.hailas.system.mapper.CompanyMapper;
import com.hailas.system.service.ICompanyService;
import com.hailas.common.core.domain.entity.Company;
import org.springframework.transaction.annotation.Transactional;

/**
 * 公司配置Service业务层处理
 * 
 * @author jose
 * @date 2022-03-23
 */
@Slf4j
@Service
public class CompanyServiceImpl implements ICompanyService
{
    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 查询公司列
     *
     * @param id 公司列主键
     * @return 公司列
     */
    @Override
    public Company selectCompanyById(Long id)
    {
        return companyMapper.selectCompanyById(id);
    }

    /**
     * 查询公司列列表
     *
     * @param company 公司列
     * @return 公司列
     */
    @Override
    public List<Company> selectCompanyList(Company company)
    {
        return companyMapper.selectCompanyList(company);
    }



    /**
     * 修改公司列
     *
     * @param company 公司列
     * @return 结果
     */
    @Override
    public int updateCompany(Company company)
    {
        company.prepareUpdate();
        return companyMapper.updateCompany(company);
    }

    /**
     * 批量删除公司列
     *
     * @param ids 需要删除的公司列主键
     * @return 结果
     */
    @Override
    public int deleteCompanyByIds(Long[] ids)
    {


        return companyMapper.deleteCompanyByIds(ids,SecurityUtils.getLoginUser().getUsername());
    }

    /**
     * 删除公司列信息
     *
     * @param id 公司列主键
     * @return 结果
     */
    @Override
    public int deleteCompanyById(Long id)
    {
        return companyMapper.deleteCompanyById(id,SecurityUtils.getLoginUser().getUsername());
    }
}
