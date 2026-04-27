public class QuantityMeasurementApp {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double value) {
            return value / toFeetFactor;
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

        public double toBaseUnit() {
            return unit.toFeet(value);
        }

        public QuantityLength convertTo(LengthUnit target) {
            double base = toBaseUnit();
            double converted = target.fromFeet(base);
            return new QuantityLength(converted, target);
        }

        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException();
            }
            double sumBase = this.toBaseUnit() + other.toBaseUnit();
            double result = this.unit.fromFeet(sumBase);
            return new QuantityLength(result, this.unit);
        }

        public QuantityLength add(QuantityLength other, LengthUnit target) {
            if (other == null || target == null) {
                throw new IllegalArgumentException();
            }
            double sumBase = this.toBaseUnit() + other.toBaseUnit();
            double result = target.fromFeet(sumBase);
            return new QuantityLength(result, target);
        }

        public static QuantityLength add(QuantityLength q1, QuantityLength q2, LengthUnit target) {
            if (q1 == null || q2 == null || target == null) {
                throw new IllegalArgumentException();
            }
            double sumBase = q1.toBaseUnit() + q2.toBaseUnit();
            double result = target.fromFeet(sumBase);
            return new QuantityLength(result, target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength other = (QuantityLength) obj;
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toBaseUnit());
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void main(String[] args) {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println(q1.add(q2, LengthUnit.FEET));
        System.out.println(q1.add(q2, LengthUnit.INCH));
        System.out.println(q1.add(q2, LengthUnit.YARD));
    }
}