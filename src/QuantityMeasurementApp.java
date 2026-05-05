import java.util.Objects;

public class QuantityMeasurementApp {

    // ---------------- INTERFACE ----------------
    public interface IMeasurable {
        double getConversionFactor();
        double convertToBaseUnit(double value);
        double convertFromBaseUnit(double baseValue);
        String getUnitName();
    }

    // ---------------- LENGTH ----------------
    public enum LengthUnit implements IMeasurable {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double getConversionFactor() { return factor; }

        public double convertToBaseUnit(double value) {
            return value * factor;
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / factor;
        }

        public String getUnitName() {
            return name();
        }
    }

    // ---------------- WEIGHT ----------------
    public enum WeightUnit implements IMeasurable {
        KILOGRAM(1.0),
        GRAM(0.001),
        TONNE(1000.0);

        private final double factor;

        WeightUnit(double factor) {
            this.factor = factor;
        }

        public double getConversionFactor() { return factor; }

        public double convertToBaseUnit(double value) {
            return value * factor;
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / factor;
        }

        public String getUnitName() {
            return name();
        }
    }

    // ---------------- GENERIC QUANTITY ----------------
    public static class Quantity<U extends IMeasurable> {

        private final double value;
        private final U unit;
        private static final double EPSILON = 1e-6;

        public Quantity(double value, U unit) {
            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        public double getValue() { return value; }
        public U getUnit() { return unit; }

        // ---------- EQUALS ----------
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Quantity<?> other)) return false;

            // Cross-category prevention
            if (this.unit.getClass() != other.unit.getClass())
                return false;

            double base1 = unit.convertToBaseUnit(value);
            double base2 = other.unit.convertToBaseUnit(other.value);

            return Math.abs(base1 - base2) < EPSILON;
        }

        // ---------- CONVERT ----------
        public Quantity<U> convertTo(U targetUnit) {
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double base = unit.convertToBaseUnit(value);
            double converted = targetUnit.convertFromBaseUnit(base);

            return new Quantity<>(round(converted), targetUnit);
        }

        // ---------- ADD ----------
        public Quantity<U> add(Quantity<U> other) {
            return add(other, this.unit);
        }

        public Quantity<U> add(Quantity<U> other, U targetUnit) {
            if (other == null)
                throw new IllegalArgumentException("Other quantity cannot be null");

            if (this.unit.getClass() != other.unit.getClass())
                throw new IllegalArgumentException("Different measurement categories");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double base1 = unit.convertToBaseUnit(value);
            double base2 = other.unit.convertToBaseUnit(other.value);

            double resultBase = base1 + base2;
            double result = targetUnit.convertFromBaseUnit(resultBase);

            return new Quantity<>(round(result), targetUnit);
        }

        // ---------- HELPERS ----------
        private double round(double value) {
            return Math.round(value * 100.0) / 100.0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(unit.convertToBaseUnit(value));
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit.getUnitName() + ")";
        }
    }

    // ---------------- GENERIC DEMO METHODS ----------------
    public static <U extends IMeasurable> void demonstrateEquality(Quantity<U> q1, Quantity<U> q2) {
        System.out.println("Equality: " + q1 + " == " + q2 + " → " + q1.equals(q2));
    }

    public static <U extends IMeasurable> void demonstrateConversion(Quantity<U> q, U target) {
        System.out.println("Conversion: " + q + " → " + q.convertTo(target));
    }

    public static <U extends IMeasurable> void demonstrateAddition(Quantity<U> q1, Quantity<U> q2, U target) {
        System.out.println("Addition: " + q1 + " + " + q2 + " → " + q1.add(q2, target));
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        // LENGTH
        Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);

        demonstrateEquality(l1, l2);
        demonstrateConversion(l1, LengthUnit.INCHES);
        demonstrateAddition(l1, l2, LengthUnit.FEET);

        System.out.println();

        // WEIGHT
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        demonstrateEquality(w1, w2);
        demonstrateConversion(w1, WeightUnit.GRAM);
        demonstrateAddition(w1, w2, WeightUnit.KILOGRAM);

        System.out.println();

        // CROSS CATEGORY (should be false)
        System.out.println("Cross Category: " +
                l1.equals(new Quantity<>(1.0, WeightUnit.KILOGRAM)));
    }
}