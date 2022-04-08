package com.hailas;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class HailasCompanyApplication
{
    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {

        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(HailasCompanyApplication.class, args);
    }
}
