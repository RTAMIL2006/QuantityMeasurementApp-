import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityTest {

    // ---------- LENGTH TESTS ----------

    @Test
    void testLengthEquality_FeetToInches() {
        QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
                new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.LengthUnit.FEET);

        QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
                new QuantityMeasurementApp.Quantity<>(12.0, QuantityMeasurementApp.LengthUnit.INCHES);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testLengthConversion() {
        QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q =
                new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.LengthUnit.FEET);

        QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> result =
                q.convertTo(QuantityMeasurementApp.LengthUnit.INCHES);

        assertEquals(12.0, result.getValue());
    }

    @Test
    void testLengthAddition() {
        var q1 = new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.Quantity<>(12.0, QuantityMeasurementApp.LengthUnit.INCHES);

        var result = q1.add(q2, QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(2.0, result.getValue());
    }

    // ---------- WEIGHT TESTS ----------

    @Test
    void testWeightEquality_KgToGram() {
        var q1 = new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var q2 = new QuantityMeasurementApp.Quantity<>(1000.0, QuantityMeasurementApp.WeightUnit.GRAM);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testWeightConversion() {
        var q = new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);

        var result = q.convertTo(QuantityMeasurementApp.WeightUnit.GRAM);

        assertEquals(1000.0, result.getValue());
    }

    @Test
    void testWeightAddition() {
        var q1 = new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
        var q2 = new QuantityMeasurementApp.Quantity<>(1000.0, QuantityMeasurementApp.WeightUnit.GRAM);

        var result = q1.add(q2, QuantityMeasurementApp.WeightUnit.KILOGRAM);

        assertEquals(2.0, result.getValue());
    }

    // ---------- GENERIC BEHAVIOR ----------

    @Test
    void testCrossCategoryEquality_ShouldBeFalse() {
        var length = new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var weight = new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);

        assertFalse(length.equals(weight));
    }

    @Test
    void testNullUnit_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new QuantityMeasurementApp.Quantity<>(1.0, null));
    }

    @Test
    void testInvalidValue_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new QuantityMeasurementApp.Quantity<>(Double.NaN, QuantityMeasurementApp.LengthUnit.FEET));
    }

    @Test
    void testAdd_Null_ShouldThrowException() {
        var q = new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> q.add(null));
    }

    @Test
    void testConvert_Null_ShouldThrowException() {
        var q = new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> q.convertTo(null));
    }

    @Test
    void testSameReferenceEquality() {
        var q = new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertTrue(q.equals(q));
    }
}