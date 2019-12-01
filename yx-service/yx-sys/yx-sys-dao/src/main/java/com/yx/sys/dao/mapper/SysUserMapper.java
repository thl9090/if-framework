package com.yx.sys.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.SysUser;
import com.yx.sys.model.vo.SysUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 Mapper 接口
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据关键字分页查询
     *
     * @param page      分页对象
     * @param searchKey 关键字
     * @return List<SysUserModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    //List<SysUser> selectPage(Page<SysUser> page, @Param("searchKey") String searchKey);

	/**
	 * 分页查询（基础数据）
	 * @param page
	 * @param sysUser
	 * @return
	 */
    List<SysUser> selectPage(Page<SysUser> page, @Param("sysUser") SysUser sysUser);

    /**
     * 分页查询（扩展类）
     * @param page
     * @param sysUserVO
     * @return
     */
    List<SysUserVO> selectVOPage(Page<SysUserVO> page, @Param("sysUserVO") SysUserVO sysUserVO);


   /**
    * 根据用户ID查询（基础数据）
    * @param id
    * @return
    */
    SysUser selectById(@Param("id") Long id);

    /**
     * 根据用户ID查询（扩展数据）
     * @param id
     * @return
     */
    SysUserVO selectVOById(@Param("id") Long id);

}
