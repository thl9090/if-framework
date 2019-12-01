package com.yx.sys.common;

/**
 * sys_content表中的content_model_type字段描述
 *
 * @author lilulu
 * @since 2018/08/17 16:16
 */
public enum AgreementTypesEnum {

    AGREEMENT(1,"服务协议"),
    INVESTMENT(2,"投资协议"),
    BORROW(3,"借款协议"),
    RISK_WARNING(4,"风险提示协议"),
	REGISTER_USER_AGREEMENT(5,"注册-******用户协议"),
	REGISTER_RISK_WARNING(6,"注册-风险提示"),
	BORROW_CONSULTANT_CONTRACT(7,"借款-咨询服务合同"),
	BORROW_CONTRACT(8,"借款-借款合同"),
	LOAN_RISK_WARNING(9,"出借-风险提示"),
	LOAN_AGREEMENT(10,"出借-借款合同"),
    TRANSFER_AGREEMENT(11,"转让-借款合同"),
    OPENACCOUNT_AGREEMENT(12,"存管协议"),
    TRADE_AUTHORIZATION_DENSITY_FREE_AGREEMENT(13,"平台交易免密授权书"),
    DIGITAL_CERTIFICATE_SERVICE_PROTOCOL(14,"数字证书服务协议"),
    SMART_RISK_WARNING(15,"智投-风险提示");

    /**
     * 成员变量
     */
    private int status;//状态值
    private String desc;//描述

    public int getStatus() {
        return status;
    }
    public String getDesc() {
        return desc;
    }

    /**
     * 构造方法
     * @param status
     * @param desc
     */
    private AgreementTypesEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(AgreementTypesEnum demo: AgreementTypesEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
