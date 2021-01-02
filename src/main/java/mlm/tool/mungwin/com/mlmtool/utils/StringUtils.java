package mlm.tool.mungwin.com.mlmtool.utils;

import java.text.DecimalFormat;

public class StringUtils {

    public static boolean isNullOrEmpty(String s){
        return (s == null || s.isEmpty());
    }

    public static String doubleToTwoDecimalPlaces(Double number){
        DecimalFormat format = new DecimalFormat("##.00");
        return format.format(number);
    }

}
