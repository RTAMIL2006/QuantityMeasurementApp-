import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityTest {

    double EPS = 1e-6;

    @Test
    void testLengthEquality() {
        var q1 = new Quantity<>(1.0, LengthUnit.FEET);
        var q2 = new Quantity<>(12.0, LengthUnit.INCH);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testWeightEquality() {
        var q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        var q2 = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testLengthConversion() {
        var q = new Quantity<>(1.0, LengthUnit.FEET);
        var result = q.convertTo(LengthUnit.INCH);
        assertEquals(12.0, result.toBase() * 12.0, EPS);
    }

    @Test
    void testWeightConversion() {
        var q = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        var result = q.convertTo(WeightUnit.GRAM);
        assertEquals(1000.0, result.toBase() / 0.001, EPS);
    }

    @Test
    void testLengthAddition() {
        var q1 = new Quantity<>(1.0, LengthUnit.FEET);
        var q2 = new Quantity<>(12.0, LengthUnit.INCH);
        var result = q1.add(q2, LengthUnit.FEET);
        assertEquals(2.0, result.toBase(), EPS);
    }

    @Test
    void testWeightAddition() {
        var q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        var q2 = new Quantity<>(1000.0, WeightUnit.GRAM);
        var result = q1.add(q2, WeightUnit.KILOGRAM);
        assertEquals(2.0, result.toBase(), EPS);
    }

    @Test
    void testCrossCategoryNotEqual() {
        var length = new Quantity<>(1.0, LengthUnit.FEET);
        var weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertFalse(length.equals(weight));
    }

    @Test
    void testNullUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Quantity<>(1.0, null);
        });
    }

    @Test
    void testInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Quantity<>(Double.NaN, LengthUnit.FEET);
        });
    }
}