package com.hailas.company.controller;

import com.hailas.common.annotation.Log;
import com.hailas.common.constant.UserConstants;
import com.hailas.common.core.controller.BaseController;
import com.hailas.common.core.domain.AjaxResult;
import com.hailas.common.core.domain.entity.SysMenu;
import com.hailas.common.enums.BusinessType;
import com.hailas.common.utils.StringUtils;
import com.hailas.company.service.IComMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单信息
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/com/menu")
public class ComMenuController extends BaseController
{
    @Autowired
    private IComMenuService menuService;



    /**
     * 加载对应企业菜单列表树
     */
    @GetMapping(value = "/companyMenuTreeselect")
    public AjaxResult roleMenuTreeselect(SysMenu sysMenu)
    {
        List<SysMenu> menus = menuService.selectMenuListByCompany(sysMenu);
        AjaxResult ajax = AjaxResult.success(menus);

//        ajax.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
//        ajax.put("menus", menuService.buildMenuTreeSelect(menus));
        return ajax;
    }


    /**
     * 获取对应企业菜单
     */
    @GetMapping(value = "company/{companyId}/{menuId}")
    public AjaxResult selectCompanyMenuById(@PathVariable("companyId") Long companyId,@PathVariable("menuId") Long menuId)
    {
        SysMenu menu = menuService.selectCompanyMenuById(menuId,companyId);
        AjaxResult ajax = AjaxResult.success(menu);

        return ajax;
    }

    /**
     * 获取对应企业菜单
     */
    @PostMapping(value = "company/save")
    public AjaxResult selectCompanyMenuById(@RequestBody SysMenu sysMenu)
    {

        AjaxResult ajax = AjaxResult.success( menuService.saveCompanyMenu(sysMenu));

        return ajax;
    }

    /**
     * 获取菜单列表
     */
//    @PreAuthorize("@ss.hasPermi('system:menu:list')")
    @GetMapping("/list")
    public AjaxResult list(SysMenu menu)
    {
        List<SysMenu> menus = menuService.selectMenuList(menu);
        return AjaxResult.success(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:menu:query')")
    @GetMapping(value = "/{menuId}")
    public AjaxResult getInfo(@PathVariable Long menuId)
    {
        return AjaxResult.success(menuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysMenu menu)
    {
        List<SysMenu> menus = menuService.selectMenuList(menu);
        return AjaxResult.success(menuService.buildMenuTreeSelect(menus));
    }



    /**
     * 新增菜单
     */
//    @PreAuthorize("@ss.hasPermi('system:menu:add')")
    @Log(title = "SaaS菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysMenu menu)
    {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu)))
        {
            return AjaxResult.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath()))
        {
            return AjaxResult.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateBy(getUsername());
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
//    @PreAuthorize("@ss.hasPermi('system:menu:edit')")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysMenu menu)
    {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu)))
        {
            return AjaxResult.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath()))
        {
            return AjaxResult.error("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        else if (menu.getMenuId().equals(menu.getParentId()))
        {
            return AjaxResult.error("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menu.setUpdateBy(getUsername());
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
//    @PreAuthorize("@ss.hasPermi('system:menu:remove')")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public AjaxResult remove(@PathVariable("menuId") Long menuId)
    {
        if (menuService.hasChildByMenuId(menuId))
        {
            return AjaxResult.error("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId))
        {
            return AjaxResult.error("菜单已分配,不允许删除");
        }
        return toAjax(menuService.deleteMenuById(menuId));
    }
}