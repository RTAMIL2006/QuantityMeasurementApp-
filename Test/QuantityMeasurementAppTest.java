import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityUC12Test {

    private static final double EPS = 1e-6;

    @Test
    void testSubtraction_SameUnit_Length() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(5.0, QuantityMeasurementApp.LengthUnit.FEET);
        var r = a.subtract(b);
        assertEquals(5.0, r.getValue(), EPS);
    }

    @Test
    void testSubtraction_CrossUnit_Length() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(6.0, QuantityMeasurementApp.LengthUnit.INCHES);
        var r = a.subtract(b);
        assertEquals(9.5, r.getValue(), EPS);
    }

    @Test
    void testSubtraction_ExplicitTarget_Length() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(6.0, QuantityMeasurementApp.LengthUnit.INCHES);
        var r = a.subtract(b, QuantityMeasurementApp.LengthUnit.INCHES);
        assertEquals(114.0, r.getValue(), EPS);
    }

    @Test
    void testSubtraction_NegativeResult() {
        var a = new QuantityMeasurementApp.Quantity<>(5.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var r = a.subtract(b);
        assertEquals(-5.0, r.getValue(), EPS);
    }

    @Test
    void testSubtraction_ZeroResult() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(120.0, QuantityMeasurementApp.LengthUnit.INCHES);
        var r = a.subtract(b);
        assertEquals(0.0, r.getValue(), EPS);
    }

    @Test
    void testSubtraction_Weight() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var b = new QuantityMeasurementApp.Quantity<>(5000.0, QuantityMeasurementApp.WeightUnit.GRAM);
        var r = a.subtract(b);
        assertEquals(5.0, r.getValue(), EPS);
    }

    @Test
    void testSubtraction_Volume() {
        var a = new QuantityMeasurementApp.Quantity<>(5.0, QuantityMeasurementApp.VolumeUnit.LITRE);
        var b = new QuantityMeasurementApp.Quantity<>(500.0, QuantityMeasurementApp.VolumeUnit.MILLILITRE);
        var r = a.subtract(b);
        assertEquals(4.5, r.getValue(), EPS);
    }

    @Test
    void testSubtraction_NullOperand() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertThrows(IllegalArgumentException.class, () -> a.subtract(null));
    }

    @Test
    void testSubtraction_CrossCategory() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(5.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        assertThrows(IllegalArgumentException.class, () -> a.subtract((QuantityMeasurementApp.Quantity) b));
    }

    @Test
    void testDivision_SameUnit_Length() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(2.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertEquals(5.0, a.divide(b), EPS);
    }

    @Test
    void testDivision_CrossUnit_Length() {
        var a = new QuantityMeasurementApp.Quantity<>(24.0, QuantityMeasurementApp.LengthUnit.INCHES);
        var b = new QuantityMeasurementApp.Quantity<>(2.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertEquals(1.0, a.divide(b), EPS);
    }

    @Test
    void testDivision_RatioLessThanOne() {
        var a = new QuantityMeasurementApp.Quantity<>(5.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertEquals(0.5, a.divide(b), EPS);
    }

    @Test
    void testDivision_Weight() {
        var a = new QuantityMeasurementApp.Quantity<>(2.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var b = new QuantityMeasurementApp.Quantity<>(2000.0, QuantityMeasurementApp.WeightUnit.GRAM);
        assertEquals(1.0, a.divide(b), EPS);
    }

    @Test
    void testDivision_Volume() {
        var a = new QuantityMeasurementApp.Quantity<>(1000.0, QuantityMeasurementApp.VolumeUnit.MILLILITRE);
        var b = new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.VolumeUnit.LITRE);
        assertEquals(1.0, a.divide(b), EPS);
    }

    @Test
    void testDivision_ByZero() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(0.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertThrows(ArithmeticException.class, () -> a.divide(b));
    }

    @Test
    void testDivision_NullOperand() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertThrows(IllegalArgumentException.class, () -> a.divide(null));
    }

    @Test
    void testDivision_CrossCategory() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(5.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        assertThrows(IllegalArgumentException.class, () -> a.divide((QuantityMeasurementApp.Quantity) b));
    }

    @Test
    void testIntegration_AddSubtractInverse() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(2.0, QuantityMeasurementApp.LengthUnit.FEET);
        var r = a.add(b).subtract(b);
        assertEquals(a.getValue(), r.getValue(), EPS);
    }

    @Test
    void testChainedSubtraction() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var r = a.subtract(new QuantityMeasurementApp.Quantity<>(2.0, QuantityMeasurementApp.LengthUnit.FEET))
                .subtract(new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.LengthUnit.FEET));
        assertEquals(7.0, r.getValue(), EPS);
    }
}