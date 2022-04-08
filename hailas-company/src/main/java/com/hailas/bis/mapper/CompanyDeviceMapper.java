package com.hailas.bis.mapper;

import java.util.List;
import com.hailas.bis.domain.CompanyDevice;
import org.apache.ibatis.annotations.Param;

/**
 * 公司设备Mapper接口
 * 
 * @author jose
 * @date 2022-03-31
 */
public interface CompanyDeviceMapper 
{
    /**
     * 查询公司设备
     * 
     * @param id 公司设备主键
     * @return 公司设备
     */
    public CompanyDevice selectCompanyDeviceById(Long id);

    /**
     * 查询公司设备列表
     * 
     * @param companyDevice 公司设备
     * @return 公司设备集合
     */
    public List<CompanyDevice> selectCompanyDeviceList(CompanyDevice companyDevice);

    /**
     * 新增公司设备
     * 
     * @param companyDevice 公司设备
     * @return 结果
     */
    public int insertCompanyDevice(CompanyDevice companyDevice);

    /**
     * 修改公司设备
     * 
     * @param companyDevice 公司设备
     * @return 结果
     */
    public int updateCompanyDevice(CompanyDevice companyDevice);

    /**
     * 删除公司设备
     * 
     * @param id 公司设备主键
     * @param deleteBy 删除者
     * @return 结果
     */
    public int deleteCompanyDeviceById(@Param("id") Long id,@Param("deleteBy") String deleteBy);

    /**
     * 批量删除公司设备
     * 
     * @param ids 需要删除的数据主键集合
     * @param deleteBy 删除者
     * @return 结果
     */
    public int deleteCompanyDeviceByIds(@Param("ids") Long[] ids,@Param("deleteBy") String deleteBy);
}
