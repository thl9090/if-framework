package com.yx.sys.common;


/**
 * 枚举定义demo
 * 所有的枚举定义都以enum结束
 */
public enum SmsTypeEnum {

    SYS(0,"系统自定义",""),

/*
    VERIFYCODE(1, "验证码类型", "【yx-framework】您的验证验证码是:{$var}。如非本人操作，请忽略。"),
*/
    VERIFYCODE(1, "验证码类型", "【yx-framework】尊敬的用户您的验证码为：{$var}，请尽快完成验证。验证码提供他人可能导致账号被盗，请勿泄漏，谨防被骗。"),
    BID(2, "出借类型", "【yx-framework】%1$s，您好，您在******出借%2$s元已经成功，期限%3$s%4$s，预期收益%5$s元"),
    AUTORECALL(3, "系统撤标类型", "【yx-framework】%1$s，您好，您在******出借%2$s元失败了"),
    RECEIPT(4, "回款类型", "【yx-framework】您好，%1$s，您有新的回款，本金%2$s元，利息%3$s元，管理费%4$s元"),
    REPAY(5, "还款类型", "【yx-framework】您好，%1$s，您于%2$s日有应还款，本金%3$s元，利息%4$s元，总额%5$s元，请提前准备好款项"),
    USERRECALL(6, "用户撤标类型", "【yx-framework】%1$s，您好，您在******出借%2$s元已经成功撤销了"),
    FLOWBID(7, "购买撤销类型","【yx-framework】yx-framework客户，您出借的【%1$s】借款已流标，资金已经回退到您的个人账户中，建议您稍后查询，造成的不便望您谅解，如有疑问请您致电客服热线400-051-6868。"),

    FULLBID_REMIND(8, "满标放款运营提醒","【yx-framework】【%1$s】标的已满标且合同签署成功，请尽快安排放款！"),
    FULLBID_RISK(9, "满标合同签署风控提醒","【yx-framework】【%1$s】个标已满标，请联系借款人签署合同！");




    private Integer status;//状态值
    private String desc;//描述
    private String modelValue;//相应类型的短信模板key

    public Integer getStatus() {
        return status;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getModelValue() {
        return modelValue;
    }


    /**
     * 构造方法
     *
     * @param status
     * @param desc
     */
    private SmsTypeEnum(Integer status, String desc, String modelValue) {
        this.status = status;
        this.desc = desc;
        this.modelValue = modelValue;
    }

    public static String getDesc(int status) {
        for (SmsTypeEnum demo : SmsTypeEnum.values()) {
            if (demo.getStatus() == status) {
                return demo.getDesc();
            }
        }
        return null;
    }

    public static String getModelValue(int status) {
        for (SmsTypeEnum demo : SmsTypeEnum.values()) {
            if (demo.getStatus() == status) {
                return demo.getModelValue();
            }
        }
        return null;
    }
}
