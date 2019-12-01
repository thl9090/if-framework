package com.yx.business.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.business.dao.mapper.CodeDemoMapper;
import com.yx.business.model.CodeDemo;
import com.yx.business.model.vo.CodeDemoVO;
import com.yx.business.rpc.api.CodeDemoService;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.sys.model.ProductData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  demo服务实现类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-13
 */
@Slf4j
@Service("codeDemoService")
public class CodeDemoServiceImpl extends BaseServiceImpl<CodeDemoMapper, CodeDemo> implements CodeDemoService {

    @Autowired
    private CodeDemoMapper codeDemoMapper;

    private void test(){
        //以下方法已经封装好，直接调用，不需要自己写
        CodeDemo codeDemo=new CodeDemo();
        List<Long> idList=new ArrayList<>();

        //插入
        codeDemoMapper.insert(codeDemo);


        //更新
        codeDemoMapper.updateById(codeDemo);
       //删除
        codeDemoMapper.deleteById(1L);
        //批量删除
        codeDemoMapper.deleteBatchIds(idList);
        //根据id查询查询
        codeDemoMapper.selectById(1L);


    }


    @Override
    public CodeDemo getById(Long id) {
        return codeDemoMapper.getById(id);
    }

    @Override
    public CodeDemoVO selectVOById(Long id) {
        return codeDemoMapper.selectVOById(id);
    }

    @Override
    public CodeDemoVO getVOById(Long id) {
        return codeDemoMapper.getVOById(id);
    }

    @Override
    public Page<CodeDemo> selectPage(Page<CodeDemo> page, CodeDemo codeDemo) {
        List<CodeDemo> list = codeDemoMapper.selectPage(page, codeDemo);
        page.setRecords(list);
        return page;
    }

    @Override
    public Page<CodeDemoVO> selectVOPage(Page<CodeDemoVO> page, CodeDemoVO codeDemoVO) {
        List<CodeDemoVO> list = codeDemoMapper.selectVOPage(page, codeDemoVO);
        page.setRecords(list);
        return page;
    }


    @Override
    public Integer selectCountByTitle(String title) {
        return codeDemoMapper.selectCountByTitle(title);
    }

    @Override
    public int updateProductTitle(ProductData data) {
        return codeDemoMapper.updateProductTitle(data);
    }

    @Override
    public void dealWith() throws Exception {
        System.out.println("==========处理开始==================");

        System.out.println("==========处理结束==================:");
    }
}
