package cn.ting.er.datamapping.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class StringUtil {
    private static final int SPECIAL_WHITESPACE_CODE_POINT = 160;
    public static boolean equalsSameAsNullAndEmpty(String a, String b) {
        if (StringUtils.isEmpty(a)) {
            return StringUtils.isEmpty(b);
        }
        return a.equals(b);
    }

    public static String trim(String target) {
        if (StringUtils.isEmpty(target)) {
            return target;
        }
        int length = target.length();
        int start = 0;
        while ((start < length) && isWhiteSpace(target.charAt(start))) {
            start ++;
        }
        while ((start < length) && isWhiteSpace(target.charAt(length - 1))) {
            length --;
        }
        return ((length > 0) || (length < target.length())) ? target.substring(start, length) : target;
    }

    public static String formatNumber(String target) {
        if (target != null) {
            try {
                if (target.contains(".") || target.toLowerCase().contains("e")) {
                    return BigDecimal.valueOf(Double.parseDouble(target)).setScale(10, BigDecimal.ROUND_HALF_UP)
                            .stripTrailingZeros().toPlainString();
                }
            } catch (Exception ignore) {
            }
        }
        return target;
    }

    public static boolean isWhiteSpace(char c) {
        return c <= ' ' || c == SPECIAL_WHITESPACE_CODE_POINT;
    }
}
