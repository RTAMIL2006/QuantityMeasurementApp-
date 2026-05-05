public class QuantityMeasurementApp {

    public interface IMeasurable {
        double getConversionFactor();
        double convertToBaseUnit(double value);
        double convertFromBaseUnit(double baseValue);
        String getUnitName();
    }

    public enum LengthUnit implements IMeasurable {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double getConversionFactor() {
            return factor;
        }

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

    public enum WeightUnit implements IMeasurable {
        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double factor;

        WeightUnit(double factor) {
            this.factor = factor;
        }

        public double getConversionFactor() {
            return factor;
        }

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

    public enum VolumeUnit implements IMeasurable {
        LITRE(1.0),
        MILLILITRE(0.001),
        GALLON(3.78541);

        private final double factor;

        VolumeUnit(double factor) {
            this.factor = factor;
        }

        public double getConversionFactor() {
            return factor;
        }

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

    public static class Quantity<U extends IMeasurable> {
        private final double value;
        private final U unit;
        private static final double EPSILON = 1e-6;

        public Quantity(double value, U unit) {
            if (unit == null || !Double.isFinite(value)) throw new IllegalArgumentException();
            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public U getUnit() {
            return unit;
        }

        private double toBase() {
            return unit.convertToBaseUnit(value);
        }

        private double round(double v) {
            return Math.round(v * 100.0) / 100.0;
        }

        public Quantity<U> convertTo(U target) {
            if (target == null) throw new IllegalArgumentException();
            double result = target.convertFromBaseUnit(toBase());
            return new Quantity<>(round(result), target);
        }

        public Quantity<U> add(Quantity<U> other) {
            return add(other, this.unit);
        }

        public Quantity<U> add(Quantity<U> other, U target) {
            validate(other, target);
            double sum = this.toBase() + other.toBase();
            return new Quantity<>(round(target.convertFromBaseUnit(sum)), target);
        }

        public Quantity<U> subtract(Quantity<U> other) {
            return subtract(other, this.unit);
        }

        public Quantity<U> subtract(Quantity<U> other, U target) {
            validate(other, target);
            double diff = this.toBase() - other.toBase();
            return new Quantity<>(round(target.convertFromBaseUnit(diff)), target);
        }

        public double divide(Quantity<U> other) {
            if (other == null) throw new IllegalArgumentException();
            if (!unit.getClass().equals(other.unit.getClass())) throw new IllegalArgumentException();
            double divisor = other.toBase();
            if (Math.abs(divisor) < EPSILON) throw new ArithmeticException();
            return this.toBase() / divisor;
        }

        private void validate(Quantity<U> other, U target) {
            if (other == null || target == null) throw new IllegalArgumentException();
            if (!unit.getClass().equals(other.unit.getClass())) throw new IllegalArgumentException();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Quantity<?> other = (Quantity<?>) obj;
            if (!unit.getClass().equals(other.unit.getClass())) return false;
            return Math.abs(this.toBase() - other.toBase()) < EPSILON;
        }

        public int hashCode() {
            return Double.hashCode(round(toBase()));
        }

        public String toString() {
            return "Quantity(" + value + ", " + unit.getUnitName() + ")";
        }
    }

    public static void main(String[] args) {

        Quantity<LengthUnit> l1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(6.0, LengthUnit.INCHES);

        System.out.println(l1.subtract(l2));
        System.out.println(l1.subtract(l2, LengthUnit.INCHES));
        System.out.println(l1.divide(new Quantity<>(2.0, LengthUnit.FEET)));

        Quantity<WeightUnit> w1 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(5000.0, WeightUnit.GRAM);

        System.out.println(w1.subtract(w2));
        System.out.println(w1.divide(new Quantity<>(5.0, WeightUnit.KILOGRAM)));

        Quantity<VolumeUnit> v1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        System.out.println(v1.subtract(v2));
        System.out.println(v1.subtract(v2, VolumeUnit.MILLILITRE));
        System.out.println(v1.divide(new Quantity<>(10.0, VolumeUnit.LITRE)));
    }
}