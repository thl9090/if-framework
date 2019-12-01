package com.yx.mobile.server.controller.content;


import cn.hutool.core.lang.Assert;
import com.yx.common.core.Constants;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.mobile.server.annotation.SysLogOpt;
import com.yx.sys.common.AgreementTypesEnum;
import com.yx.sys.common.ClientTypeEnum;
import com.yx.sys.common.ContentModelTypeEnum;
import com.yx.sys.common.SysContentStatusEnum;
import com.yx.sys.model.SysContent;
import com.yx.sys.rpc.api.SysContentService;
import com.yx.sys.rpc.api.SysParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lilulu
 * @since 2018-07-25
 */
@Slf4j
@RestController
@RequestMapping("/sysContent")
@Api(value = "内容管理", description = "内容管理")
public class SysContentController extends BaseController {
    @Autowired
    private SysContentService sysContentService;

	@Autowired
	private SysParamService sysParamService;


	/**
     * 根据内容标ID查询
     *
     * @param id 内容标ID
     * @return ResultModel
     * @author lilulu
     * @date 2018-08-20
     */
    @ApiOperation(value = "获取某一条数据", notes = "参数：{id:id}")
    @GetMapping("/select")
    @SysLogOpt(module = "根据id获取协议", value = "根据id获取协议", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectById(Long id) {
        Assert.notNull(id);
        SysContent entity = sysContentService.selectById(id);
        if(entity == null){
            entity = new SysContent();
        }
        return ResultUtil.ok(entity);
    }

    /**
     * 手机端注册界面用户协议
     *
     * @return ResultModel
     * @author lilulu
     * @date 2018-08-20
     */
    @ApiOperation(value = "注册--用户协议", notes = "参数：{}")
    @PostMapping("/selectRegistereAgreement")
    @SysLogOpt(module = "注册--用户协议", value = "注册--用户协议", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectRegistereAgreement() {
        SysContent sysContent = new SysContent();
        //有效
        sysContent.setStatus(SysContentStatusEnum.PUBLISH.getStatus());
        //协议
        sysContent.setModelType(ContentModelTypeEnum.AGREEMENT.getStatus());
        //注册协议
        sysContent.setModelTypeTypes(AgreementTypesEnum.REGISTER_USER_AGREEMENT.getStatus());
        //移动端
        sysContent.setClientType(ClientTypeEnum.APP.getStatus());
        SysContent model = sysContentService.selectOneData(sysContent);
        if(model == null){
            model = new SysContent();
        }
        return ResultUtil.ok(model);
    }

    /**
     * 手机端注册界面风险提示
     *
     * @return ResultModel
     * @author lilulu
     * @date 2018-08-20
     */
    @ApiOperation(value = "注册--风险提示", notes = "参数：{}")
    @PostMapping("/selectRiskWarningAgreement")
    @SysLogOpt(module = "注册--风险提示", value = "注册--风险提示", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectRiskWarningAgreement() {
        SysContent sysContent = new SysContent();
        //有效
        sysContent.setStatus(SysContentStatusEnum.PUBLISH.getStatus());
        //协议
        sysContent.setModelType(ContentModelTypeEnum.AGREEMENT.getStatus());
        //风险提示协议
        sysContent.setModelTypeTypes(AgreementTypesEnum.REGISTER_RISK_WARNING.getStatus());
        //移动端
        sysContent.setClientType(ClientTypeEnum.APP.getStatus());
        SysContent model = sysContentService.selectOneData(sysContent);
        if(model == null){
            model = new SysContent();
        }
        return ResultUtil.ok(model);
    }
	
	/**
	 * 手机端借款界面咨询服务合同
	 *
	 * @return ResultModel
	 * @author lilulu
	 * @date 2018-08-20
	 */
	@ApiOperation(value = "借款--咨询服务合同", notes = "参数：{}")
	@PostMapping("/selectBorrowConsultantContract")
	@SysLogOpt(module = "借款--咨询服务合同", value = "借款--咨询服务合同", operationType = Constants.LogOptEnum.QUERY)
	public ResultModel selectBorrowConsultantContract() {
		SysContent sysContent = new SysContent();
		//有效
		sysContent.setStatus(SysContentStatusEnum.PUBLISH.getStatus());
		//协议
		sysContent.setModelType(ContentModelTypeEnum.AGREEMENT.getStatus());
		//风险提示协议
		sysContent.setModelTypeTypes(AgreementTypesEnum.BORROW_CONSULTANT_CONTRACT.getStatus());
		//移动端
		sysContent.setClientType(ClientTypeEnum.APP.getStatus());
		SysContent model = sysContentService.selectOneData(sysContent);
		if(model == null){
			model = new SysContent();
		}
		return ResultUtil.ok(model);
	}
	
	/**
	 * 手机端出借界面风险提示
	 *
	 * @return ResultModel
	 * @author lilulu
	 * @date 2018-08-20
	 */
	@ApiOperation(value = "出借--风险提示", notes = "参数：{}")
	@PostMapping("/selectLoanRiskWarning")
	@SysLogOpt(module = "出借--风险提示", value = "出借--风险提示", operationType = Constants.LogOptEnum.QUERY)
	public ResultModel selectLoanRiskWarning() {
		SysContent sysContent = new SysContent();
		//有效
		sysContent.setStatus(SysContentStatusEnum.PUBLISH.getStatus());
		//协议
		sysContent.setModelType(ContentModelTypeEnum.AGREEMENT.getStatus());
		//风险提示协议
		sysContent.setModelTypeTypes(AgreementTypesEnum.LOAN_RISK_WARNING.getStatus());
		//移动端
		sysContent.setClientType(ClientTypeEnum.APP.getStatus());
		SysContent model = sysContentService.selectOneData(sysContent);
		if(model == null){
			model = new SysContent();
		}
		return ResultUtil.ok(model);
	}


	/**
	 * 出借界面--借款合同
	 *
	 * @return ResultModel
	 * @author lilulu
	 * @date 2018-08-20
	 */
	@ApiOperation(value = "出借--借款合同", notes = "出借--借款合同")
	@PostMapping("/selectLoanAgreement")
	@SysLogOpt(module = "出借--借款合同", value = "出借--借款合同", operationType = Constants.LogOptEnum.QUERY)
	public ResultModel selectLoanAgreement() {
		SysContent sysContent = new SysContent();
		//有效
		sysContent.setStatus(SysContentStatusEnum.PUBLISH.getStatus());
		//协议

		sysContent.setModelType(ContentModelTypeEnum.AGREEMENT.getStatus());
		sysContent.setModelTypeTypes(AgreementTypesEnum.LOAN_AGREEMENT.getStatus());
		sysContent.setClientType(ClientTypeEnum.APP.getStatus());
		SysContent model = sysContentService.selectOneData(sysContent);
		return ResultUtil.ok(model);
	}


	/**
	 * 转让界面--借款合同
	 * @return
	 */
	@ApiOperation(value = "转让--借款合同", notes = "转让--借款合同")
	@PostMapping("/selectTransferAgreement")
	@SysLogOpt(module = "转让--借款合同", value = "转让--借款合同", operationType = Constants.LogOptEnum.QUERY)
	public ResultModel selectTransferAgreement() {
		SysContent sysContent = new SysContent();
		//有效
		sysContent.setStatus(SysContentStatusEnum.PUBLISH.getStatus());
		//协议
		sysContent.setModelType(ContentModelTypeEnum.AGREEMENT.getStatus());

		sysContent.setModelTypeTypes(AgreementTypesEnum.TRANSFER_AGREEMENT.getStatus());
		sysContent.setClientType(ClientTypeEnum.APP.getStatus());
		SysContent model = sysContentService.selectOneData(sysContent);
		return ResultUtil.ok(model);
	}

	/**
	 * 开户存管协议
	 *
	 * @return ResultModel
	 */
	@ApiOperation(value = "开户存管协议", notes = "参数：{}")
	@PostMapping("/selectopenaccountAgreement")
	@SysLogOpt(module = "开户存管协议", value = "开户存管协议", operationType = Constants.LogOptEnum.QUERY)
	public ResultModel selectopenaccountAgreement() {
        SysContent sysContent = new SysContent();
        //有效
        sysContent.setStatus(SysContentStatusEnum.PUBLISH.getStatus());
        //协议
        sysContent.setModelType(ContentModelTypeEnum.AGREEMENT.getStatus());

        sysContent.setModelTypeTypes(AgreementTypesEnum.OPENACCOUNT_AGREEMENT.getStatus());
        sysContent.setClientType(ClientTypeEnum.APP.getStatus());
        SysContent model = sysContentService.selectOneData(sysContent);
		return ResultUtil.ok(model);
	}

	/**
	 * 免密授权书
	 *
	 * @return ResultModel
	 */
	@ApiOperation(value = "免密授权书", notes = "参数：{}")
	@PostMapping("/selectTradeAuthorization")
	@SysLogOpt(module = "免密授权书", value = "免密授权书", operationType = Constants.LogOptEnum.QUERY)
	public ResultModel selectTradeAuthorization() {
        SysContent sysContent = new SysContent();
        //有效
        sysContent.setStatus(SysContentStatusEnum.PUBLISH.getStatus());
        //协议
        sysContent.setModelType(ContentModelTypeEnum.AGREEMENT.getStatus());

        sysContent.setModelTypeTypes(AgreementTypesEnum.TRADE_AUTHORIZATION_DENSITY_FREE_AGREEMENT.getStatus());
        sysContent.setClientType(ClientTypeEnum.APP.getStatus());
        SysContent model = sysContentService.selectOneData(sysContent);
        return ResultUtil.ok(model);
	}
	/**
	 * 免密授权书
	 *
	 * @return ResultModel
	 */
	@ApiOperation(value = "数字证书服务协议", notes = "参数：{}")
	@PostMapping("/digitalCertificate")
	@SysLogOpt(module = "数字证书服务协议", value = "数字证书服务协议", operationType = Constants.LogOptEnum.QUERY)
	public ResultModel digitalCertificate() {
        SysContent sysContent = new SysContent();
        //有效
        sysContent.setStatus(SysContentStatusEnum.PUBLISH.getStatus());
        //协议
        sysContent.setModelType(ContentModelTypeEnum.AGREEMENT.getStatus());

        sysContent.setModelTypeTypes(AgreementTypesEnum.DIGITAL_CERTIFICATE_SERVICE_PROTOCOL.getStatus());
        sysContent.setClientType(ClientTypeEnum.APP.getStatus());
        SysContent model = sysContentService.selectOneData(sysContent);
        return ResultUtil.ok(model);
	}
	/**
	 * 智投-风险提示
	 *
	 * @return ResultModel
	 */
	@ApiOperation(value = "智投-风险提示", notes = "参数：{}")
	@PostMapping("/smartRiskWarning")
	@SysLogOpt(module = "智投-风险提示", value = "智投-风险提示", operationType = Constants.LogOptEnum.QUERY)
	public ResultModel smartRiskWarning() {
        SysContent sysContent = new SysContent();
        //有效
        sysContent.setStatus(SysContentStatusEnum.PUBLISH.getStatus());
        //协议
        sysContent.setModelType(ContentModelTypeEnum.AGREEMENT.getStatus());

        sysContent.setModelTypeTypes(AgreementTypesEnum.SMART_RISK_WARNING.getStatus());
        sysContent.setClientType(ClientTypeEnum.APP.getStatus());
        SysContent model = sysContentService.selectOneData(sysContent);
        return ResultUtil.ok(model);
	}

	/**
	 * 获取协议内容
	 *
	 * @return ResultModel
	 */
	@ApiOperation(value = "获取协议内容", notes = "参数：{}")
	@PostMapping("/selectAgreementInfo")
	@SysLogOpt(module = "获取协议内容", value = "获取协议内容", operationType = Constants.LogOptEnum.QUERY)
	public ResultModel selectAgreementInfo(SysContent sysContent) throws BusinessException {

		if(sysContent ==null || sysContent.getModelTypeTypes() == null){
			throw new BusinessException("请求参数不能为空");
		}
		//有效
		sysContent.setStatus(SysContentStatusEnum.PUBLISH.getStatus());
		//协议
		sysContent.setModelType(ContentModelTypeEnum.AGREEMENT.getStatus());
		//移动端
		sysContent.setClientType(ClientTypeEnum.APP.getStatus());
		SysContent model = sysContentService.selectOneData(sysContent);
		if(model == null){
			throw new BusinessException("该类型所对应的协议内容不存在");
		}
		return ResultUtil.ok(model);
	}
}

