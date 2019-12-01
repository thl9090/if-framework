package com.yx.common.utils;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * spring expression表达式语言执行工具类
 *
 * @author YanBingHao
 * @since 2018/7/20
 */
public class SpELUtils {

    public static final String LONG_NOT_NULL = "%s != null";
    public static final String LONG_COMPARE = "T(java.lang.Long).valueOf(%s).compareTo(T(java.lang.Long).valueOf(%s)) %s 0";

    public static final String STRING_NOT_NULL = "T(org.apache.commons.lang.StringUtils).isNotBlank('%s')";
    public static final String STRING_COMPARE = "('%s' eq '%s')";

    public static final String STRING_MODEL_NOT_NULL = "T(org.apache.commons.lang.StringUtils).isNotBlank(%s)";
    public static final String STRING_MODEL_COMPARE = "(%s eq '%s')";

    public static final String INTEGER_NOT_NULL = "%s != null";
    public static final String INTEGER_COMPARE = "T(java.lang.Integer).valueOf(%s).compareTo(T(java.lang.Integer).valueOf(%s)) %s 0";

    public static final String BIGDECIMAL_NOT_NULL = "%s != null";
    public static final String BIGDECIMAL_COMPARE = "T(java.math.BigDecimal).valueOf(T(java.lang.Double).valueOf(T(java.lang.String).valueOf(%s))).compareTo(T(java.math.BigDecimal).valueOf(T(java.lang.Double).valueOf(T(java.lang.String).valueOf(%s)))) %s 0";

    public static final String DATE_NOT_NULL = "T(com.yx.common.utils.DateUtils).parseDate('%s') != null";
    public static final String DATE_COMPARE = "T(com.yx.common.utils.DateUtils).parseDate('%s').getTime() - T(com.yx.common.utils.DateUtils).parseDate('%s').getTime() %s 0";

    public static final String DATE_MODEL_NOT_NULL = "T(com.yx.common.utils.DateUtils).parseDate(%s) != null";
    public static final String DATE_MODEL_COMPARE = "T(com.yx.common.utils.DateUtils).parseDate(%s).getTime() - T(com.yx.common.utils.DateUtils).parseDate(%s).getTime() %s 0";

    public static final String DATE_IS_WORKDAY = "T(com.yx.common.utils.DateUtils).isWorkday(T(com.yx.common.utils.DateUtils).parseDate(%s))";
    public static final String DATE_IS_NOT_WORKDAY = "!(T(com.yx.common.utils.DateUtils).isWorkday(T(com.yx.common.utils.DateUtils).parseDate(%s)))";

    public static final String TODAY_IS_WORKDAY = "T(com.yx.common.utils.DateUtils).isWorkday()";
    public static final String TODAY_IS_NOT_WORKDAY = "!(T(com.yx.common.utils.DateUtils).isWorkday())";

    /**
     * 执行expression表达式
     *
     * @param expressionStr
     * @param params
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T executeExpression(String expressionStr, Map<String, Object> params, Class<T> clazz) {
        //创建解析器
        ExpressionParser expressionParser = new SpelExpressionParser();
        //解析表达式
        Expression expression = expressionParser.parseExpression(expressionStr);
        //构造上下文
        EvaluationContext context = new StandardEvaluationContext();
        //为end参数值来赋值
        ((StandardEvaluationContext) context).setVariables(params);
        //打印expression表达式的值
        return expression.getValue(context, clazz);
    }

    /**
     * 执行expression表达式
     *
     * @param expressionStr
     * @param params
     * @return
     */
    public static Object executeExpression(String expressionStr, Map<String, Object> params) {
        //创建解析器
        ExpressionParser expressionParser = new SpelExpressionParser();
        //解析表达式
        Expression expression = expressionParser.parseExpression(expressionStr);
        //构造上下文
        EvaluationContext context = new StandardEvaluationContext();
        //为end参数值来赋值
        ((StandardEvaluationContext) context).setVariables(params);
        //打印expression表达式的值
        return expression.getValue(context);
    }

    public static Object executeExpression(String expressionStr) {
        //创建解析器
        ExpressionParser expressionParser = new SpelExpressionParser();
        //解析表达式
        Expression expression = expressionParser.parseExpression(expressionStr);
        //构造上下文
        EvaluationContext context = new StandardEvaluationContext();
        //打印expression表达式的值
        return expression.getValue(context);
    }

    public static boolean rangeIntersection(BigDecimal sourceStart, BigDecimal sourceEnd, BigDecimal targetStart, BigDecimal targetEnd) {
        return (sourceStart.compareTo(targetStart) >= 0 ? sourceStart : targetStart).compareTo(sourceEnd.compareTo(targetEnd) >= 0 ? targetEnd : sourceEnd) < 0;
    }

    public static boolean rangeIntersection(Integer sourceStart, Integer sourceEnd, Integer targetStart, Integer targetEnd) {
        return (sourceStart.compareTo(targetStart) >= 0 ? sourceStart : targetStart).compareTo(sourceEnd.compareTo(targetEnd) >= 0 ? targetEnd : sourceEnd) < 0;
    }

    public static boolean rangeBigDecimal(BigDecimal source, BigDecimal startNode, BigDecimal endNode) {
        return source.compareTo(startNode) >= 0 && source.compareTo(endNode) <= 0;
    }

    public static boolean rangeInteger(Integer source, Integer startNode, Integer endNode) {
        return source.compareTo(startNode) >= 0 && source.compareTo(endNode) <= 0;
    }

    public static List getPropertyList(List<?> list, String propertyName) {
        String expression = "#list.?[" + propertyName + " != null].![" + propertyName + "]";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("list", list);
        return (List) executeExpression(expression, params);
    }

    public static List filterEquals(List<?> list, String propertyName, String propertyValue) {
        String expression = "#list.?[" + propertyName + " == " + propertyValue + "]";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("list", list);
        return (List) executeExpression(expression, params);
    }

    public static List filterNoEquals(List<?> list, String propertyName, String propertyValue) {
        String expression = "#list.?[" + propertyName + " != " + propertyValue + "]";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("list", list);
        return (List) executeExpression(expression, params);
    }

    public static Object getItemById(List<?> list, Long id) {
        return getItemByLongProperty(list, "id", id);
    }

    public static Object getItemByLongProperty(List<?> list, String propertyName, Long propertyValue) {
        String expression = "#list.^[" + propertyName + ".longValue() == " + propertyValue + "]";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("list", list);
        Object o = executeExpression(expression, params);
        return o;
    }

}
