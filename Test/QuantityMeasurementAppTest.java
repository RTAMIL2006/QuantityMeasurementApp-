import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityUC13Test {

    private static final double EPS = 1e-6;

    // ---------- ADD (Behavior preserved) ----------

    @Test
    void testAdd_BehaviorPreserved() {
        var a = new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(12.0, QuantityMeasurementApp.LengthUnit.INCHES);

        var r = a.add(b);
        assertEquals(2.0, r.getValue(), EPS);
    }

    // ---------- SUBTRACT ----------

    @Test
    void testSubtract_DelegationAndResult() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(6.0, QuantityMeasurementApp.LengthUnit.INCHES);

        var r = a.subtract(b);
        assertEquals(9.5, r.getValue(), EPS);
    }

    @Test
    void testSubtract_ExplicitTargetUnit() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(6.0, QuantityMeasurementApp.LengthUnit.INCHES);

        var r = a.subtract(b, QuantityMeasurementApp.LengthUnit.INCHES);
        assertEquals(114.0, r.getValue(), EPS);
    }

    // ---------- DIVIDE ----------

    @Test
    void testDivide_DelegationAndResult() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(2.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(5.0, a.divide(b), EPS);
    }

    @Test
    void testDivide_CrossUnit() {
        var a = new QuantityMeasurementApp.Quantity<>(24.0, QuantityMeasurementApp.LengthUnit.INCHES);
        var b = new QuantityMeasurementApp.Quantity<>(2.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(1.0, a.divide(b), EPS);
    }

    // ---------- VALIDATION (Centralized) ----------

    @Test
    void testValidation_NullOperand_AllOperations() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> a.add(null));
        assertThrows(IllegalArgumentException.class, () -> a.subtract(null));
        assertThrows(IllegalArgumentException.class, () -> a.divide(null));
    }

    @Test
    void testValidation_CrossCategory_AllOperations() {
        var length = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var weight = new QuantityMeasurementApp.Quantity<>(5.0, QuantityMeasurementApp.WeightUnit.KILOGRAM);

        assertThrows(IllegalArgumentException.class, () -> length.add((QuantityMeasurementApp.Quantity) weight));
        assertThrows(IllegalArgumentException.class, () -> length.subtract((QuantityMeasurementApp.Quantity) weight));
        assertThrows(IllegalArgumentException.class, () -> length.divide((QuantityMeasurementApp.Quantity) weight));
    }

    @Test
    void testValidation_NullTargetUnit() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(5.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> a.add(b, null));
        assertThrows(IllegalArgumentException.class, () -> a.subtract(b, null));
    }

    // ---------- ENUM OPERATION TEST ----------

    @Test
    void testArithmeticEnum_ADD() {
        double result = QuantityMeasurementApp.ArithmeticOperation.ADD.compute(10, 5);
        assertEquals(15.0, result, EPS);
    }

    @Test
    void testArithmeticEnum_SUBTRACT() {
        double result = QuantityMeasurementApp.ArithmeticOperation.SUBTRACT.compute(10, 5);
        assertEquals(5.0, result, EPS);
    }

    @Test
    void testArithmeticEnum_DIVIDE() {
        double result = QuantityMeasurementApp.ArithmeticOperation.DIVIDE.compute(10, 5);
        assertEquals(2.0, result, EPS);
    }

    @Test
    void testArithmeticEnum_DivideByZero() {
        assertThrows(ArithmeticException.class,
                () -> QuantityMeasurementApp.ArithmeticOperation.DIVIDE.compute(10, 0));
    }

    // ---------- ROUNDING ----------

    @Test
    void testRounding_AddSubtract() {
        var a = new QuantityMeasurementApp.Quantity<>(1.234, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(0.001, QuantityMeasurementApp.LengthUnit.FEET);

        var r = a.add(b);
        assertEquals(1.24, r.getValue(), EPS);
    }

    @Test
    void testDivide_NoRounding() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(3.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(10.0 / 3.0, a.divide(b), EPS);
    }

    // ---------- IMMUTABILITY ----------

    @Test
    void testImmutability_AfterOperations() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(5.0, QuantityMeasurementApp.LengthUnit.FEET);

        a.add(b);
        a.subtract(b);
        a.divide(b);

        assertEquals(10.0, a.getValue(), EPS);
    }

    // ---------- CHAIN OPERATIONS ----------

    @Test
    void testChainedOperations() {
        var a = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET);

        var result = a.add(new QuantityMeasurementApp.Quantity<>(2.0, QuantityMeasurementApp.LengthUnit.FEET))
                .subtract(new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.LengthUnit.FEET));

        assertEquals(11.0, result.getValue(), EPS);
    }

    // ---------- MULTI CATEGORY ----------

    @Test
    void testAllCategories_Working() {

        var length = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.LengthUnit.FEET)
                .subtract(new QuantityMeasurementApp.Quantity<>(6.0, QuantityMeasurementApp.LengthUnit.INCHES));
        assertEquals(9.5, length.getValue(), EPS);

        var weight = new QuantityMeasurementApp.Quantity<>(10.0, QuantityMeasurementApp.WeightUnit.KILOGRAM)
                .add(new QuantityMeasurementApp.Quantity<>(1000.0, QuantityMeasurementApp.WeightUnit.GRAM));
        assertEquals(11.0, weight.getValue(), EPS);

        var volume = new QuantityMeasurementApp.Quantity<>(5.0, QuantityMeasurementApp.VolumeUnit.LITRE)
                .subtract(new QuantityMeasurementApp.Quantity<>(500.0, QuantityMeasurementApp.VolumeUnit.MILLILITRE));
        assertEquals(4.5, volume.getValue(), EPS);
    }
}