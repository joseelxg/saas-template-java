package com.hailas.bis.service;

import java.util.List;
import com.hailas.bis.domain.CompanyDevice;

/**
 * 公司设备Service接口
 * 
 * @author jose
 * @date 2022-03-31
 */
public interface ICompanyDeviceService 
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
     * 批量删除公司设备
     * 
     * @param ids 需要删除的公司设备主键集合
     * @return 结果
     */
    public int deleteCompanyDeviceByIds(Long[] ids);

    /**
     * 删除公司设备信息
     * 
     * @param id 公司设备主键
     * @return 结果
     */
    public int deleteCompanyDeviceById(Long id);
}
