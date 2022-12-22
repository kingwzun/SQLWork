package study.utils;

import com.alibaba.druid.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final String format = "yyyy-MM-dd HH:mm:ss";

    public static Date parse(String time){
        if (StringUtils.isEmpty(time)) {
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
