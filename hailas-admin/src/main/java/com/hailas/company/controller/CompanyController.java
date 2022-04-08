package com.hailas.company.controller;

import com.hailas.common.annotation.Log;
import com.hailas.common.core.controller.BaseController;
import com.hailas.common.core.domain.AjaxResult;
import com.hailas.common.core.domain.entity.Company;
import com.hailas.common.core.page.TableDataInfo;
import com.hailas.common.enums.BusinessType;
import com.hailas.common.utils.poi.ExcelUtil;
import com.hailas.company.service.ICompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 公司列Controller
 * 
 * @author jose
 * @date 2022-03-24
 */
@Slf4j
@Api(tags = "公司列相关接口")
@RestController
@RequestMapping("/bis/company")
public class CompanyController extends BaseController
{
    @Autowired
    private ICompanyService companyService;

    /**
     * 查询公司列列表
     */
    @ApiOperation("查询公司列列表")
    @PreAuthorize("@ss.hasPermi('bis:company:list')")
    @GetMapping("/list")
    public TableDataInfo<Company> list(Company company)
    {
        startPage();
        List<Company> list = companyService.selectCompanyList(company);
        return getDataTable(list);
    }

    /**
     * 导出公司列列表
     */
    @ApiOperation("导出公司列列表")
    @PreAuthorize("@ss.hasPermi('bis:company:export')")
    @Log(title = "公司列", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Company company)
    {
        List<Company> list = companyService.selectCompanyList(company);
        ExcelUtil<Company> util = new ExcelUtil<Company>(Company.class);
        util.exportExcel(response, list, "公司列数据");
    }

    /**
     * 获取公司列详细信息
     */
    @ApiOperation(value = "获取公司列详细信息",response = Company.class)
    @PreAuthorize("@ss.hasPermi('bis:company:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(companyService.selectCompanyById(id));
    }

    /**
     * 新增公司列
     */
    @ApiOperation("新增公司列")
    @PreAuthorize("@ss.hasPermi('bis:company:add')")
    @Log(title = "公司列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Company company)
    {
        return toAjax(companyService.insertCompany(company));
    }

    /**
     * 修改公司列
     */
    @ApiOperation("修改公司列")
    @PreAuthorize("@ss.hasPermi('bis:company:edit')")
    @Log(title = "公司列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Company company)
    {
        return toAjax(companyService.updateCompany(company));
    }

    /**
     * 删除公司列
     */
    @ApiOperation("删除公司列")
    @PreAuthorize("@ss.hasPermi('bis:company:remove')")
    @Log(title = "公司列", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(companyService.deleteCompanyByIds(ids));
    }
}
