import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

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
        public double convertToBaseUnit(double value) { return value * factor; }
        public double convertFromBaseUnit(double baseValue) { return baseValue / factor; }
        public String getUnitName() { return name(); }
    }

    // ---------------- WEIGHT ----------------
    public enum WeightUnit implements IMeasurable {
        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double factor;

        WeightUnit(double factor) {
            this.factor = factor;
        }

        public double getConversionFactor() { return factor; }
        public double convertToBaseUnit(double value) { return value * factor; }
        public double convertFromBaseUnit(double baseValue) { return baseValue / factor; }
        public String getUnitName() { return name(); }
    }

    // ---------------- VOLUME ----------------
    public enum VolumeUnit implements IMeasurable {
        LITRE(1.0),
        MILLILITRE(0.001),
        GALLON(3.78541);

        private final double factor;

        VolumeUnit(double factor) {
            this.factor = factor;
        }

        public double getConversionFactor() { return factor; }
        public double convertToBaseUnit(double value) { return value * factor; }
        public double convertFromBaseUnit(double baseValue) { return baseValue / factor; }
        public String getUnitName() { return name(); }
    }

    // ---------------- ARITHMETIC ENUM ----------------
    public enum ArithmeticOperation {
        ADD((a, b) -> a + b),
        SUBTRACT((a, b) -> a - b),
        DIVIDE((a, b) -> {
            if (b == 0) throw new ArithmeticException("Division by zero");
            return a / b;
        });

        private final DoubleBinaryOperator op;

        ArithmeticOperation(DoubleBinaryOperator op) {
            this.op = op;
        }

        public double compute(double a, double b) {
            return op.applyAsDouble(a, b);
        }
    }

    // ---------------- GENERIC QUANTITY ----------------
    public static class Quantity<U extends IMeasurable> {
        private final double value;
        private final U unit;
        private static final double EPS = 1e-6;

        public Quantity(double value, U unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
            this.value = value;
            this.unit = unit;
        }

        public double getValue() { return value; }
        public U getUnit() { return unit; }

        // ---------- VALIDATION ----------
        private void validate(Quantity<U> other, U target, boolean checkTarget) {
            if (other == null) throw new IllegalArgumentException("Other cannot be null");
            if (unit.getClass() != other.unit.getClass())
                throw new IllegalArgumentException("Different categories");
            if (!Double.isFinite(other.value))
                throw new IllegalArgumentException("Invalid value");

            if (checkTarget && target == null)
                throw new IllegalArgumentException("Target unit cannot be null");
        }

        // ---------- CORE HELPER ----------
        private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation op) {
            double base1 = unit.convertToBaseUnit(value);
            double base2 = other.unit.convertToBaseUnit(other.value);
            return op.compute(base1, base2);
        }

        private double round(double val) {
            return Math.round(val * 100.0) / 100.0;
        }

        // ---------- ADD ----------
        public Quantity<U> add(Quantity<U> other) {
            return add(other, unit);
        }

        public Quantity<U> add(Quantity<U> other, U target) {
            validate(other, target, true);
            double base = performBaseArithmetic(other, ArithmeticOperation.ADD);
            return new Quantity<>(round(target.convertFromBaseUnit(base)), target);
        }

        // ---------- SUBTRACT ----------
        public Quantity<U> subtract(Quantity<U> other) {
            return subtract(other, unit);
        }

        public Quantity<U> subtract(Quantity<U> other, U target) {
            validate(other, target, true);
            double base = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
            return new Quantity<>(round(target.convertFromBaseUnit(base)), target);
        }

        // ---------- DIVIDE ----------
        public double divide(Quantity<U> other) {
            validate(other, null, false);
            return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
        }

        // ---------- CONVERT ----------
        public Quantity<U> convertTo(U target) {
            if (target == null) throw new IllegalArgumentException("Target null");
            double base = unit.convertToBaseUnit(value);
            return new Quantity<>(round(target.convertFromBaseUnit(base)), target);
        }

        // ---------- EQUALS ----------
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Quantity<?> q)) return false;
            if (unit.getClass() != q.unit.getClass()) return false;

            double b1 = unit.convertToBaseUnit(value);
            double b2 = q.unit.convertToBaseUnit(q.value);

            return Math.abs(b1 - b2) < EPS;
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

    // ---------------- MAIN METHOD ----------------
    public static void main(String[] args) {

        // LENGTH
        var l1 = new Quantity<>(10.0, LengthUnit.FEET);
        var l2 = new Quantity<>(6.0, LengthUnit.INCHES);

        System.out.println("Add: " + l1.add(l2));
        System.out.println("Subtract: " + l1.subtract(l2));
        System.out.println("Divide: " + l1.divide(l2));

        // WEIGHT
        var w1 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
        var w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        System.out.println("Weight Add: " + w1.add(w2));
        System.out.println("Weight Subtract: " + w1.subtract(w2));

        // VOLUME
        var v1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        var v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        System.out.println("Volume Add: " + v1.add(v2));
        System.out.println("Volume Subtract: " + v1.subtract(v2));
    }
}