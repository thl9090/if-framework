package com.yx.sys.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.sys.common.MessageCenterEnum;
import com.yx.sys.common.MessageCenterStatusEnum;
import com.yx.sys.dao.mapper.SysMessageCenterMapper;
import com.yx.sys.model.SysMessageCenter;
import com.yx.sys.model.vo.SysMessageCenterVO;
import com.yx.sys.rpc.api.SysMessageCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息中心表 服务实现类
 * </p>
 *
 * @author lilulu
 * @since 2018-08-15
 */
@Service("sysMessageCenterService")
public class SysMessageCenterServiceImpl extends BaseServiceImpl<SysMessageCenterMapper, SysMessageCenter> implements SysMessageCenterService {

    @Autowired
    private SysMessageCenterMapper sysMessageCenterMapper;

    @Override
    public SysMessageCenterVO selectVOById(Long id) {
        return sysMessageCenterMapper.selectVOById(id);
    }

    @Override
    public Page selectPageByEntity(Page<SysMessageCenter> page, SysMessageCenter sysMessageCenter) {
        List<SysMessageCenter> list = sysMessageCenterMapper.selectPageByEntity(page,sysMessageCenter);
        return page.setRecords(list);
    }

    /**
     * 公告列表
     * @param currentPage
     * @return
     */
    @Override
    public Page<SysMessageCenter> selectNoticeListPage(Integer currentPage) {
        Page<SysMessageCenter> page = new Page<>();
        if(currentPage != null){
            page.setCurrent(currentPage);
        }
        SysMessageCenter sysMessageCenter = new SysMessageCenter();
        //设置查询条件为公告列表
        sysMessageCenter.setType(MessageCenterEnum.NOTICE.getStatus());
        //有效
        sysMessageCenter.setStatus(MessageCenterStatusEnum.PUBLISH.getStatus());
        List<SysMessageCenter> sysMessageCenters = sysMessageCenterMapper.selectPageByEntity(page,sysMessageCenter);
        return page.setRecords(sysMessageCenters);
    }

    /**
     * 视频专区
     * @param currentPage
     * @return
     */
    @Override
    public Page<SysMessageCenter> selectVideoListPage(Integer currentPage) {
        Page<SysMessageCenter> page = new Page<>();
        if(currentPage != null){
            page.setCurrent(currentPage);
        }
        SysMessageCenter sysMessageCenter = new SysMessageCenter();
        //设置查询条件为视频专区
        sysMessageCenter.setType(MessageCenterEnum.VIDEO.getStatus());
        //有效
        sysMessageCenter.setStatus(MessageCenterStatusEnum.PUBLISH.getStatus());
        List<SysMessageCenter> sysMessageCenters = sysMessageCenterMapper.selectPageByEntity(page,sysMessageCenter);
        return page.setRecords(sysMessageCenters);
    }

    /**
     * 行业质询
     * @param currentPage
     * @return
     */
    @Override
    public Page<SysMessageCenter> selectIndustryInquiryListPage(Integer currentPage) {
        Page<SysMessageCenter> page = new Page<>();
        if(currentPage != null){
            page.setCurrent(currentPage);
        }
        SysMessageCenter sysMessageCenter = new SysMessageCenter();
        //设置查询条件为行业质询
        sysMessageCenter.setType(MessageCenterEnum.INDUSTRY_INQUIRY.getStatus());
        //有效
        sysMessageCenter.setStatus(MessageCenterStatusEnum.PUBLISH.getStatus());
        List<SysMessageCenter> sysMessageCenters = sysMessageCenterMapper.selectPageByEntity(page,sysMessageCenter);
        return page.setRecords(sysMessageCenters);
    }

    /**
     * 媒体报道
     * @param currentPage
     * @return
     */
    @Override
    public Page<SysMessageCenter> selectMediaListPage(Integer currentPage) {
        Page<SysMessageCenter> page = new Page<>();
        if(currentPage != null){
            page.setCurrent(currentPage);
        }
        SysMessageCenter sysMessageCenter = new SysMessageCenter();
        //设置查询条件为媒体报道
        sysMessageCenter.setType(MessageCenterEnum.MEDIA.getStatus());
        //有效
        sysMessageCenter.setStatus(MessageCenterStatusEnum.PUBLISH.getStatus());
        List<SysMessageCenter> sysMessageCenters = sysMessageCenterMapper.selectPageByEntity(page,sysMessageCenter);
        return page.setRecords(sysMessageCenters);
    }

    /**
     * 存管动态
     * @param currentPage
     * @return
     */
    @Override
    public Page<SysMessageCenter> selectDepositListPage(Integer currentPage) {
        Page<SysMessageCenter> page = new Page<>();
        if(currentPage != null){
            page.setCurrent(currentPage);
        }
        SysMessageCenter sysMessageCenter = new SysMessageCenter();
        //设置查询条件为存管动态
        sysMessageCenter.setType(MessageCenterEnum.DEPOSIT.getStatus());
        //有效
        sysMessageCenter.setStatus(MessageCenterStatusEnum.PUBLISH.getStatus());
        List<SysMessageCenter> sysMessageCenters = sysMessageCenterMapper.selectPageByEntity(page,sysMessageCenter);
        return page.setRecords(sysMessageCenters);
    }

    /**
     * 网贷知识
     * @param currentPage
     * @return
     */
    @Override
    public Page<SysMessageCenter> selectIntellectualListPage(Integer currentPage) {
        Page<SysMessageCenter> page = new Page<>();
        if(currentPage != null){
            page.setCurrent(currentPage);
        }
        SysMessageCenter sysMessageCenter = new SysMessageCenter();
        //设置查询条件为网贷知识
        sysMessageCenter.setType(MessageCenterEnum.P2P_INTELLECTUAL.getStatus());
        //有效
        sysMessageCenter.setStatus(MessageCenterStatusEnum.PUBLISH.getStatus());
        List<SysMessageCenter> sysMessageCenters = sysMessageCenterMapper.selectPageByEntity(page,sysMessageCenter);
        return page.setRecords(sysMessageCenters);
    }

    /**
     * 政策法规
     * @param currentPage
     * @return
     */
    @Override
    public Page<SysMessageCenter> selectPolicieRegulationsListPage(Integer currentPage) {
        Page<SysMessageCenter> page = new Page<>();
        if(currentPage != null){
            page.setCurrent(currentPage);
        }
        SysMessageCenter sysMessageCenter = new SysMessageCenter();
        //设置查询条件为政策法规
        sysMessageCenter.setType(MessageCenterEnum.POLICIE_REGULATIONS.getStatus());
        //有效
        sysMessageCenter.setStatus(MessageCenterStatusEnum.PUBLISH.getStatus());
        List<SysMessageCenter> sysMessageCenters = sysMessageCenterMapper.selectPageByEntity(page,sysMessageCenter);
        return page.setRecords(sysMessageCenters);
    }

    @Override
    public List<SysMessageCenter> selectList(SysMessageCenter messageCenter) {
        return sysMessageCenterMapper.selectList(messageCenter);
    }
	
	@Override
	public List<SysMessageCenter> selectAuthorityInformation() {
		SysMessageCenter sysMessageCenter = new SysMessageCenter();
		//设置查询条件为行业资讯
		sysMessageCenter.setType(MessageCenterEnum.INDUSTRY_INQUIRY.getStatus());
		//有效
		sysMessageCenter.setStatus(MessageCenterStatusEnum.PUBLISH.getStatus());
		List<SysMessageCenter> sysMessageCenters = sysMessageCenterMapper.selectAuthorityInformation(sysMessageCenter);
		return sysMessageCenters;
	}
	
}
