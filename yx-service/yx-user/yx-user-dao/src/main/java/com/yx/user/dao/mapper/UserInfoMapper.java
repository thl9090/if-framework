package com.yx.user.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseMapper;
import com.yx.user.model.RangeUserModel;
import com.yx.user.model.UserInfo;
import com.yx.user.model.referrer.UserReferrerModel;
import com.yx.user.model.vo.UserInfoExp;
import com.yx.user.model.vo.UserInfoVO;
import com.yx.user.model.vo.UserOpenAccountExp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-19
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 根据主键查询（从主库）
     * @param id
     * @return
     */
    UserInfo getById(@Param("id")Long id);

    /**
     * 通过手机号码查询用户
     * @param phone
     * @return
     */
    UserInfo selectByPhone(@Param("phone")Long phone);

    /**
     * 通过邮箱查询用户
     * @param email
     * @return
     */
    UserInfo selectByEmail(@Param("email")String email);


    /**
     * 根据主键查询扩展类*（从从库）
     * @param id
     * @return
     */
    UserInfoVO selectVOById(@Param("id")Long id);

    /**
     * 根据主键查询扩展类*（从主库）
     * @param id
     * @return
     */
    UserInfoVO getVOById(@Param("id")Long id);

    /**
     * 分页查询（单表数据）
     * @param page
     * @param userInfo
     * @return
     */
    List<UserInfo> selectPage(Page<UserInfo> page,@Param("userInfo") UserInfo userInfo);

    /**
     * 分页查询（扩展）
     * @param page
     * @param userInfoVO
     * @return
     */
    List<UserInfoVO> selectVOPage(Page<UserInfoVO> page,@Param("userInfoVO")  UserInfoVO userInfoVO);



    /**
     * 获取推荐借款人集合
     * @param userList
     * @return
     */
    List<Long> selectAllReferrerBorrow(@Param("userList")List<Long> userList);

    /**
     * 查询借款人列表
     * @param page
     * @param model
     * @return
     */
    List<UserReferrerModel> selectMyReferrerBorrow(@Param("page")Page page, @Param("model")UserReferrerModel model);


    /**
     * 查询单个借款信息
     * @param page
     * @param model
     * @return
     */
    List<UserReferrerModel> selectMyReferrerBorrowOne(@Param("page")Page page, @Param("model")UserReferrerModel model);

    /**
     * 根据手机号查询推荐人
     * @param phone
     * @return
     */
    List<UserInfo> selectByTransactorPhone(@Param("phone") Long phone);

    /**
     * 根据id 查询申请人成功推荐人的id
     * @param id
     * @return
     */
    List<Long> selectMyReferrerCountById(@Param("id")Long id);

    /**
     * 根据推荐人集合查询有效投资数量
     * @param recomList
     * @return
     */
    List<Long> selectEffective(@Param("recomList")List<Long> recomList);

    /**
     * 实时获取平台注册人数
     * @return
     */
    int selectCountRegister();

    List<UserInfoExp> selectExpList(@Param("userInfo") UserInfo userInfo);

    /**
     *
     * @param page
     * @param userInfoVO
     * @return
     */
    List<UserInfoVO> selectVOIsDepositaryPage(Page<UserInfoVO> page, @Param("userInfoVO") UserInfoVO userInfoVO);

    /**
     * 根据标的信息中的推荐方 id 查询 担保方用户信息
     * @param guaranteeId
     * @return
     */
    UserInfo selectByGuaranteeId(@Param("guaranteeId") Long guaranteeId);


    /**
     * 修改用户推荐人
     * @param userInfoVO
     */
    int updateUserReferee(@Param("userInfoVO") UserInfoVO userInfoVO);

    int selectCountByIdnumber(@Param("idNumber") String idNumber);

    int selectCountByAcno(@Param("acno") String acno);

    int selectCountByCustNo(@Param("custNo") String custNo);


    Long selectPhoneById(@Param("userId")Long userId);

    /**
     * 解绑成功后调用改方法将用户绑定的银行卡信息置为null
     * @return
     */
    int updateUserInfoForBandCard(@Param("userId")Long userId);

    List<RangeUserModel> selectByPhones(@Param("phones") List<String> phones);

    List<Long> selectAllUserId();

    /**
     * 查询开通存管账户的用户名单（不包含企业账户）
     * @param page
     * @param userInfoVO
     * @return
     */
    List<UserInfoVO> selectOpenAccountUserVOPage(Page<UserInfoVO> page,@Param("userInfoVO")  UserInfoVO userInfoVO);

    /**
     * 查询开通存管账户的用户名单（不包含企业账户）--导出
     * @param userInfoVO
     * @return
     */
    List<UserOpenAccountExp> selectOpenAccountUserExp(@Param("userInfoVO")  UserInfoVO userInfoVO);


    //-----------------------------法大大相关方法----------------start-------------
    /**
     * 查询需要法大大自动注册存证的用户
     * 条件：1、已开通存管；
     *
     * @return
     */
    List<UserInfo> selectFddAutoRegisterUser();


    UserInfoVO selectUserByIdNumber(@Param("idNumber") String idNumber);

    UserInfoVO selectVoByProductId(@Param("productId")Long productId);


    //-----------------------------法大大相关方法----------------end-------------






}
