package com.yx.user.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.utils.DateUtils;
import com.yx.user.dao.mapper.UserInfoMapper;
import com.yx.user.model.RangeUserModel;
import com.yx.user.model.UserInfo;
import com.yx.user.model.vo.UserInfoExp;
import com.yx.user.model.vo.UserInfoVO;
import com.yx.user.model.vo.UserOpenAccountExp;
import com.yx.user.rpc.api.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-19
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {


	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public UserInfo selectByPhone(Long phone) {
		return userInfoMapper.selectByPhone(phone);
	}

	@Override
	public UserInfo selectByEmail(String email) {
		return userInfoMapper.selectByEmail(email);
	}


	@Override
	public UserInfoVO selectVOById(Long id) {
		return userInfoMapper.selectVOById(id);
	}

	@Override
	public UserInfoVO getVOById(Long id) {
		return userInfoMapper.getVOById(id);
	}

	@Override
	public Page<UserInfo> selectPage(Page<UserInfo> page, UserInfo userInfo) {

		List<UserInfo> list = userInfoMapper.selectPage(page, userInfo);
		page.setRecords(list);
		return page;
	}

	@Override
	public Page<UserInfoVO> selectVOPage(Page<UserInfoVO> page, UserInfoVO userInfoVO) {
		List<UserInfoVO> list = userInfoMapper.selectVOPage(page, userInfoVO);
		page.setRecords(list);
		return page;
	}

	@Override
	public UserInfo resetPassWord(Long id) {
		UserInfo entity = new UserInfo();
		entity.setId(id);
		UserInfo userInfo = userInfoMapper.selectById(entity);
		return userInfo;
	}


	@Override
	public List<UserInfo> selectByTransactorPhone(Long phone) {
		return userInfoMapper.selectByTransactorPhone(phone);
	}

	@Override
	public List<Long> selectMyReferrerCountById(Long id) {
		return userInfoMapper.selectMyReferrerCountById(id);
	}

	@Override
	public List<Long> selectEffective(List<Long> recomList) {
		return userInfoMapper.selectEffective(recomList);
	}

	@Override
	public int getCountRegister() {
		return userInfoMapper.selectCountRegister();
	}

	@Override
	public List<UserInfoExp> selectExpList(UserInfoVO userInfo) {
		if(userInfo.getCreateTimeStart()!=null){
			String dateStart = DateUtils.formatDate(userInfo.getCreateTimeStart());
			dateStart=dateStart+" 00:00:00";
			userInfo.setCreateTimeStart(DateUtils.parseDate(dateStart));
		}
		if(userInfo.getCreateTimeEnd()!=null){
			String dateEnd = DateUtils.formatDate(userInfo.getCreateTimeEnd());
			dateEnd=dateEnd+" 23:59:59";
			userInfo.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
		}
		return this.userInfoMapper.selectExpList(userInfo);
	}

	/*@Override
	public List<UserInfoExp> selectBorrowerExpList(UserInfoVO userInfo) {
		if(userInfo.getCreateTimeStart()!=null){
			String dateStart = DateUtils.formatDate(userInfo.getCreateTimeStart());
			dateStart=dateStart+" 00:00:00";
			userInfo.setCreateTimeStart(DateUtils.parseDate(dateStart));
		}
		if(userInfo.getCreateTimeEnd()!=null){
			String dateEnd = DateUtils.formatDate(userInfo.getCreateTimeEnd());
			dateEnd=dateEnd+" 23:59:59";
			userInfo.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
		}
		userInfo.setIsBorrower(IsBorrowerEnum.YES.getStatus());
		return this.userInfoMapper.selectExpList(userInfo);
	}*/

	/*@Override
	public List<UserInfoExp> selectDepositaryExpList(UserInfoVO userInfo) {
		if(userInfo.getCreateTimeStart()!=null){
			String dateStart = DateUtils.formatDate(userInfo.getCreateTimeStart());
			dateStart=dateStart+" 00:00:00";
			userInfo.setCreateTimeStart(DateUtils.parseDate(dateStart));
		}
		if(userInfo.getCreateTimeEnd()!=null){
			String dateEnd = DateUtils.formatDate(userInfo.getCreateTimeEnd());
			dateEnd=dateEnd+" 23:59:59";
			userInfo.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
		}
		userInfo.setIsDepositary(IsBorrowerEnum.YES.getStatus());
		return this.userInfoMapper.selectExpList(userInfo);
	}*/

	@Override
	public Page selectVOIsDepositaryPage(Page<UserInfoVO> page, UserInfoVO userInfoVO) {
		List<UserInfoVO> list = userInfoMapper.selectVOIsDepositaryPage(page, userInfoVO);
		page.setRecords(list);
		return page;
	}

	@Override
	public UserInfo selectByGuaranteeId(Long guaranteeId) {
		return userInfoMapper.selectByGuaranteeId(guaranteeId);
	}

	/**
	 * 修改用户推荐人
	 * @param userInfoVO
	 */
	@Override
	public void updateUserReferee(UserInfoVO userInfoVO) throws BusinessException {

		if(userInfoVO == null){
			throw new BusinessException("请求参数不能为空");
		}
		if(userInfoVO.getId() == null){
			throw new BusinessException("用户id不能为空");
		}
		if(userInfoVO.getRefPhone() == null){
			throw new BusinessException("推荐人手机号码不能为空");
		}

		UserInfoVO user = userInfoMapper.selectVOById(userInfoVO.getId());
		if(user == null){
			throw new BusinessException("该用户不存在");
		}
		if(user.getStatus() == 0){
			throw new BusinessException("该用户已被禁用，不能修改");
		}
		UserInfo  refUser = userInfoMapper.selectByPhone(userInfoVO.getRefPhone());
		if(refUser == null || refUser.getPhone() == null){
			throw new BusinessException("您输入的手机号码有误");
		}
		if(refUser.getId().longValue()== userInfoVO.getId().longValue()){
			throw new BusinessException("用户不能将自己设为推荐人");
		}
		userInfoMapper.updateUserReferee(userInfoVO);
	}

	@Override
	public int selectCountByIdnumber(String idNumber) {
		return userInfoMapper.selectCountByIdnumber(idNumber);
	}

	@Override
	public int selectCountByAcno(String acno) {
		return userInfoMapper.selectCountByAcno(acno);
	}

	@Override
	public int selectCountByCustNo(String custNo) {
		return userInfoMapper.selectCountByCustNo(custNo);
	}

	@Override
	public Long selectPhoneById(Long userId) {
		return userInfoMapper.selectPhoneById(userId);
	}

	@Override
	public void updateUserInfoForBandCard(Long userId) {
		userInfoMapper.updateUserInfoForBandCard(userId);
	}

	@Override
	public List<RangeUserModel> queryByPhones(List<String> phones) {
		return userInfoMapper.selectByPhones(phones);
	}

	@Override
	public List<Long> getAllUserId() {
		return userInfoMapper.selectAllUserId();
	}

	@Override
	public Page<UserInfoVO> selectOpenAccountUserVOPage(Page<UserInfoVO> page, UserInfoVO userInfoVO) {
		List<UserInfoVO> list = userInfoMapper.selectOpenAccountUserVOPage(page, userInfoVO);
		page.setRecords(list);
		return page;
	}

	@Override
	public List<UserOpenAccountExp> selectOpenAccountUserExp(UserInfoVO userInfoVO) {
		return userInfoMapper.selectOpenAccountUserExp(userInfoVO);
	}


	//--------------以下方法用于法大大---start---------------------

	@Override
	public List<UserInfo> selectFddAutoRegisterUser() {
		return userInfoMapper.selectFddAutoRegisterUser();
	}

	@Override
	public UserInfoVO selectUserByIdNumber(String idNumber) {
		return userInfoMapper.selectUserByIdNumber(idNumber);
	}

	@Override
	public UserInfoVO selectVoByProductId(Long productId) {
		return userInfoMapper.selectVoByProductId(productId);
	}

	//-------------以下方法用于法大大--------end----------

}
