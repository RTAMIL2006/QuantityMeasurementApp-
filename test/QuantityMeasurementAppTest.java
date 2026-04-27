import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    double EPS = 1e-6;

    @Test
    void testEquality_KilogramToKilogram() {
        var q1 = new QuantityMeasurementApp.QuantityWeight(1.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var q2 = new QuantityMeasurementApp.QuantityWeight(1.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_KilogramToGram() {
        var kg = new QuantityMeasurementApp.QuantityWeight(1.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var g = new QuantityMeasurementApp.QuantityWeight(1000.0, QuantityMeasurementApp.WeightUnit.GRAM);
        assertTrue(kg.equals(g));
    }

    @Test
    void testEquality_KilogramToPound() {
        var kg = new QuantityMeasurementApp.QuantityWeight(1.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var lb = new QuantityMeasurementApp.QuantityWeight(2.20462, QuantityMeasurementApp.WeightUnit.POUND);
        assertEquals(kg.toBase(), lb.toBase(), 1e-3);
    }

    @Test
    void testConversion_KgToGram() {
        var kg = new QuantityMeasurementApp.QuantityWeight(1.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var result = kg.convertTo(QuantityMeasurementApp.WeightUnit.GRAM);
        assertEquals(1000.0, result.toBase() / 0.001, EPS);
    }

    @Test
    void testConversion_PoundToKg() {
        var lb = new QuantityMeasurementApp.QuantityWeight(2.20462, QuantityMeasurementApp.WeightUnit.POUND);
        var result = lb.convertTo(QuantityMeasurementApp.WeightUnit.KILOGRAM);
        assertEquals(1.0, result.toBase(), 1e-3);
    }

    @Test
    void testAddition_SameUnit() {
        var q1 = new QuantityMeasurementApp.QuantityWeight(1.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var q2 = new QuantityMeasurementApp.QuantityWeight(2.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var result = q1.add(q2);
        assertEquals(3.0, result.toBase(), EPS);
    }

    @Test
    void testAddition_CrossUnit() {
        var kg = new QuantityMeasurementApp.QuantityWeight(1.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var g = new QuantityMeasurementApp.QuantityWeight(1000.0, QuantityMeasurementApp.WeightUnit.GRAM);
        var result = kg.add(g);
        assertEquals(2.0, result.toBase(), EPS);
    }

    @Test
    void testAddition_TargetUnit_Gram() {
        var kg = new QuantityMeasurementApp.QuantityWeight(1.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var g = new QuantityMeasurementApp.QuantityWeight(1000.0, QuantityMeasurementApp.WeightUnit.GRAM);
        var result = kg.add(g, QuantityMeasurementApp.WeightUnit.GRAM);
        assertEquals(2000.0, result.toBase() / 0.001, EPS);
    }

    @Test
    void testAddition_TargetUnit_Pound() {
        var kg = new QuantityMeasurementApp.QuantityWeight(1.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var g = new QuantityMeasurementApp.QuantityWeight(1000.0, QuantityMeasurementApp.WeightUnit.GRAM);
        var result = kg.add(g, QuantityMeasurementApp.WeightUnit.POUND);
        assertEquals(4.409, result.toBase() / 0.453592, 1e-2);
    }

    @Test
    void testZeroValue() {
        var kg = new QuantityMeasurementApp.QuantityWeight(5.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var zero = new QuantityMeasurementApp.QuantityWeight(0.0, QuantityMeasurementApp.WeightUnit.GRAM);
        var result = kg.add(zero);
        assertEquals(5.0, result.toBase(), EPS);
    }

    @Test
    void testNegativeValue() {
        var kg = new QuantityMeasurementApp.QuantityWeight(5.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var neg = new QuantityMeasurementApp.QuantityWeight(-2000.0, QuantityMeasurementApp.WeightUnit.GRAM);
        var result = kg.add(neg);
        assertEquals(3.0, result.toBase(), EPS);
    }

    @Test
    void testNullUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityMeasurementApp.QuantityWeight(1.0, null);
        });
    }

    @Test
    void testInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityMeasurementApp.QuantityWeight(Double.NaN, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        });
    }

    @Test
    void testWeightVsLength_NotEqual() {
        var weight = new QuantityMeasurementApp.QuantityWeight(1.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var length = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertFalse(weight.equals(length));
    }
}