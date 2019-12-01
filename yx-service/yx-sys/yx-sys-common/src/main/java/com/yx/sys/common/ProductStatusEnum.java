package com.yx.sys.common;

/**
 * 标的状态枚举类
 * @author zhangshaogang
 * @since 2018/7/20
 */
public enum ProductStatusEnum {

    FIRST_AUDIT(2,"初审"),
    FINAL_AUDIT(3,"终审"),
    FIRST_AUDIT_REFUSE(4,"初审拒绝"),
    FINAL_AUDIT_REFUSE(5,"终审拒绝"),
    FAIL_BID(6,"招标失败"),
    BIDING(7,"招标中"),
    CANCELED(8,"已撤销"),
    FLOW_PRODUCT(9,"流标"),
    REPAYMENTING(10,"还款中"),
    PAYOFF(11,"已还清"),
    OVERDUE(12,"逾期"),
    FULL_PRODUCT(14,"满标"),
    BANK_LOANING(20,"放款中"),
    BANK_LOANFAIL(21,"放款失败"),
    /** 新增状态*/
    FLOW_PRODUCTING(22,"流标处理中");








    private Integer status;
    private String desc;




    public Integer getStatus() {
        return status;
    }


    public String getDesc() {
        return desc;
    }


    private ProductStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(int status) {
        for(ProductStatusEnum demo: ProductStatusEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

    public static ProductStatusEnum resolve(Integer status) {
        if(status==null){
            return null;
        }
        for(ProductStatusEnum demo: ProductStatusEnum.values()) {
            if(demo.getStatus().equals(status)) {
                return demo;
            }
        }
        return null;
    }


}
