package com.yx.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 利息计算工具类
 *
 * @author YanBingHao
 * @since 2018/7/24
 */
public class InterestUtils {

    // 一年总月数
    public static final BigDecimal YEAR_ALL_MONTH = BigDecimal.valueOf(12);
    // 一百
    public static final BigDecimal ONE_HUNDRED = BigDecimal.TEN.pow(2);
    // 一年总天数
    public static final BigDecimal ONE_YEAR_DAYS = BigDecimal.valueOf(360);
    // 一期总天数
    public static final BigDecimal ONE_PERIODS_DAYS = BigDecimal.valueOf(30);

    /**
     * 等额本息（每期总额）
     * 每期总额 = 贷款本金×[月利率×(1+月利率)^还款月数]÷{[(1+月利率) ^ 还款月数]-1}
     *
     * @param capital
     * @param yearRate
     * @param periods
     * @return
     */
    public static BigDecimal equalCapitalInterest(BigDecimal capital, BigDecimal yearRate, Integer periods) {
        // 年利率 / 100
        yearRate = yearRate.divide(ONE_HUNDRED);
        // 月利率
        BigDecimal monthRate = calcMonthRate(yearRate);
        // (1＋月利率)＾还款期数
        BigDecimal temp = (BigDecimal.ONE.add(monthRate)).pow(periods);
        // 被除数
        BigDecimal firstDivisor = capital.multiply(monthRate).multiply(temp);
        // 除数
        BigDecimal secondDivisor = temp.subtract(BigDecimal.ONE);

        return firstDivisor.divide(secondDivisor, 2, RoundingMode.HALF_UP);
    }

    /**
     * 等额本息（每期本金）
     * 每期本金 = 贷款本金×月利率×(1+月利率)^(还款月序号-1)÷〔(1+月利率)^还款月数-1〕
     *
     * @param capital
     * @param yearRate
     * @param periods
     * @return
     */
    public static BigDecimal equalCapital(BigDecimal capital, BigDecimal yearRate, Integer periods, Integer index) {
        // 年利率 / 100
        yearRate = yearRate.divide(ONE_HUNDRED);
        // 月利率
        BigDecimal monthRate = calcMonthRate(yearRate);
        // (1+月利率)^(还款月序号-1)
        BigDecimal temp1 = (BigDecimal.ONE.add(monthRate)).pow(index - 1);
        // (1＋月利率)＾还款期数
        BigDecimal temp2 = (BigDecimal.ONE.add(monthRate)).pow(periods);
        // 被除数
        BigDecimal firstDivisor = capital.multiply(monthRate).multiply(temp1);
        // 除数
        BigDecimal secondDivisor = temp2.subtract(BigDecimal.ONE);

        return firstDivisor.divide(secondDivisor, 2, RoundingMode.HALF_UP);
    }

    /**
     * 等额本息（每期利息）
     * 每期利息 = 贷款本金×月利率×〔(1+月利率)^还款期数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款期数-1〕
     *
     * @param capital
     * @param yearRate
     * @param periods
     * @return
     */
    public static BigDecimal equalInterest(BigDecimal capital, BigDecimal yearRate, Integer periods, Integer index) {
        // 年利率 / 100
        yearRate = yearRate.divide(ONE_HUNDRED);
        // 月利率
        BigDecimal monthRate = calcMonthRate(yearRate);
        // (1＋月利率)＾还款期数
        BigDecimal temp1 = (BigDecimal.ONE.add(monthRate)).pow(periods);
        // (1+月利率)^(还款月序号-1)
        BigDecimal temp2 = (BigDecimal.ONE.add(monthRate)).pow(index - 1);

        BigDecimal temp = temp1.subtract(temp2);

        // 被除数
        BigDecimal firstDivisor = capital.multiply(monthRate).multiply(temp);
        // 除数
        BigDecimal secondDivisor = temp1.subtract(BigDecimal.ONE);

        return firstDivisor.divide(secondDivisor, 2, RoundingMode.HALF_UP);
    }

    /**
     * 等额本息计算总收益
     *
     * @param capital
     * @param yearRate
     * @param periods
     * @return
     */
    public static BigDecimal equalAllInterest(BigDecimal capital, BigDecimal yearRate, Integer periods) {
        return equalCapitalInterest(capital, yearRate, periods).multiply(BigDecimal.valueOf(periods)).subtract(capital);
    }

    /**
     * 计算月利息
     * 公式 = 本金 * 年利率 / 12
     *
     * @param capital
     * @param yearRate
     * @return
     */
    public static BigDecimal calcPeriodsInterest(BigDecimal capital, BigDecimal yearRate) {
        return capital.multiply(calcMonthRate(yearRate)).divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);
    }

    /**
     * 计算月的总利息
     * 公式 = 本金 * 年利率 / 12
     *
     * @param capital
     * @param yearRate
     * @return
     */
    public static BigDecimal calcAllPeriodsInterest(BigDecimal capital, BigDecimal yearRate, Integer periods) {
        return calcPeriodsInterest(capital, yearRate).multiply(BigDecimal.valueOf(periods));
    }

    /**
     * 计算天利息
     * 公式 = 本金 * 年利率 / 360 * 天数
     *
     * @param capital
     * @param yearRate
     * @return
     */
    public static BigDecimal calcDayInterest(BigDecimal capital, BigDecimal yearRate, Integer days) {
        return capital.multiply(calcDayRate(yearRate)).multiply(BigDecimal.valueOf(days)).divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);
    }

    public static void main(String[] args) {
        BigDecimal allAmount = BigDecimal.ZERO;
        BigDecimal tmpAmount = BigDecimal.ZERO;
        for (int i = 1; i < 54; i++) {
            tmpAmount= InterestUtils.calcDayInterest(BigDecimal.valueOf(200000), BigDecimal.valueOf(Double.valueOf("0.02")), i);
            System.out.println(tmpAmount);
            allAmount = allAmount.add(tmpAmount);
        }
        System.out.println(allAmount);
    }

    /**
     * 计算利息
     * 公式 = 本金 * 利率
     *
     * @param capital
     * @param rate
     * @return
     */
    public static BigDecimal calcInterest(BigDecimal capital, BigDecimal rate) {
        return capital.multiply(rate).divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);
    }

    /**
     * 计算提前还款的违约金
     *
     * @param capital
     * @param violationRate
     * @return
     */
    public static BigDecimal calcInterestForViolation(BigDecimal capital, BigDecimal violationRate, Integer violationDays) {
        return capital.multiply(calcMonthRate(violationRate)).multiply(BigDecimal.valueOf(violationDays).divide(ONE_YEAR_DAYS, 10, RoundingMode.HALF_UP)).divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);
    }

    /**
     * 计算月利率
     * 公式 = 年利率 / 12
     *
     * @param yearRate
     * @return
     */
    public static BigDecimal calcMonthRate(BigDecimal yearRate) {
        return yearRate.divide(YEAR_ALL_MONTH, 10, RoundingMode.HALF_UP);
    }

    /**
     * 计算天利率
     *
     * @param yearRate
     * @return
     */
    public static BigDecimal calcDayRate(BigDecimal yearRate) {
        return yearRate.divide(ONE_YEAR_DAYS, 10, RoundingMode.HALF_UP);
    }

    /**
     * 预计收益
     *
     * @param repayMode
     * @param capital
     * @param rate
     * @param periods
     * @return
     */
    public static BigDecimal preInterest(Integer repayMode, BigDecimal capital, BigDecimal rate, Integer periods) {
        BigDecimal interest = BigDecimal.ZERO;
        switch (repayMode) {
            case 1:
                interest = equalAllInterest(capital, rate, periods);
                break;
            case 2:
                interest = calcPeriodsInterest(capital, rate).multiply(BigDecimal.valueOf(periods));
                break;
            case 3:
                interest = calcDayInterest(capital, rate, periods);
                break;
        }
        return interest;
    }

}
