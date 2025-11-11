package com.olp.util;

import java.sql.Date;
import java.util.Calendar;

/**
 * Task 0.6: 工具类 - 公共工具函数
 * 
 * 提供时间比较、ID 生成、日期计算等通用功能
 */
public class Utils {

    // 静态计数器，用于生成唯一 ID
    private static int counter = 0;

    /**
     * 比较两个日期
     * 
     * @param d1 第一个日期
     * @param d2 第二个日期
     * @return -1 如果 d1 < d2, 0 如果 d1 == d2, 1 如果 d1 > d2
     */
    public static int compareDates(Date d1, Date d2) {
        if (d1 == null && d2 == null) {
            return 0;
        }
        if (d1 == null) {
            return -1;
        }
        if (d2 == null) {
            return 1;
        }
        return d1.compareTo(d2);
    }

    /**
     * 生成唯一 ID
     * 
     * @param prefix ID 前缀
     * @return 唯一 ID 字符串
     */
    public static String generateId(String prefix) {
        if (prefix == null) {
            prefix = "ID";
        }
        long timestamp = System.currentTimeMillis();
        synchronized (Utils.class) {
            counter++;
        }
        return prefix + "_" + timestamp + "_" + counter;
    }

    /**
     * 在日期基础上添加天数
     * 
     * @param base 基准日期
     * @param days 要添加的天数（可以为负数）
     * @return 新的日期对象
     */
    public static Date addDays(Date base, int days) {
        if (base == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(base);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 在日期基础上添加月数
     * 
     * @param base 基准日期
     * @param months 要添加的月数（可以为负数）
     * @return 新的日期对象
     */
    public static Date addMonths(Date base, int months) {
        if (base == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(base);
        calendar.add(Calendar.MONTH, months);
        // Calendar 会自动处理边界情况，如 1 月 31 日 + 1 个月 = 2 月 28/29 日
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 获取当前系统时间
     * 
     * @return 当前日期时间
     */
    public static Date getCurrentTime() {
        return new Date(System.currentTimeMillis());
    }
}

