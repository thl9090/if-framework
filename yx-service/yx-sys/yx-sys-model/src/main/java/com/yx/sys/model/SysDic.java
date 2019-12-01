package com.yx.sys.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * <p>
 * 数据字典明细表
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@Data
@TableName("sys_dic")
public class SysDic extends BaseModel {

    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    @NotBlank(message = "字典类型不能为空")
    @Size(min = 1, max = 50, message = "字典类型长度必须在1至50之间")
    @TableField("type_")
    private String type;
    /**
     * 字典值
     */
    @NotBlank(message = "字典值不能为空")
    @Size(min = 1, max = 50, message = "字典值长度必须在1至50之间")
    @TableField("code_")
    private String code;
    /**
     * 字典名称
     */
    @TableField("code_text")
    private String codeText;
    @TableField("parent_type")
    private String parentType;
    @TableField("parent_code")
    private String parentCode;
    /**
     * 排序
     */
    @TableField("sort_no")
    private Integer sortNo;
    @TableField("editable_")
    private Integer editable;
    /**
     * 是否启用
     */
    @TableField("enable_")
    private Integer enable;
    /**
     * 是否删除(0:未删除;1:已删除)
     */
    @TableField("is_del")
    private Integer isDel;
    /**
     * 备注
     */
    @TableField("remark_")
    private String remark;
}
