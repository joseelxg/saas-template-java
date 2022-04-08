package com.hailas.bis.service.impl;

import java.util.List;

import com.hailas.common.annotation.DataCompany;
import com.hailas.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.hailas.common.utils.SecurityUtils;
import com.hailas.bis.mapper.CompanyDeviceMapper;
import com.hailas.bis.domain.CompanyDevice;
import com.hailas.bis.service.ICompanyDeviceService;
import org.springframework.transaction.annotation.Transactional;
/**
 * 公司设备Service业务层处理
 * 
 * @author jose
 * @date 2022-03-31
 */
@Slf4j
@Service
@Transactional
public class CompanyDeviceServiceImpl implements ICompanyDeviceService 
{
    @Autowired
    private CompanyDeviceMapper companyDeviceMapper;

    /**
     * 查询公司设备
     * 
     * @param id 公司设备主键
     * @return 公司设备
     */
    @Override
    public CompanyDevice selectCompanyDeviceById(Long id)
    {
        return companyDeviceMapper.selectCompanyDeviceById(id);
    }

    /**
     * 查询公司设备列表
     * 
     * @param companyDevice 公司设备
     * @return 公司设备
     */
    @Override
    @DataCompany
    public List<CompanyDevice> selectCompanyDeviceList(CompanyDevice companyDevice)
    {
        return companyDeviceMapper.selectCompanyDeviceList(companyDevice);
    }

    /**
     * 新增公司设备
     * 
     * @param companyDevice 公司设备
     * @return 结果
     */
    @Override
    public int insertCompanyDevice(CompanyDevice companyDevice)
    {
        companyDevice.prepareCreate();
        return companyDeviceMapper.insertCompanyDevice(companyDevice);
    }

    /**
     * 修改公司设备
     * 
     * @param companyDevice 公司设备
     * @return 结果
     */
    @Override
    public int updateCompanyDevice(CompanyDevice companyDevice)
    {
        companyDevice.prepareUpdate();
        return companyDeviceMapper.updateCompanyDevice(companyDevice);
    }

    /**
     * 批量删除公司设备
     * 
     * @param ids 需要删除的公司设备主键
     * @return 结果
     */
    @Override
    public int deleteCompanyDeviceByIds(Long[] ids)
    {


        return companyDeviceMapper.deleteCompanyDeviceByIds(ids,SecurityUtils.getLoginUser().getUsername());
    }

    /**
     * 删除公司设备信息
     * 
     * @param id 公司设备主键
     * @return 结果
     */
    @Override
    public int deleteCompanyDeviceById(Long id)
    {
        return companyDeviceMapper.deleteCompanyDeviceById(id,SecurityUtils.getLoginUser().getUsername());
    }
}
