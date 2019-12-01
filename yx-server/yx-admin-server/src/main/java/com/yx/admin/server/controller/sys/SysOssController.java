package com.yx.admin.server.controller.sys;


import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.Constants;
import com.yx.common.core.model.PageModel;
import com.yx.common.oss.configuration.OSSFactory;
import com.yx.common.utils.DateUtils;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.model.SysOss;
import com.yx.sys.model.vo.SysOssVO;
import com.yx.sys.rpc.api.SysOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 文件上传前端控制器
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-29
 */
@Api(value = "oss文件上传", description = "oss文件上传")
@RestController
@RequestMapping("/sysOss")
public class SysOssController extends BaseController {
	@Autowired
	private SysOssService service;

	/**
	 * 根据文件上传ID查询
	 *
	 * @param id 文件上传ID
	 * @return ResultModel
	 * @author TangHuaLiang
	 * @date 2018-07-29
	 */
	@ApiOperation(value = "根据主键id查询", notes = "根据主键id查询")
	@GetMapping("/select/{id}")
	public ResultModel selectById(@PathVariable Long id) {
		Assert.notNull(id);
		SysOss entity = service.selectById(id);
		return ResultUtil.ok(entity);
	}

	/**
	 * 查询文件上传分页方法
	 *
	 * @param pageModel 分页实体
	 * @return com.yx.common.web.model.ResultModel
	 * @author TangHuaLiang
	 * @date 2018-07-29
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/selectPage")
	public ResultModel queryListPage(@RequestBody PageModel<SysOssVO> pageModel) {
		SysOssVO sysOssVO=new SysOssVO();
		final Map condition = pageModel.getCondition();
		if(condition!=null) {
			String jsonStr = JSON.toJSONString(condition);
			sysOssVO = JSON.parseObject(jsonStr, SysOssVO.class);
		}
		if(sysOssVO.getCreateTimeStart()!=null){
			sysOssVO.setCreateTimeStart(DateUtils.getOfDayFirst(sysOssVO.getCreateTimeStart()));
		}
		if(sysOssVO.getCreateTimeEnd()!=null){
			sysOssVO.setCreateTimeEnd(DateUtils.getOfDayLast(sysOssVO.getCreateTimeEnd()));
		}

		Page<SysOssVO> page = service.selectVOPage(pageModel, sysOssVO);
		return ResultUtil.ok(page);
	}

	/**
	 * 新增文件上传方法
	 *
	 * @param entity 文件上传实体
	 * @return com.yx.common.web.model.ResultModel
	 * @author TangHuaLiang
	 * @date 2018-07-29
	 */
//	@PostMapping("/add")
//	public ResultModel add(@Valid @RequestBody SysOss entity) {
//		if (entity != null) {
//			entity.setCreateBy(this.getCurrentUserId());
//			entity.setUpdateBy(this.getCurrentUserId());
//		}
//		return ResultUtil.ok(service.add(entity));
//	}

	/**
	 * 修改文件上传方法
	 *
	 * @param entity 文件上传实体
	 * @return com.yx.common.web.model.ResultModel
	 * @author TangHuaLiang
	 * @date 2018-07-29
	 */
//	@PostMapping("/update")
//	public ResultModel update(@RequestBody SysOss entity) {
//		entity.setUpdateBy(this.getCurrentUserId());
//		entity.setUpdateTime(new Date());
//		service.updateById(entity);
//		return ResultUtil.ok();
//	}

	/**
	 * 批量删除文件上传方法
	 *
	 * @param ids 文件上传ID集合
	 * @return com.yx.common.web.model.ResultModel
	 * @author TangHuaLiang
	 * @date 2018-07-29
	 */
//	@PostMapping("/delete")
//	public ResultModel delete(@RequestBody Long[] ids) {
//		Assert.notNull(ids);
//		return ResultUtil.ok(service.deleteBatchIds(Arrays.asList(ids)));
//	}


	/**
	 * 上传文件
	 */
	@ApiOperation(value = "其他文件上传", notes = "其他文件上传")
	@PostMapping("/uploadTest")
	public ResultModel uploadTest()  {

		//File newFile=new File("C:/Users/YX/Desktop/P80518-090916.jpg");
		File newFile=new File("C:/Users/YX/Desktop/广东华兴银行外联接口规范_v2.3.6.pdf");
//		FileInputStream input=new FileInputStream(newFile);
//		String suffix = newFile.getPath().substring(newFile.getPath().lastIndexOf("."));
//		byte[] bytes = StreamUtils.InputStreamTOByte(input);
//		//方法1：
//		String url = OSSFactory.build().uploadSuffix(bytes, suffix);

		//方法2：
		String url = null;
		try {
			url = OSSFactory.build().upload(newFile);
		} catch (IOException e) {
			e.printStackTrace();
			return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "文件上传失败");
		}
		return ResultUtil.ok(url);
	}


	/**
	 * 上传文件
	 */
	@ApiOperation(value = "表单文件上传", notes = "表单文件上传")
	@PostMapping("/upload")
	public ResultModel upload(@RequestParam("file") MultipartFile file){

		if (file.isEmpty()) {
			return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到文件对象");
		}
		//上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = null;
		Map<String,Object> map=new HashMap<>();
		try {
			url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
			SysOss oss=new SysOss();
			oss.setUrl(url);
			service.add(oss);
			map.put("url",url);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "文件上传失败");
		}

		return ResultUtil.ok(map);
	}

	/**
	 * 富文本编辑器上传文件
	 */
	@ApiOperation(value = "富文本编辑器文件上传", notes = "富文本编辑器文件上传")
	@PostMapping("/layEditUpload")
	public ResultModel layEditUpload(@RequestParam("file") MultipartFile file){

		if (file.isEmpty()) {
			return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到文件对象");
		}
		//上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = null;
		Map<String,Object> map=new HashMap<>();
		try {
			url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
			SysOss oss=new SysOss();
			oss.setUrl(url);
			service.add(oss);
			map.put("src",url);
			map.put("title","");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "文件上传失败");
		}
		ResultModel resultModel=new ResultModel(0,"上传成功",map);
		return resultModel;
	}




	@ApiOperation(value = "UEditor富文本编辑器文件上传", notes = "UEditor富文本编辑器文件上传")
	@PostMapping("/UEditorUpload")
	public Map UEditorUpload(@RequestParam("file") MultipartFile file){
		Map<String,Object> map=new HashMap<>();
		if (file.isEmpty()) {
			map.put("state","没有获取到文件对象");
			return map;
		}
		//上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = null;
		try {
			url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
			SysOss oss=new SysOss();
			oss.setUrl(url);
			service.add(oss);
			map.put("state","SUCCESS");
			map.put("url",url);
			map.put("title","");
			map.put("original","");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state","文件上传失败");
			return map;
		}
	}


//	public static void main(String[] args) {
//		OssProperties ossProperties=new OssProperties();
//		ossProperties.setAccessKeyId("LTAIFYQhM80vNdRS");
//		ossProperties.setAccessKeySecret("tF8RD2BIjqsKQ9IwGplbrGBB3PxXUN");
//		ossProperties.setBucketName("yx-oss-public");
//		ossProperties.setDomain("https://yx-oss-public.oss-cn-qingdao.aliyuncs.com");
//		ossProperties.setEndPoint("https://oss-cn-qingdao.aliyuncs.com");
//		ossProperties.setPrefix("upload");
//		ossProperties.setType(1);
//		String s = JSON.toJSONString(ossProperties);
//		System.out.println("============:"+s);
//	}

}

