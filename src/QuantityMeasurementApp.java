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

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException();
            }
            this.value = value;
            this.unit = unit;
        }

        public double toBase() {
            return unit.toBase(value);
        }

        public QuantityLength convertTo(LengthUnit target) {
            double base = toBase();
            return new QuantityLength(target.fromBase(base), target);
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
            QuantityLength other = (QuantityLength) obj;
            return Double.compare(this.toBase(), other.toBase()) == 0;
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

        QuantityLength f1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength i1 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength y1 = new QuantityLength(1.0, LengthUnit.YARD);
        QuantityLength c1 = new QuantityLength(2.54, LengthUnit.CENTIMETER);

        System.out.println(f1.equals(i1));
        System.out.println(f1.convertTo(LengthUnit.INCH));
        System.out.println(f1.add(i1));
        System.out.println(f1.add(i1, LengthUnit.INCH));
        System.out.println(f1.add(i1, LengthUnit.YARD));
        System.out.println(y1.add(f1, LengthUnit.YARD));
        System.out.println(c1.convertTo(LengthUnit.INCH));
    }
}