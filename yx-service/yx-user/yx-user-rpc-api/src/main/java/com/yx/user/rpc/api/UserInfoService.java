package com.yx.user.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.user.model.RangeUserModel;
import com.yx.user.model.UserInfo;
import com.yx.user.model.referrer.UserReferrerModel;
import com.yx.user.model.vo.UserInfoExp;
import com.yx.user.model.vo.UserInfoVO;
import com.yx.user.model.vo.UserOpenAccountExp;

import java.util.List;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-19
 */
public interface UserInfoService extends BaseService<UserInfo> {


	/**
	 * 通过手机号码查询用户
	 * @param phone
	 * @return
	 */
	UserInfo selectByPhone(Long phone);

	/**
	 * 通过邮箱查询用户
	 * @param email
	 * @return
	 */
	UserInfo selectByEmail(String email);

	/**
	 * 根据主键查询扩展类*（从从库）
	 * @param id
	 * @return
	 */
	UserInfoVO selectVOById(Long id);

	/**
	 * 根据主键查询扩展类*（从主库）
	 * @param id
	 * @return
	 */
	UserInfoVO getVOById(Long id);

	/**
	 * 分页查询（单表数据）
	 * @param page
	 * @param userInfo
	 * @return
	 */
	Page<UserInfo> selectPage(Page<UserInfo> page, UserInfo userInfo);

	/**
	 * 分页查询（扩展）
	 * @param page
	 * @param userInfoVO
	 * @return
	 */
	Page<UserInfoVO> selectVOPage(Page<UserInfoVO> page, UserInfoVO userInfoVO);

	/**
	 * 重置密码
	 * @param id
	 * @return
	 */
	UserInfo resetPassWord(Long id);




	/**
	 * 根据手机号查询推荐人
	 * @param phone
	 * @return
	 */
	List<UserInfo> selectByTransactorPhone(Long phone);

	/**
	 * 根据id 查询申请人成功推荐人的id
	 * @param id
	 * @return
	 */
	List<Long> selectMyReferrerCountById(Long id);

	/**
	 * 根据推荐人集合查询有效投资数量
	 * @param recomList
	 * @return
	 */
	List<Long> selectEffective(List<Long> recomList);


	/**
	 * 实时获取平台注册人数
	 * @return
	 */
	int getCountRegister();

	List<UserInfoExp> selectExpList(UserInfoVO userInfo);

	/*List<UserInfoExp> selectBorrowerExpList(UserInfoVO userInfo);

	List<UserInfoExp> selectDepositaryExpList(UserInfoVO userInfo);*/

	/**
	 * 查询受托人列表
	 * @param
	 * @param userInfoVO
	 * @return
	 */
	Page<UserInfoVO> selectVOIsDepositaryPage(Page<UserInfoVO> page, UserInfoVO userInfoVO);

	/**
	 * 根据推荐方id 查询 担保方信息
	 * @param guaranteeId
	 * @return
	 */
	UserInfo selectByGuaranteeId(Long guaranteeId);

	/**
	 * 修改用户推荐人
	 * @param userInfoVO
	 */
	void updateUserReferee(UserInfoVO userInfoVO) throws Exception;

	int selectCountByIdnumber(String idNumber);

	int selectCountByAcno(String acno);

	int selectCountByCustNo(String custNo);

	/**
	 * 通过id查询用户手机号码
	 * @return
	 */
	Long selectPhoneById(Long userId);

	/**
	 * 解绑后更新用户信息
	 * @param userId
	 */
	void updateUserInfoForBandCard(Long userId);

	List<RangeUserModel> queryByPhones(List<String> phones);

	List<Long> getAllUserId();

	/**
	 * 开户绑卡--费用查询
	 * @param page
	 * @param userInfoVO
	 * @return
	 */
	Page<UserInfoVO> selectOpenAccountUserVOPage(Page<UserInfoVO> page,UserInfoVO userInfoVO);


	/**
	 * 查询开通存管账户的用户名单（不包含企业账户）--导出
	 * @param userInfoVO
	 * @return
	 */
	List<UserOpenAccountExp> selectOpenAccountUserExp(UserInfoVO userInfoVO);


	//--------------以下方法用于法大大---start---------------------
	/**
	 * 查询需要法大大自动注册的用户
	 * 条件：1、已开通存管；
	 *       2、个人出借用户
	 *
	 * @return
	 */
	List<UserInfo> selectFddAutoRegisterUser();

	/**
	 * 根据证件号码查询用户信息
	 * @param idNumber
	 * @return
	 */
	UserInfoVO selectUserByIdNumber(String idNumber);

	/**
	 * 根据借款标id查询借款人信息
	 * @param productId
	 * @return
	 */
	UserInfoVO selectVoByProductId(Long productId);


	//-------------以下方法用于法大大--------end----------

}
