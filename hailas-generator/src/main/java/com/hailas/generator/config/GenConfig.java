package com.hailas.generator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取代码生成相关配置
 * 
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "gen")
@PropertySource(value = { "classpath:generator-${spring.profiles.active}.yml" })
public class GenConfig
{
    /** 作者 */
    public static String author;

    /** 生成路径 */
    public static String genPath;

    /** 子系统生成路径 */
    public static String genCompanyPath;

    /** 子系统生成路径 */
    public static String genDomainPath;

    /** 生成包路径 */
    public static String packageName;

    /** 自动去除表前缀，默认是false */
    public static boolean autoRemovePre;

    /** 表前缀(类名不会包含表前缀) */
    public static String tablePrefix;

    public static String getAuthor()
    {
        return author;
    }

    @Value("${author}")
    public void setAuthor(String author)
    {
        GenConfig.author = author;
    }

    public static String getPackageName()
    {
        return packageName;
    }

    public static String getGenPath() {
        return genPath;
    }

    @Value("${genPath}")
    public  void setGenPath(String genPath) {
        GenConfig.genPath = genPath;
    }

    public static String getGenCompanyPath() {
        return genCompanyPath;
    }

    @Value("${genCompanyPath}")
    public void setGenCompanyPath(String genCompanyPath) {
        GenConfig.genCompanyPath = genCompanyPath;
    }

    public static String getGenDomainPath() {
        return genDomainPath;
    }

    @Value("${genDomainPath}")
    public void setGenDomainPath(String genDomainPath) {
        GenConfig.genDomainPath = genDomainPath;
    }

    @Value("${packageName}")
    public void setPackageName(String packageName)
    {
        GenConfig.packageName = packageName;
    }

    public static boolean getAutoRemovePre()
    {
        return autoRemovePre;
    }

    @Value("${autoRemovePre}")
    public void setAutoRemovePre(boolean autoRemovePre)
    {
        GenConfig.autoRemovePre = autoRemovePre;
    }

    public static String getTablePrefix()
    {
        return tablePrefix;
    }

    @Value("${tablePrefix}")
    public void setTablePrefix(String tablePrefix)
    {
        GenConfig.tablePrefix = tablePrefix;
    }
}
