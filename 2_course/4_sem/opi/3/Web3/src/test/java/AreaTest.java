import org.example.jsf.CheckBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class AreaTest {
    private final BigDecimal x;
    private final BigDecimal y;
    private final BigDecimal r;
    private final boolean expected;
    private final String description;

    public AreaTest(BigDecimal x, BigDecimal y, BigDecimal r, boolean expected, String description) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.expected = expected;
        this.description = description;
    }

    @Parameters(name = "{index}: {4} | x={0}, y={1}, r={2} : {3}")
    public static Collection<Object[]> data() {
        BigDecimal R = new BigDecimal("4");
        BigDecimal ZERO = BigDecimal.ZERO;

        return Arrays.asList(new Object[][] {
                {new BigDecimal("1"), new BigDecimal("1"), R, false, "1-й квадрант: вне фигуры"},
                {new BigDecimal("100"), new BigDecimal("100"), R, false, "1-й квадрант: большие значения"},

                //{new BigDecimal("0"), new BigDecimal("0"), R, false, "FAIL TEST!!: (0,0) должно быть true, но ждём false"},
        });
    }

    @Test   //метод, осуществляющий тестирование
    public void testCheckHit() {
        boolean result = CheckBean.checkHit(x, y, r);
        assertEquals(description, expected, result);
    }
}