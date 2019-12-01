package com.yx.user.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.pool.ThreadExecutor;
import com.yx.common.utils.DateUtils;
import com.yx.user.dao.mapper.UserLoginLogMapper;
import com.yx.user.model.ClientInfo;
import com.yx.user.model.UserLoginLog;
import com.yx.user.rpc.api.UserLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.yx.common.utils.DateUtils.MONTH_PARSE;

@Slf4j
@Service("userLoginLogService")
public class UserLoginLogServiceImpl implements UserLoginLogService {

    @Autowired
    private UserLoginLogMapper userLoginLogMapper;


    @Override
    public int insertSelective(UserLoginLog userLoginLog) {
        userLoginLog.setTableName("user_login_log_" + DateUtils.getDate(MONTH_PARSE));
        return userLoginLogMapper.insertSelective(userLoginLog);
    }

    @Override
    public void addLoginLog(Long userId, ClientInfo clientInfo) {
        if(userId!=null&&clientInfo!=null){
            try{
                UserLoginLog userLoginLog=new UserLoginLog();
                userLoginLog.setTableName("user_login_log_" + DateUtils.getDate(MONTH_PARSE));
                userLoginLog.setUserId(userId);
                userLoginLog.setChannelType(clientInfo.getChannelType());
                userLoginLog.setChannelVersion(clientInfo.getChannelVersion());
                userLoginLog.setIp(clientInfo.getIp());
                userLoginLog.setDerviceName(clientInfo.getDerviceName());
                userLoginLog.setLoginTime(new Date());
                userLoginLogMapper.insertSelective(userLoginLog);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public Page<UserLoginLog> selectPage(Page<UserLoginLog> page, UserLoginLog userLoginLog) {
        userLoginLog.setTableName("user_login_log_" + DateUtils.getDate(MONTH_PARSE));
        List<UserLoginLog> list = userLoginLogMapper.selectPage(page, userLoginLog);
        page.setRecords(list);
        return page;
    }

    @Override
    public UserLoginLog selectLateLogByUserId(Long userId) {
        String tableName="user_login_log_" + DateUtils.getDate(MONTH_PARSE);
        return userLoginLogMapper.selectLateLogByUserId(userId,tableName);
    }


}
