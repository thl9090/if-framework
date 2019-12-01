package com.yx.admin.server.controller.product;


import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.admin.server.annotation.SysLogOpt;
import com.yx.business.rpc.api.ProductBusinessService;
import com.yx.common.core.Constants;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.core.model.PageModel;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.utils.DateUtils;
import com.yx.common.utils.StringUtils;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.common.*;
import com.yx.sys.model.product.Product;
import com.yx.sys.model.product.ProductCheck;
import com.yx.sys.model.product.ProductFile;
import com.yx.sys.model.vo.ProductCheckVO;
import com.yx.sys.model.vo.ProductVO;
import com.yx.sys.rpc.api.ProductCheckService;
import com.yx.sys.rpc.api.ProductFileService;
import com.yx.sys.rpc.api.ProductService;
import com.yx.sys.rpc.api.SysParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的信息表前端控制器
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-30
 */
@RestController
@RequestMapping("/product")
@Api(value = "标的接口", description = "标的接口")
public class ProductController extends BaseController {

    @Autowired
    private ProductService service;
    @Autowired
    private ProductFileService productFileService;
    @Autowired
    private ProductCheckService productCheckService;
    @Autowired
    private ProductBusinessService productBusinessService;
//    @Autowired
//    private LoanService loanService;
    @Autowired
    private SysParamService sysParamService;
//    @Autowired
//    private BidService bidService;

    private static String RESOURCE_SERVER_URL="RESOURCE_SERVER_URL";


    /**
     * 根据标的信息表ID查询
     *
     * @param id 标的信息表ID
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-30
     */
    @ApiOperation(value = "标的信息")
    @GetMapping("/select/{id}")
    @RequiresPermissions("sys:product:read")
    public ResultModel selectById(@PathVariable Long id) {
        Assert.notNull(id);
        ProductVO productVO = service.selectVOById(id);
        productVO.setBorrowUserName("["+productVO.getBorrowUserRealName()+"-"+productVO.getBorrowUserPhone()+"]");

        ProductCheck productCheck = productCheckService.selectByProductIdAndType(id,ProductCheckTypeEnum.PRODUCT.getStatus());
        if(productCheck!=null){
            productVO.setFirstCheckRemark(productCheck.getFirstCheckRemark());
        }
        List<ProductFile> productFiles = productFileService.selectByProductId(productVO.getId());
        List<String> fileList=new ArrayList<>();
        String resourceBaseUrl = sysParamService.queryStringByKey(RESOURCE_SERVER_URL);
        for(ProductFile productFile:productFiles){
            if(productVO.getIsNewData()==0){
                fileList.add(resourceBaseUrl+"/images/"+productFile.getUrl());
            }else{
                fileList.add(productFile.getUrl());
            }

        }
        productVO.setFileList(fileList);
        return ResultUtil.ok(productVO);
    }

    /**
     * 查询标的信息表分页方法
     *
     * @param pageModel 分页实体
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 2018-07-30
     */
    @ApiOperation(value = "标的列表")
    @PostMapping("/selectPage")
    @RequiresPermissions("sys:product:read")
    public ResultModel selectPage(@RequestBody PageModel pageModel) {

        ProductVO productVO=new ProductVO();
        final Map condition = pageModel.getCondition();
        if(condition!=null) {
            String jsonStr = JSON.toJSONString(condition);
            productVO = JSON.parseObject(jsonStr, ProductVO.class);
        }
        if(productVO.getCreateTimeStart()!=null){
            String dateStart = DateUtils.formatDate(productVO.getCreateTimeStart());
            dateStart=dateStart+" 00:00:00";
            productVO.setCreateTimeStart(DateUtils.parseDate(dateStart));
        }
        if(productVO.getCreateTimeEnd()!=null){
            String dateEnd = DateUtils.formatDate(productVO.getCreateTimeEnd());
            dateEnd=dateEnd+" 23:59:59";
            productVO.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
        }

        productVO.setIsDel(ProductIsDelEnum.NO_DEL.getStatus());
        Page page = service.selectVOPage(pageModel, productVO);
        return ResultUtil.ok(page);
    }

    /**
     * 初审列表
     * @param pageModel
     * @return
     */
    @ApiOperation(value = "标的初审列表")
    @PostMapping("/selectFirstPage")
    @RequiresPermissions("sys:product:read")
    public ResultModel selectFirstPage(@RequestBody PageModel pageModel) {

        ProductVO productVO=new ProductVO();
        final Map condition = pageModel.getCondition();
        if(condition!=null) {
            String jsonStr = JSON.toJSONString(condition);
            productVO = JSON.parseObject(jsonStr, ProductVO.class);
        }
        if(productVO.getCreateTimeStart()!=null){
            String dateStart = DateUtils.formatDate(productVO.getCreateTimeStart());
            dateStart=dateStart+" 00:00:00";
            productVO.setCreateTimeStart(DateUtils.parseDate(dateStart));
        }
        if(productVO.getCreateTimeEnd()!=null){
            String dateEnd = DateUtils.formatDate(productVO.getCreateTimeEnd());
            dateEnd=dateEnd+" 23:59:59";
            productVO.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
        }
        //初审状态
        productVO.setProductStatus(ProductStatusEnum.FIRST_AUDIT.getStatus());

        productVO.setIsDel(ProductIsDelEnum.NO_DEL.getStatus());
        Page page = service.selectVOPage(pageModel, productVO);
        return ResultUtil.ok(page);
    }

    /**
     * 终审列表
     * @param pageModel
     * @return
     */
    @ApiOperation(value = "标的终审列表")
    @PostMapping("/selectFinalPage")
    @RequiresPermissions("sys:product:read")
    public ResultModel selectFinalPage(@RequestBody PageModel pageModel) {

        ProductVO productVO=new ProductVO();
        final Map condition = pageModel.getCondition();
        if(condition!=null) {
            String jsonStr = JSON.toJSONString(condition);
            productVO = JSON.parseObject(jsonStr, ProductVO.class);
        }
        if(productVO.getCreateTimeStart()!=null){
            String dateStart = DateUtils.formatDate(productVO.getCreateTimeStart());
            dateStart=dateStart+" 00:00:00";
            productVO.setCreateTimeStart(DateUtils.parseDate(dateStart));
        }
        if(productVO.getCreateTimeEnd()!=null){
            String dateEnd = DateUtils.formatDate(productVO.getCreateTimeEnd());
            dateEnd=dateEnd+" 23:59:59";
            productVO.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
        }
        //终审状态
        productVO.setProductStatus(ProductStatusEnum.FINAL_AUDIT.getStatus());

        productVO.setIsDel(ProductIsDelEnum.NO_DEL.getStatus());
        Page page = service.selectVOPage(pageModel, productVO);
        return ResultUtil.ok(page);
    }

    /**
     * 新增标的信息表方法
     *
     * @param entity 标的信息表实体
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 2018-07-30
     */
    @ApiOperation(value = "新增标的")
    @PostMapping("/add")
    @RequiresPermissions("sys:product:update")
    public ResultModel add(@RequestBody ProductVO entity) throws Exception{
        if (entity != null) {
            entity.setCreateBy(this.getCurrentUserId());
            entity.setUpdateBy(this.getCurrentUserId());
            //System.out.printf("========="+ JSON.toJSONString(entity.getFileList()));
            productBusinessService.addProduct(entity);
        }

        return ResultUtil.ok();
    }

    /**
     * 修改标的信息表方法
     *
     * @param entity 标的信息表实体
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 2018-07-30
     */
    @ApiOperation(value = "修改标的")
    @PostMapping("/update")
    @RequiresPermissions("sys:product:update")
    public ResultModel update(@RequestBody ProductVO entity) throws Exception{
        entity.setUpdateBy(this.getCurrentUserId());
        entity.setUpdateTime(new Date());

        if(entity.getId()==null){
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "没有获取到id！");
        }
        Product product = service.selectById(entity.getId());
        if(product==null){
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "没有获取到指定数据");
        }
        if(!(product.getProductStatus().equals(ProductStatusEnum.FIRST_AUDIT.getStatus()) || product.getProductStatus().equals(ProductStatusEnum.FINAL_AUDIT.getStatus())) ){
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "非初审、终审状态的标的不能被修改！");
        }
        productBusinessService.updateProduct(entity);
        return ResultUtil.ok();
    }

    /**
     * 批量删除标的信息表方法
     *
     * @param ids 标的信息表ID集合
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 2018-07-30
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:product:delete")
    public ResultModel delete(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        //boolean b = service.deleteBatchIds(Arrays.asList(ids));
        //标的信息不允许物理删除
        List<Product> updateList=new ArrayList<>();
        for(Long id:ids){
            Product update=new Product();
            update.setId(id);
            update.setIsDel(ProductIsDelEnum.DELETE.getStatus());
            update.setUpdateBy(getCurrentUserId());
            update.setUpdateTime(new Date());
            updateList.add(update);
        }
        boolean b = service.updateBatchById(updateList);
        return ResultUtil.ok(b);
    }

    /**
     * 标的初审
     * @param entity
     * @return
     */
    @ApiOperation(value = "标的初审")
    @PostMapping("/firstCheck")
    @RequiresPermissions("sys:product:firstCheck")
    public ResultModel firstCheck(@RequestBody ProductVO entity) throws Exception {
        if (entity != null) {
            if(entity.getCheckStatus()==null){
                return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取checkStatus的值");
            }
            if(ProductCheckStatusEnum.PASS.getStatus()==entity.getCheckStatus()){
                entity.setProductStatus(ProductStatusEnum.FINAL_AUDIT.getStatus());
            }else{
                entity.setProductStatus(ProductStatusEnum.FIRST_AUDIT_REFUSE.getStatus());
            }
            entity.setCreateBy(this.getCurrentUserId());
            entity.setUpdateBy(this.getCurrentUserId());
            entity.setUpdateTime(new Date());

            productBusinessService.firstCheck(entity);
        }

        return ResultUtil.ok();
    }


    /**
     * 标的终审
     * @param entity
     * @return
     */
    @ApiOperation(value = "标的终审")
    @PostMapping("/finalCheck")
    @RequiresPermissions("sys:product:finalCheck")
    public ResultModel finalCheck(@RequestBody ProductVO entity) throws Exception {
        if (entity != null) {
            if(entity.getCheckStatus()==null){
                return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取checkStatus的值");
            }
            if(ProductCheckStatusEnum.PASS.getStatus()==entity.getCheckStatus()){
                entity.setProductStatus(ProductStatusEnum.BIDING.getStatus());
            }else{
                entity.setProductStatus(ProductStatusEnum.FINAL_AUDIT_REFUSE.getStatus());
            }
            entity.setCreateBy(this.getCurrentUserId());
            entity.setUpdateBy(this.getCurrentUserId());
            entity.setUpdateTime(new Date());

            productBusinessService.finalCheck(entity);
        }

        return ResultUtil.ok();
    }


    /**
     * 查询正在招标中标的信息表分页方法
     *
     * @param pageModel 分页实体
     * @return com.yx.common.web.model.ResultModel
     * @author ZhangPanPan
     * @date 2018-08-19
     */
    @ApiOperation(value = "正在招标中的列表")
    @PostMapping("/selectBidingPage")
    @RequiresPermissions("sys:product:read")
    public ResultModel selectBidingPage(@RequestBody PageModel pageModel) {

        ProductVO productVO=new ProductVO();
        final Map condition = pageModel.getCondition();
        if(condition!=null) {
            String jsonStr = JSON.toJSONString(condition);
            productVO = JSON.parseObject(jsonStr, ProductVO.class);
        }
        if(productVO.getCreateTimeStart()!=null){
            String dateStart = DateUtils.formatDate(productVO.getCreateTimeStart());
            dateStart=dateStart+" 00:00:00";
            productVO.setCreateTimeStart(DateUtils.parseDate(dateStart));
        }
        if(productVO.getCreateTimeEnd()!=null){
            String dateEnd = DateUtils.formatDate(productVO.getCreateTimeEnd());
            dateEnd=dateEnd+" 23:59:59";
            productVO.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
        }
        productVO.setProductStatus(ProductStatusEnum.BIDING.getStatus());
        productVO.setIsDel(ProductIsDelEnum.NO_DEL.getStatus());
        Page page = service.selectBidingVOPage(pageModel, productVO);
        return ResultUtil.ok(page);
    }

    /**
     * 查询已还款标的信息表分页方法
     *
     * @param pageModel 分页实体
     * @return com.yx.common.web.model.ResultModel
     * @author ZhangPanPan
     * @date 2018-08-19
     */
    @ApiOperation(value = "已还款标的列表")
    @PostMapping("/selectPayOffPage")
    @RequiresPermissions("sys:product:read")
    public ResultModel selectPayOffPage(@RequestBody PageModel pageModel) {

        ProductVO productVO=new ProductVO();
        final Map condition = pageModel.getCondition();
        if(condition!=null) {
            String jsonStr = JSON.toJSONString(condition);
            productVO = JSON.parseObject(jsonStr, ProductVO.class);
        }
        if(productVO.getCreateTimeStart()!=null){
            String dateStart = DateUtils.formatDate(productVO.getCreateTimeStart());
            dateStart=dateStart+" 00:00:00";
            productVO.setCreateTimeStart(DateUtils.parseDate(dateStart));
        }
        if(productVO.getCreateTimeEnd()!=null){
            String dateEnd = DateUtils.formatDate(productVO.getCreateTimeEnd());
            dateEnd=dateEnd+" 23:59:59";
            productVO.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
        }
        productVO.setProductStatus(ProductStatusEnum.PAYOFF.getStatus());
        productVO.setIsDel(ProductIsDelEnum.NO_DEL.getStatus());
        productVO.setProductType(ProductTypeEnum.LOAN.getStatus());
        Page page = service.selectVOPage(pageModel, productVO);
        return ResultUtil.ok(page);
    }

    /**
     * 查询还款中标的信息表分页方法
     *
     * @param pageModel 分页实体
     * @return com.yx.common.web.model.ResultModel
     * @author ZhangPanPan
     * @date 2018-08-19
     */
    @ApiOperation(value = "还款中标的列表")
    @PostMapping("/selectRepaymentingPage")
    @RequiresPermissions("sys:product:read")
    public ResultModel selectRepaymentingPage(@RequestBody PageModel pageModel) {

        ProductVO productVO=new ProductVO();
        final Map condition = pageModel.getCondition();
        if(condition!=null) {
            String jsonStr = JSON.toJSONString(condition);
            productVO = JSON.parseObject(jsonStr, ProductVO.class);
        }
        if(productVO.getCreateTimeStart()!=null){
            String dateStart = DateUtils.formatDate(productVO.getCreateTimeStart());
            dateStart=dateStart+" 00:00:00";
            productVO.setCreateTimeStart(DateUtils.parseDate(dateStart));
        }
        if(productVO.getCreateTimeEnd()!=null){
            String dateEnd = DateUtils.formatDate(productVO.getCreateTimeEnd());
            dateEnd=dateEnd+" 23:59:59";
            productVO.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
        }
        productVO.setProductStatus(ProductStatusEnum.REPAYMENTING.getStatus());
        productVO.setIsDel(ProductIsDelEnum.NO_DEL.getStatus());
        productVO.setProductType(ProductTypeEnum.LOAN.getStatus());
        Page page = service.selectVOPage(pageModel, productVO);
        return ResultUtil.ok(page);
    }

    @ApiOperation(value = "根据标的id放款")
    @PostMapping(value = "/loan")
    @RequiresPermissions("sys:product:loan")
    @SysLogOpt(module = "根据标的id放款", value = "根据标的id放款", operationType = Constants.LogOptEnum.ADD)
    public ResultModel loan(@RequestBody ProductVO entity) throws Exception {
        if(StringUtils.isBlank(entity.getSmsCode())){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到短信验证码");
        }
        if(StringUtils.isBlank(entity.getCaptchaId())){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到短信验证码id");
        }
        String sCode = (String) CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SMS_CAPTCHA.value() + entity.getCaptchaId());
        if (StringUtils.isBlank(sCode) || (!sCode.equals(entity.getSmsCode()) )) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "短信验证码不正确");
        }
//        loanService.loanForAdmin(entity.getId());
        return ResultUtil.ok();
    }

    @ApiOperation(value = "首页标的推送")
    @PostMapping(value = "/push")
    @RequiresPermissions("sys:product:pushAndCancel")
    @SysLogOpt(module = "首页标的推送", value = "首页标的推送", operationType = Constants.LogOptEnum.ADD)
    public ResultModel push(@RequestBody Long[] ids) throws Exception {
        Assert.notNull(ids);
        Product update=new Product();
        update.setId(ids[0]);
        update.setIndexShow(ProductIndexShowEnum.PUSH.getStatus());
        service.updateById(update);
        return ResultUtil.ok(true);
    }


    @ApiOperation(value = "首页标的推送-取消")
    @PostMapping(value = "/cancelPush")
    @RequiresPermissions("sys:product:pushAndCancel")
    @SysLogOpt(module = "首页标的推送-取消", value = "首页标的推送-取消", operationType = Constants.LogOptEnum.ADD)
    public ResultModel cancelPush(@RequestBody Long[] ids) throws Exception {
        Assert.notNull(ids);
        Product update=new Product();
        update.setId(ids[0]);
        update.setIndexShow(ProductIndexShowEnum.NO_PUSH.getStatus());
        service.updateById(update);
        return ResultUtil.ok(true);
    }


    @ApiOperation(value = "标的审核信息")
    @GetMapping("/productCheckDetail/{productId}")
    @RequiresPermissions("sys:product:read")
    public ResultModel productCheckDetail(@PathVariable Long productId) {
        Assert.notNull(productId);
        ProductCheckVO productCheckVO = productCheckService.selectVOByProductIdAndType(productId,ProductCheckTypeEnum.PRODUCT.getStatus());
        return ResultUtil.ok(productCheckVO);
    }

    @ApiOperation(value = "修改标的标题")
    @PostMapping("/updateProductTitle")
    @RequiresPermissions("sys:product:updateProductTitle")
    public ResultModel updateProductTitle(@RequestBody ProductVO entity) throws Exception{
        entity.setUpdateBy(this.getCurrentUserId());
        entity.setUpdateTime(new Date());

        if(entity.getId()==null){
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "没有获取到id！");
        }
        if(StringUtils.isBlank(entity.getTitle())){
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "没有获取到title！");
        }
        Product product = service.selectById(entity.getId());
        if(product==null){
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "没有获取到指定数据");
        }
        if(!(product.getProductStatus().equals(ProductStatusEnum.FIRST_AUDIT.getStatus()) || product.getProductStatus().equals(ProductStatusEnum.FINAL_AUDIT.getStatus())) ){
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "非初审、终审状态的标的不能修改名称！");
        }
        Product product1 = service.selectByTitle(entity.getTitle().trim());
        if(product1!=null){
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "标的名称已存在，不能修改！");
        }
        Product update=new Product();
        update.setId(entity.getId());
        update.setTitle(entity.getTitle().trim());
        update.setUpdateBy(getCurrentUserId());
        update.setUpdateTime(new Date());
        service.updateById(update);
        return ResultUtil.ok(true);
    }

    @ApiOperation(value = "新增放款成功提醒手机号")
    @GetMapping("/addRemindPhone")
    public ResultModel addRemindPhone(@RequestParam("phone") String phone) throws Exception {
        productBusinessService.addRemindPhone(phone);
        return ResultUtil.ok();
    }

    @ApiOperation(value = "新增放款成功提醒手机号")
    @GetMapping("/editRemindPhone")
    public ResultModel editRemindPhone(@RequestParam("oldPhone") String oldPhone
            , @RequestParam("newPhone") String newPhone) throws Exception {
        productBusinessService.editRemindPhone(oldPhone, newPhone);
        return ResultUtil.ok();
    }

    @ApiOperation(value = "分页查询放款成功提醒手机号")
    @PostMapping("/pageRemindPhone")
    public ResultModel pageRemindPhone(@RequestBody Page page) throws Exception {
        return ResultUtil.ok(productBusinessService.pageRemindPhones(page));
    }

    @ApiOperation(value = "分页查询放款成功提醒手机号")
    @PostMapping("/delRemindPhone/{phone}")
    public ResultModel delRemindPhone(@PathVariable String phone) throws Exception {
        productBusinessService.delRemindPhone(phone);
        return ResultUtil.ok();
    }

    @ApiOperation(value = "关闭处理中的出借订单")
    @PostMapping("/failBidOrder/{productId}")
    public ResultModel delRemindPhone(@PathVariable Long productId) throws Exception {
//        bidService.failBidOrderByProductId(productId);
        return ResultUtil.ok();
    }


    public static void main(String[] args) {
        BigDecimal b=new BigDecimal(1.23);
        int i = b.intValue();
        System.out.println("11111111111111111111:"+i);
    }

}
