package com.hailas.system.service;

import java.util.List;
import com.hailas.common.core.domain.entity.Company;
/**
 * 公司配置Service接口
 * 
 * @author jose
 * @date 2022-03-23
 */
public interface ICompanyService
{
    /**
     * 查询公司列
     *
     * @param id 公司列主键
     * @return 公司列
     */
    public Company selectCompanyById(Long id);

    /**
     * 查询公司列列表
     *
     * @param company 公司列
     * @return 公司列集合
     */
    public List<Company> selectCompanyList(Company company);

    /**
     * 新增公司列
     *
     * @param company 公司列
     * @return 结果
     */
//    public int insertCompany(Company company);

    /**
     * 修改公司列
     *
     * @param company 公司列
     * @return 结果
     */
    public int updateCompany(Company company);

    /**
     * 批量删除公司列
     *
     * @param ids 需要删除的公司列主键集合
     * @return 结果
     */
    public int deleteCompanyByIds(Long[] ids);

    /**
     * 删除公司列信息
     *
     * @param id 公司列主键
     * @return 结果
     */
    public int deleteCompanyById(Long id);
}
