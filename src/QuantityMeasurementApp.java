public class QuantityMeasurementApp {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double toBase(double value) {
            return value * factor;
        }

        public double fromBase(double base) {
            return base / factor;
        }
    }

    enum WeightUnit {
        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double factor;

        WeightUnit(double factor) {
            this.factor = factor;
        }

        public double toBase(double value) {
            return value * factor;
        }

        public double fromBase(double base) {
            return base / factor;
        }
    }

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) throw new IllegalArgumentException();
            this.value = value;
            this.unit = unit;
        }

        public double toBase() {
            return unit.toBase(value);
        }

        public QuantityLength convertTo(LengthUnit target) {
            return new QuantityLength(target.fromBase(toBase()), target);
        }

        public QuantityLength add(QuantityLength other) {
            double sum = this.toBase() + other.toBase();
            return new QuantityLength(this.unit.fromBase(sum), this.unit);
        }

        public QuantityLength add(QuantityLength other, LengthUnit target) {
            double sum = this.toBase() + other.toBase();
            return new QuantityLength(target.fromBase(sum), target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength o = (QuantityLength) obj;
            return Double.compare(this.toBase(), o.toBase()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toBase());
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            if (unit == null || !Double.isFinite(value)) throw new IllegalArgumentException();
            this.value = value;
            this.unit = unit;
        }

        public double toBase() {
            return unit.toBase(value);
        }

        public QuantityWeight convertTo(WeightUnit target) {
            return new QuantityWeight(target.fromBase(toBase()), target);
        }

        public QuantityWeight add(QuantityWeight other) {
            double sum = this.toBase() + other.toBase();
            return new QuantityWeight(this.unit.fromBase(sum), this.unit);
        }

        public QuantityWeight add(QuantityWeight other, WeightUnit target) {
            double sum = this.toBase() + other.toBase();
            return new QuantityWeight(target.fromBase(sum), target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityWeight o = (QuantityWeight) obj;
            return Double.compare(this.toBase(), o.toBase()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toBase());
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void main(String[] args) {

        QuantityLength l1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println(l1.equals(l2));
        System.out.println(l1.add(l2));
        System.out.println(l1.add(l2, LengthUnit.YARD));

        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight w3 = new QuantityWeight(2.20462, WeightUnit.POUND);

        System.out.println(w1.equals(w2));
        System.out.println(w1.equals(w3));
        System.out.println(w1.add(w2));
        System.out.println(w1.add(w2, WeightUnit.GRAM));
        System.out.println(w3.convertTo(WeightUnit.KILOGRAM));

        System.out.println(w1.equals(l1));
    }
}