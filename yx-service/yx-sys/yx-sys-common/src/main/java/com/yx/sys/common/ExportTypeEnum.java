package com.yx.sys.common;


/**
 * 导出类型
 * 所有的枚举定义都以enum结束
 */
public enum ExportTypeEnum {
    EXPORT_USER_LIST(1,"导出用户信息","用户信息"),
    EXPORT_PRODUCT(2,"导出标的信息","标的信息"),
    EXPORT_USER_FLOW(3,"导出个人流水的信息","个人流水"),
    EXPORT_BID_ORDER(4,"导出投标列表的信息","投标列表"),
    EXPORT_GUARANTEE_USER_ORDER(5,"导出渠道用户基本信息","渠道用户基本信息"),
    EXPORT_GUARANTEE_REPAY_ORDER(6,"导出渠道待还款信息","渠道代还款信息"),
    EXPORT_GUARANTEE_FINISH_ORDER(7,"导出渠道已还清信息","渠道已还清信息"),
    EXPORT_ORDER(8,"导出借款统计信息","借款统计信息"),
    EXPORT_RECHARGE_ORDER(9,"导出充值统计信息","充值统计信息"),
    EXPORT_WITHDRAW_ORDER(10,"导出提现统计信息","提现统计信息"),
    EXPORT_BORROW_APPLY(11,"导出借款申请信息","借款申请信息"),
    EXPORT_USER_SUGGEST(13,"导出用户反馈信息","用户反馈信息"),
    EXPORT_BORROWER_LIST(14,"导出借款人列表信息","借款人列表信息"),
    EXPORT_DEPOSITARY_LIST(15,"导出受托人列表信息","受托人列表信息"),


    EXPORT_RECHARGE_LIST(16,"pc端充值记录导出","充值记录（PC）"),
    EXPORT_RECOMMEND_LIST(17,"pc端******合伙人提成导出","合伙人提成（PC）"),
    EXPORT_WITHDRAW_LIST(18,"pc端提现记录导出","体现记录（PC）"),

    EXPORT_MYINVESTMENT_LIST(19,"pc端我的出借导出","我的出借（PC）"),
    EXPORT_MYINVESTMENTRECORD_LIST(22,"pc端出借记录导出","出借记录（PC）"),
    EXPORT_MYBORROW_LIST(23,"pc端我的借款导出","我的借款（PC）"),
    EXPORT_MYBORROWRECORD_LIST(27,"pc端借款申请导出","借款申请（PC）"),
    EXPORT_MYTRANSFER_LIST(28,"pc端债权转让导出","债权转让（PC）"),
    EXPORT_MYTRANSFERRECORD_LIST(32,"pc端转让记录导出","转让记录（PC）"),
    EXPORT_BILLTRADESRECORD_LIST(33,"pc端电子账单交易记录导出","电子账单-交易记录（PC）"),
    EXPORT_BILLRECEIPTSRECORD_LIST(34,"pc端电子账单回款记录导出","电子账单-回款记录（PC）"),
    EXPORT_RECOMMENDDETAIL_LIST(35,"pc端提成明细导出","提成明细（PC）"),

    EXPORT_OVERDUERECORD_LIST(36,"导出逾期还款计划列表","逾期还款计划列表"),
    EXPORT_PENDINGPLAN_LIST(37,"导出待还款计划列表","待还款计划列表"),
    EXPORT_REIMBURSEMENT_LIST(38,"导出已还款计划列表","已还款计划列表"),

    EXPORT_PACKAGE_RECEIVE_LIST(39,"导出卡券包发放列表","卡券包发放记录"),
    EXPORT_COUPON_RECEIVE_LIST(40,"导出卡券发放列表","卡券发放记录"),
    EXPORT_RED_PACKAGE_USE_LIST(41,"导出红包使用记录列表","红包使用记录"),
    EXPORT_ADD_INTEREST_USE_LIST(42,"导出加息券使用记录列表","加息券使用记录"),
    EXPORT_CASH_BACK_USE_LIST(43,"导出现金券使用记录列表","现金券使用记录"),

    EXPORT_OPEN_ACCOUNT_USER_FEE_LIST(44,"导出用户开户绑卡费用列表","开户绑卡费用"),

    RECHARGE_FEE_LIST(45,"导出充值银行手续费列表","充值银行手续费列表"),
    WITHDRAW_FEE_LIST(46,"导出提现银行手续费列表","提现银行手续费列表"),

    AUTH_RECORD_LIST(47,"导出授权记录列表","授权记录列表"),
    SEND_EMAIL_LOG_LIST(48,"导出邮件发送记录列表","邮件发送记录列表"),

    RECEIPT_LIST(49,"导出收款计划列表","收款计划列表"),

    SMART_BID_PROCESS_LIST(50,"导出智投已加入列表","智投已加入列表"),
    SMART_BID_RECEIPT_LIST(51,"导出智投收益中列表","智投收益中列表"),
    SMART_BID_OVER_LIST(52,"导出智投已结束列表","智投已结束列表"),
    ;
    /**
     * 成员变量
     */
    private int status;//状态值
    private String desc;//描述
    private String excelName;//导出excel名称

    public int getStatus() {
        return status;
    }
    public String getDesc() {
        return desc;
    }
    public String getExcelName() {
        return excelName;
    }

    /**
     * 构造方法
     * @param status
     * @param desc
     */
    private ExportTypeEnum(int status, String desc,String excelName) {
        this.status=status;
        this.desc=desc;
        this.excelName=excelName;
    }

    public static String getDesc(int status) {
        for(ExportTypeEnum demo: ExportTypeEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

    public static ExportTypeEnum resolve(Integer status){
        for(ExportTypeEnum demo: ExportTypeEnum.values()) {
            if(demo.getStatus()==status) {
                return demo;
            }
        }
        return null;
    }


}
