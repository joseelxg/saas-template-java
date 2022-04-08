package com.hailas.system.mapper;
import com.hailas.common.core.domain.entity.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.hailas.common.core.domain.entity.Company;
/**
 * 公司列Mapper接口
 *
 * @author jose
 * @date 2022-03-24
 */
public interface CompanyMapper
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
    public int insertCompany(Company company);

    /**
     * 修改公司列
     *
     * @param company 公司列
     * @return 结果
     */
    public int updateCompany(Company company);

    /**
     * 删除公司列
     *
     * @param id 公司列主键
     * @param deleteBy 删除者
     * @return 结果
     */
    public int deleteCompanyById(@Param("id") Long id,@Param("deleteBy") String deleteBy);

    /**
     * 批量删除公司列
     *
     * @param ids 需要删除的数据主键集合
     * @param deleteBy 删除者
     * @return 结果
     */
    public int deleteCompanyByIds(@Param("ids") Long[] ids,@Param("deleteBy") String deleteBy);
}
