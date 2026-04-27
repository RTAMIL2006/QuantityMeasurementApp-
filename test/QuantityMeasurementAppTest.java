import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    double EPS = 1e-6;

    @Test
    void testEquality_FeetAndInch() {
        var f = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var i = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        assertTrue(f.equals(i));
    }

    @Test
    void testConvert_FeetToInch() {
        var f = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var result = f.convertTo(QuantityMeasurementApp.LengthUnit.INCH);
        assertEquals(12.0, result.toBase() * 12.0, EPS);
    }

    @Test
    void testConvert_CmToInch() {
        var c = new QuantityMeasurementApp.QuantityLength(2.54, QuantityMeasurementApp.LengthUnit.CENTIMETER);
        var result = c.convertTo(QuantityMeasurementApp.LengthUnit.INCH);
        assertEquals(1.0, result.toBase() * 12.0, EPS);
    }

    @Test
    void testAdd_SameUnit() {
        var f1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var f2 = new QuantityMeasurementApp.QuantityLength(2.0, QuantityMeasurementApp.LengthUnit.FEET);
        var result = f1.add(f2);
        assertEquals(3.0, result.toBase(), EPS);
    }

    @Test
    void testAdd_CrossUnit() {
        var f = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var i = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        var result = f.add(i);
        assertEquals(2.0, result.toBase(), EPS);
    }

    @Test
    void testAdd_TargetUnit_Inch() {
        var f = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var i = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        var result = f.add(i, QuantityMeasurementApp.LengthUnit.INCH);
        assertEquals(24.0, result.toBase() * 12.0, EPS);
    }

    @Test
    void testAdd_TargetUnit_Yard() {
        var f = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var i = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        var result = f.add(i, QuantityMeasurementApp.LengthUnit.YARD);
        assertEquals(2.0 / 3.0, result.toBase() / 3.0, 1e-3);
    }

    @Test
    void testZeroAddition() {
        var f = new QuantityMeasurementApp.QuantityLength(5.0, QuantityMeasurementApp.LengthUnit.FEET);
        var zero = new QuantityMeasurementApp.QuantityLength(0.0, QuantityMeasurementApp.LengthUnit.INCH);
        var result = f.add(zero);
        assertEquals(5.0, result.toBase(), EPS);
    }

    @Test
    void testNegativeAddition() {
        var f1 = new QuantityMeasurementApp.QuantityLength(5.0, QuantityMeasurementApp.LengthUnit.FEET);
        var f2 = new QuantityMeasurementApp.QuantityLength(-2.0, QuantityMeasurementApp.LengthUnit.FEET);
        var result = f1.add(f2);
        assertEquals(3.0, result.toBase(), EPS);
    }

    @Test
    void testNullUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityMeasurementApp.QuantityLength(1.0, null);
        });
    }

    @Test
    void testInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityMeasurementApp.QuantityLength(Double.NaN, QuantityMeasurementApp.LengthUnit.FEET);
        });
    }
}