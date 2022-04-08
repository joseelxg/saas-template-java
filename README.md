
# SaaS管理平台

## 平台简介

该平台基于若依前后端分离版本的二次开发，赋予其SaaS能力。


客户子系统有独立的账户、角色等用户系统功能。

## 新增功能

1.  公司管理：用于创建子客户系统及分配管理员账号，可修改子系统的平台名称。
2.  公司菜单管理：可编辑各个客户公司的菜单权限以及菜单名称修改。
3.  子系统菜单管理： 可全局配置客户系统菜单，操作权限，按钮权限标识等。

#### 并修改了原若依平台的一些功能内容包括但不限于：
1.  代码生成模板修改
2.  字典管理：改为多级树状结构；并增加内容字段可支持简单文本及富文本

## SaaS化开发说明

1.   业务表中增加company_id 字段：

```
  company_id int NOT NULL COMMENT '企业id',
```

2. 添加`@DataCompany`注解：

```
   @Override
   @DataCompany
   public List<CompanyDevice> selectCompanyDeviceList(CompanyDevice companyDevice)
   {
        return companyDeviceMapper.selectCompanyDeviceList(companyDevice);
   }   
```

3. sql中增加`company_id`的筛选条件：
```
    <select id="selectCompanyDeviceList" parameterType="CompanyDevice" resultMap="CompanyDeviceResult">
        select <include refid="selectCompanyDeviceVo"/>,
        cc.name companyName
        from com_device  cd
        left join com_company cc on cd.company_id=cc.id
        <where>
            cd.deleted=0
            <if test="name != null  and name != ''"> and cd.name like concat('%', #{name}, '%')</if>
            and cd.company_id=#{companyId}
        </where>
    </select>
```

#### 后续将完善子系统的代码生成功能，自动加入以上信息


#若依管理平台
## 平台简介

若依是一套全部开源的快速开发平台，毫无保留给个人及企业免费使用。

* 前端采用Vue、Element UI。
* 后端采用Spring Boot、Spring Security、Redis & Jwt。
* 权限认证使用Jwt，支持多终端认证系统。
* 支持加载动态权限菜单，多方式轻松权限控制。
* 高效率开发，使用代码生成器可以一键生成前后端代码。

## 内置功能

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  通知公告：系统通知公告信息发布维护。
9.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14. 系统接口：根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 缓存监控：对系统的缓存信息查询，命令统计等。
17. 在线构建器：拖动表单元素生成相应的HTML代码。
18. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。



