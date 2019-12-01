package com.yx.job.rpc.service.task;

import com.yx.common.utils.DateUtils;
import com.yx.job.rpc.service.components.CreateTableComponents;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户登陆日志建表定时
 *
 * @author TangHuaLiang
 * @since 2018/12/29
 */
@Slf4j
@Component("userLoginLogCreateTableTask")
public class UserLoginLogCreateTableTask {

    @Autowired
    private CreateTableComponents createTableComponents;

    public void execute(String suffix) {
        log.info("创建用户登陆日志表开始");
        if (StringUtils.isBlank(suffix)) {
            suffix = DateUtils.formatDate(DateUtils.addMonths(new Date(), 1), DateUtils.MONTH_PARSE);
        }
        try {
            createTableComponents.createUserLoginLogTable(suffix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("创建用户登陆日志表结束");
    }

    public void execute() {
        log.info("创建用户登陆日志表开始");
        String suffix = DateUtils.formatDate(DateUtils.addMonths(new Date(), 1), DateUtils.MONTH_PARSE);
        try {
            createTableComponents.createBankETKLogTable(suffix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("创建用户登陆日志表结束");
    }

}
