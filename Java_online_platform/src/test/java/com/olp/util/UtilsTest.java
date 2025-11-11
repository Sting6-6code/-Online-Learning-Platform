package com.olp.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.Calendar;

/**
 * Task 0.6: 工具类测试
 * 验证所有工具方法的正确性
 */
@SpringBootTest
public class UtilsTest {

    @Test
    public void testCompareDates() {
        // 测试相同日期
        Date date1 = new Date(System.currentTimeMillis());
        Date date2 = new Date(date1.getTime());
        assertEquals(0, Utils.compareDates(date1, date2), "相同日期应该返回 0");

        // 测试 d1 < d2
        Date earlier = new Date(1000000000L);
        Date later = new Date(2000000000L);
        assertEquals(-1, Utils.compareDates(earlier, later), "earlier < later 应该返回 -1");
        assertEquals(1, Utils.compareDates(later, earlier), "later > earlier 应该返回 1");

        // 测试 null 值
        assertEquals(0, Utils.compareDates(null, null), "两个 null 应该返回 0");
        assertEquals(-1, Utils.compareDates(null, date1), "null < date 应该返回 -1");
        assertEquals(1, Utils.compareDates(date1, null), "date > null 应该返回 1");

        System.out.println("✅ compareDates() 测试通过");
    }

    @Test
    public void testGenerateId() {
        // 测试生成不同的 ID
        String id1 = Utils.generateId("TEST");
        String id2 = Utils.generateId("TEST");
        assertNotEquals(id1, id2, "每次调用应该生成不同的 ID");

        // 测试 ID 格式
        assertTrue(id1.startsWith("TEST_"), "ID 应该以前缀开头");
        assertTrue(id1.contains("_"), "ID 应该包含分隔符");

        // 测试 null 前缀
        String id3 = Utils.generateId(null);
        assertTrue(id3.startsWith("ID_"), "null 前缀应该使用默认值 'ID'");

        System.out.println("✅ generateId() 测试通过");
    }

    @Test
    public void testAddDays() {
        // 创建基准日期：2024-01-15
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 15);
        Date base = new Date(cal.getTimeInMillis());

        // 测试添加正数天数
        Date result1 = Utils.addDays(base, 10);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(result1);
        assertEquals(25, cal1.get(Calendar.DAY_OF_MONTH), "添加 10 天后应该是 25 日");

        // 测试添加负数天数
        Date result2 = Utils.addDays(base, -5);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(result2);
        assertEquals(10, cal2.get(Calendar.DAY_OF_MONTH), "减去 5 天后应该是 10 日");

        // 测试跨月
        Date result3 = Utils.addDays(base, 20);
        Calendar cal3 = Calendar.getInstance();
        cal3.setTime(result3);
        assertEquals(Calendar.FEBRUARY, cal3.get(Calendar.MONTH), "添加 20 天应该跨到 2 月");
        assertEquals(4, cal3.get(Calendar.DAY_OF_MONTH), "添加 20 天后应该是 2 月 4 日");

        // 测试 null 输入
        assertNull(Utils.addDays(null, 10), "null 输入应该返回 null");

        System.out.println("✅ addDays() 测试通过");
    }

    @Test
    public void testAddMonths() {
        // 创建基准日期：2024-01-31
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 31);
        Date base = new Date(cal.getTimeInMillis());

        // 测试添加 1 个月（边界情况：1 月 31 日 + 1 个月 = 2 月 29 日，因为 2024 是闰年）
        Date result1 = Utils.addMonths(base, 1);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(result1);
        assertEquals(Calendar.FEBRUARY, cal1.get(Calendar.MONTH), "添加 1 个月后应该是 2 月");
        assertEquals(29, cal1.get(Calendar.DAY_OF_MONTH), "1 月 31 日 + 1 个月应该是 2 月 29 日（闰年）");

        // 测试添加 12 个月（跨年）
        Date result2 = Utils.addMonths(base, 12);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(result2);
        assertEquals(2025, cal2.get(Calendar.YEAR), "添加 12 个月应该跨到 2025 年");
        assertEquals(Calendar.JANUARY, cal2.get(Calendar.MONTH), "添加 12 个月后应该是 1 月");

        // 测试添加负数月数
        Date result3 = Utils.addMonths(base, -1);
        Calendar cal3 = Calendar.getInstance();
        cal3.setTime(result3);
        assertEquals(Calendar.DECEMBER, cal3.get(Calendar.MONTH), "减去 1 个月后应该是 12 月");
        assertEquals(2023, cal3.get(Calendar.YEAR), "减去 1 个月应该跨到 2023 年");

        // 测试 null 输入
        assertNull(Utils.addMonths(null, 1), "null 输入应该返回 null");

        System.out.println("✅ addMonths() 测试通过（包括边界情况）");
    }

    @Test
    public void testGetCurrentTime() {
        // 测试返回当前时间
        Date current = Utils.getCurrentTime();
        assertNotNull(current, "getCurrentTime() 不应该返回 null");

        // 验证时间在合理范围内（当前时间前后 1 秒内）
        long now = System.currentTimeMillis();
        long diff = Math.abs(current.getTime() - now);
        assertTrue(diff < 1000, "返回的时间应该在当前时间前后 1 秒内");

        System.out.println("✅ getCurrentTime() 测试通过");
    }

    @Test
    public void testEdgeCases() {
        // 测试边界情况：1 月 31 日 + 1 个月（非闰年）
        Calendar cal = Calendar.getInstance();
        cal.set(2023, Calendar.JANUARY, 31); // 2023 不是闰年
        Date base = new Date(cal.getTimeInMillis());

        Date result = Utils.addMonths(base, 1);
        Calendar calResult = Calendar.getInstance();
        calResult.setTime(result);
        assertEquals(Calendar.FEBRUARY, calResult.get(Calendar.MONTH), "应该是 2 月");
        assertEquals(28, calResult.get(Calendar.DAY_OF_MONTH), "非闰年 1 月 31 日 + 1 个月应该是 2 月 28 日");

        System.out.println("✅ 边界情况测试通过");
    }
}

