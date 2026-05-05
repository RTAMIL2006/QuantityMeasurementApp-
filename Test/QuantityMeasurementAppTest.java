import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testEquality_LitreToMillilitre() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertTrue(a.equals(b));
    }

    @Test
    void testEquality_LitreToGallon() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(0.264172, VolumeUnit.GALLON);
        assertTrue(a.equals(b));
    }

    @Test
    void testConversion_LitreToMillilitre() {
        Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> result = q.convertTo(VolumeUnit.MILLILITRE);
        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_GallonToLitre() {
        Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> result = q.convertTo(VolumeUnit.LITRE);
        assertEquals(3.78541, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_LitrePlusMillilitre() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> result = a.add(b);
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_WithTargetUnit() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> result = a.add(b, VolumeUnit.MILLILITRE);
        assertEquals(2000.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_GallonPlusLitre() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> b = new Quantity<>(3.78541, VolumeUnit.LITRE);
        Quantity<VolumeUnit> result = a.add(b);
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testZeroValue() {
        Quantity<VolumeUnit> a = new Quantity<>(0.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(0.0, VolumeUnit.MILLILITRE);
        assertTrue(a.equals(b));
    }

    @Test
    void testNegativeValues() {
        Quantity<VolumeUnit> a = new Quantity<>(-1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(-1000.0, VolumeUnit.MILLILITRE);
        assertTrue(a.equals(b));
    }

    @Test
    void testCrossCategory_NotEqual() {
        Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        assertFalse(volume.equals(length));
    }

    @Test
    void testSameReference() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertTrue(a.equals(a));
    }

    @Test
    void testNullComparison() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertFalse(a.equals(null));
    }

    @Test
    void testInvalidUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Quantity<>(1.0, null);
        });
    }

    @Test
    void testNaNValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Quantity<>(Double.NaN, VolumeUnit.LITRE);
        });
    }
}