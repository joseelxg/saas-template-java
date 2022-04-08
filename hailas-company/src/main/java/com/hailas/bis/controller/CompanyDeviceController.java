package com.hailas.bis.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.hailas.common.annotation.Log;
import com.hailas.common.core.controller.BaseController;
import com.hailas.common.core.domain.AjaxResult;
import com.hailas.common.enums.BusinessType;
import com.hailas.bis.domain.CompanyDevice;
import com.hailas.bis.domain.CompanyDevice;
import com.hailas.bis.service.ICompanyDeviceService;
import com.hailas.common.utils.poi.ExcelUtil;
import com.hailas.common.core.page.TableDataInfo;

/**
 * 公司设备Controller
 * 
 * @author jose
 * @date 2022-03-31
 */
@Slf4j
@Api(tags = "公司设备相关接口")
@RestController
@RequestMapping("/bis/companyDevice")
public class CompanyDeviceController extends BaseController
{
    @Autowired
    private ICompanyDeviceService companyDeviceService;

    /**
     * 查询公司设备列表
     */
    @ApiOperation("查询公司设备列表")
    @PreAuthorize("@ss.hasPermi('bis:companyDevice:list')")
    @GetMapping("/list")
    public TableDataInfo<CompanyDevice> list(CompanyDevice companyDevice)
    {
        startPage();
        List<CompanyDevice> list = companyDeviceService.selectCompanyDeviceList(companyDevice);
        return getDataTable(list);
    }

    /**
     * 导出公司设备列表
     */
    @ApiOperation("导出公司设备列表")
    @PreAuthorize("@ss.hasPermi('bis:companyDevice:export')")
    @Log(title = "公司设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CompanyDevice companyDevice)
    {
        List<CompanyDevice> list = companyDeviceService.selectCompanyDeviceList(companyDevice);
        ExcelUtil<CompanyDevice> util = new ExcelUtil<CompanyDevice>(CompanyDevice.class);
        util.exportExcel(response, list, "公司设备数据");
    }

    /**
     * 获取公司设备详细信息
     */
    @ApiOperation(value = "获取公司设备详细信息",response = CompanyDevice.class)
    @PreAuthorize("@ss.hasPermi('bis:companyDevice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(companyDeviceService.selectCompanyDeviceById(id));
    }

    /**
     * 新增公司设备
     */
    @ApiOperation("新增公司设备")
    @PreAuthorize("@ss.hasPermi('bis:companyDevice:add')")
    @Log(title = "公司设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CompanyDevice companyDevice)
    {
        return toAjax(companyDeviceService.insertCompanyDevice(companyDevice));
    }

    /**
     * 修改公司设备
     */
    @ApiOperation("修改公司设备")
    @PreAuthorize("@ss.hasPermi('bis:companyDevice:edit')")
    @Log(title = "公司设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CompanyDevice companyDevice)
    {
        return toAjax(companyDeviceService.updateCompanyDevice(companyDevice));
    }

    /**
     * 删除公司设备
     */
    @ApiOperation("删除公司设备")
    @PreAuthorize("@ss.hasPermi('bis:companyDevice:remove')")
    @Log(title = "公司设备", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(companyDeviceService.deleteCompanyDeviceByIds(ids));
    }
}
