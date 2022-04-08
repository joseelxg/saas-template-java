package com.hailas.company.service.impl;

import com.hailas.common.constant.UserConstants;
import com.hailas.common.core.domain.entity.Company;
import com.hailas.common.core.domain.entity.SysDept;
import com.hailas.common.core.domain.entity.SysUser;
import com.hailas.common.exception.ServiceException;
import com.hailas.common.utils.SecurityUtils;
import com.hailas.company.mapper.ComDeptMapper;
import com.hailas.company.mapper.ComMenuMapper;
import com.hailas.company.mapper.CompanyMapper;
import com.hailas.company.service.ICompanyService;
import com.hailas.company.service.ICompanyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公司列Service业务层处理
 * 
 * @author jose
 * @date 2022-03-24
 */
@Slf4j
@Service
@Transactional
public class CompanyServiceImpl implements ICompanyService
{
    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private ICompanyUserService userService;

    @Autowired
    private ComMenuMapper comMenuMapper;
    @Autowired
    ComDeptMapper comDeptMapper;
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
     * 新增公司列
     * 
     * @param company 公司列
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public int insertCompany(Company company)
    {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(company.getUsername())))
        {
            throw new  ServiceException("新增用户'" + company.getUsername() + "'失败，登录账号已存在");
        }
        company.prepareCreate();
        companyMapper.insertCompany(company);

        comMenuMapper.saveDefaultCompanyMenu(company.getId());

        SysDept dept = new SysDept();
        dept.setCompanyId(company.getId());
        dept.setAncestors("0");
        dept.setParentId(0L);
        dept.setDeptName(company.getName());
        dept.setEmail(company.getEmail());
        dept.setLeader(company.getLeader());
        dept.setPhone(company.getPhone());
        dept.prepareCreate();
        comDeptMapper.insertDept(dept);

        SysUser user = new SysUser();
        user.setCompanyId(company.getId());
        user.setUserName(company.getUsername());
        user.setRoleIds( new Long[]{1L});
        user.setPassword(company.getPassword());
        user.setIsCompanyAdmin(1);
        user.setDeptId(dept.getDeptId());
        user.setNickName(company.getLeader());
        user.setCreateBy(SecurityUtils.getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return userService.insertUser(user);
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
