package com.yx.business.rpc.api;

import com.yx.user.model.ClientInfo;
import com.yx.user.model.UserInfo;
import com.yx.user.model.vo.UserInfoVO;

public interface UserBusinessService {


    /**
     * 用户注册
     *
     * @param model
     * @return
     * @throws Exception
     */
    boolean register(UserInfoVO model) throws Exception;

    /**
     * 企业注册
     *
     * @param model
     * @return
     * @throws Exception
     */
    boolean companyRegister(UserInfoVO model) throws Exception;


    /**
     * 用户登陆
     *
     * @param model
     * @return
     * @throws Exception
     */
    String login(UserInfo model, ClientInfo clientInfo) throws Exception;

    /**
     * 退出登录
     *
     * @param accessToken
     * @return
     * @throws Exception
     */
    void logout(String accessToken);

    /**
     * 修改登陆密码(登陆后修改)
     *
     * @param model
     * @throws Exception
     */
    void updatePassword(UserInfoVO model) throws Exception;

    /**
     * 重置密码（登录前修改）
     *
     * @param model
     * @throws Exception
     */
    void resetPassword(UserInfoVO model) throws Exception;

    /**
     * 修改手机号码
     *
     * @param model
     * @throws Exception
     */
    void updatePhone(UserInfoVO model) throws Exception;

    /**
     * 修改用户名
     *
     * @param model
     * @throws Exception
     */
    void updateUserName(UserInfoVO model) throws Exception;

    /**
     * 修改用户名--pc端专用
     *
     * @param model
     * @throws Exception
     */
    void updateUserNameForPC(UserInfoVO model) throws Exception;

    /**
     * 修改邮箱
     *
     * @param model
     * @throws Exception
     */
    void updateEmail(UserInfo model) throws Exception;



}
