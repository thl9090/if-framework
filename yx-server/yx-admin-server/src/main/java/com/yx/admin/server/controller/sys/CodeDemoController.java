package com.yx.admin.server.controller.sys;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.core.model.PageModel;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.model.CodeDemo;
import com.yx.sys.rpc.api.CodeDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * codeDemo前端控制器
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-13
 */
@Api(value = "代码示例", description = "代码示例")
@RestController
@RequestMapping("/codeDemo")
public class CodeDemoController extends BaseController {
    @Autowired
    private CodeDemoService service;

    /**
    * 根据ID查询
    *
    * @param id ID
    * @return ResultModel
    * @author TangHuaLiang
    * @date 2018-07-13
    */
    @ApiOperation(value = "查询对象", notes = "根据主键ID查询对象")
    @GetMapping("/select/{id}")
    public ResultModel selectById(@PathVariable Long id) {
            Assert.notNull(id);
            CodeDemo entity = service.selectById(id);
            return ResultUtil.ok(entity);
    }

    /**
    * 查询分页方法
    *
    * @param pageModel 分页实体
    * @return com.yx.common.web.model.ResultModel
    * @author TangHuaLiang
    * @date 2018-07-13
    */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/selectPage")
    public ResultModel queryListPage(@RequestBody PageModel pageModel) {
        CodeDemo codeDemo=new CodeDemo();
        Page page = service.selectPage(pageModel, codeDemo);
        return ResultUtil.ok(page);
    }

    /**
     * 新增方法
     *
     * @param entity 实体
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 2018-07-13
     */
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    public ResultModel add(@Valid @RequestBody CodeDemo entity) throws Exception{
        if (entity != null) {
            entity.setCreateBy(this.getCurrentUserId());
            entity.setUpdateBy(this.getCurrentUserId());
        }
        return ResultUtil.ok(service.add(entity));
    }

    /**
     * 修改方法
     *
     * @param entity 实体
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 2018-07-13
     */
    @ApiOperation(value = "更新", notes = "更新")
    @PutMapping("/update")
    public ResultModel update(@RequestBody CodeDemo entity) {
        entity.setUpdateBy(this.getCurrentUserId());
        entity.setUpdateTime(new Date());
        service.updateById(entity);
        return ResultUtil.ok();
    }

    /**
     * 批量删除方法
     *
     * @param ids ID集合
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 2018-07-13
     */
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/delete")
    public ResultModel delete(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        return ResultUtil.ok(service.deleteBatchIds(Arrays.asList(ids)));
    }



    private  void test(){

        CodeDemo codeDemo=new CodeDemo();
        List<CodeDemo> list=new ArrayList<CodeDemo>();
        List<Long> idList=new ArrayList<Long>();
        //以下方法系统已经集成，不用编写
        //添加
        try {
            service.add(codeDemo);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        //插入
        service.insert(codeDemo);
        //批量插入
        service.insertBatch(list);

        //更新
        service.updateById(codeDemo);

        //删除
        service.deleteById(1L);

        //批量删除
        service.deleteBatchIds(idList);



    }


    //=========================以下用于数据清洗=====================================
    @GetMapping("/dealWithProduct")
    public ResultModel dealWithProduct() throws Exception {

        service.dealWith();

        return ResultUtil.ok();
    }



}

