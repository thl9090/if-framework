package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.sys.model.SysMessageCenter;
import com.yx.sys.model.vo.SysMessageCenterVO;

import java.util.List;

/**
 * <p>
 * 消息中心表 服务类
 * </p>
 *
 * @author lilulu
 * @since 2018-08-15
 */
public interface SysMessageCenterService extends BaseService<SysMessageCenter> {

    SysMessageCenterVO selectVOById(Long id);

    Page selectPageByEntity(Page<SysMessageCenter> page, SysMessageCenter sysMessageCenter);

    /**
     * 公告列表
     * @param currentPage
     * @return
     */
    Page<SysMessageCenter> selectNoticeListPage(Integer currentPage);

    /**
     * 视频专区
     * @param currentPage
     * @return
     */
    Page<SysMessageCenter> selectVideoListPage(Integer currentPage);

    /**
     * 行业质询
     * @param currentPage
     * @return
     */
    Page<SysMessageCenter> selectIndustryInquiryListPage(Integer currentPage);

    /**
     * 媒体报道
     * @param currentPage
     * @return
     */
    Page<SysMessageCenter> selectMediaListPage(Integer currentPage);

    /**
     * 存管动态
     * @param currentPage
     * @return
     */
    Page<SysMessageCenter> selectDepositListPage(Integer currentPage);

    /**
     * 网贷知识
     * @param currentPage
     * @return
     */
    Page<SysMessageCenter> selectIntellectualListPage(Integer currentPage);

    /**
     * 政策法规
     * @param currentPage
     * @return
     */
    Page<SysMessageCenter> selectPolicieRegulationsListPage(Integer currentPage);


    List<SysMessageCenter> selectList(SysMessageCenter messageCenter);
	
	List<SysMessageCenter> selectAuthorityInformation();
	
}
