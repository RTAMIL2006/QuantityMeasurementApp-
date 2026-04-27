import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    double EPS = 1e-6;

    @Test
    void testAddition_TargetUnit_Feet() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        var result = q1.add(q2, QuantityMeasurementApp.LengthUnit.FEET);
        assertEquals(new QuantityMeasurementApp.QuantityLength(2.0, QuantityMeasurementApp.LengthUnit.FEET), result);
    }

    @Test
    void testAddition_TargetUnit_Inches() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        var result = q1.add(q2, QuantityMeasurementApp.LengthUnit.INCH);
        assertEquals(new QuantityMeasurementApp.QuantityLength(24.0, QuantityMeasurementApp.LengthUnit.INCH), result);
    }

    @Test
    void testAddition_TargetUnit_Yards() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        var result = q1.add(q2, QuantityMeasurementApp.LengthUnit.YARD);
        assertEquals(0.666666, result.convertTo(QuantityMeasurementApp.LengthUnit.YARD).toBaseUnit() / 3.0, 1e-3);
    }

    @Test
    void testAddition_TargetUnit_Centimeter() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.INCH);
        var q2 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.INCH);
        var result = q1.add(q2, QuantityMeasurementApp.LengthUnit.CENTIMETER);
        assertEquals(5.08, result.convertTo(QuantityMeasurementApp.LengthUnit.CENTIMETER).value, 1e-2);
    }

    @Test
    void testAddition_Commutativity() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);

        var r1 = q1.add(q2, QuantityMeasurementApp.LengthUnit.YARD);
        var r2 = q2.add(q1, QuantityMeasurementApp.LengthUnit.YARD);

        assertEquals(r1, r2);
    }

    @Test
    void testAddition_WithZero() {
        var q1 = new QuantityMeasurementApp.QuantityLength(5.0, QuantityMeasurementApp.LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.QuantityLength(0.0, QuantityMeasurementApp.LengthUnit.INCH);

        var result = q1.add(q2, QuantityMeasurementApp.LengthUnit.YARD);

        assertEquals(1.666666, result.convertTo(QuantityMeasurementApp.LengthUnit.YARD).toBaseUnit() / 3.0, 1e-3);
    }

    @Test
    void testAddition_NegativeValues() {
        var q1 = new QuantityMeasurementApp.QuantityLength(5.0, QuantityMeasurementApp.LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.QuantityLength(-2.0, QuantityMeasurementApp.LengthUnit.FEET);

        var result = q1.add(q2, QuantityMeasurementApp.LengthUnit.INCH);

        assertEquals(36.0, result.convertTo(QuantityMeasurementApp.LengthUnit.INCH).value, 1e-6);
    }

    @Test
    void testAddition_NullTargetUnit() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);

        assertThrows(IllegalArgumentException.class, () -> q1.add(q2, null));
    }
}