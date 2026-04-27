public class QuantityMeasurementApp {

    // Step 1: Extended Enum
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),          // 1 inch = 1/12 feet
        YARD(3.0),                 // 1 yard = 3 feet
        CENTIMETER(0.393701 / 12.0); // 1 cm = 0.393701 inches → convert to feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // Generic Quantity Class (No change from UC3)
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return unit.toFeet(value); // convert everything to feet
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
    }

    // Demo
    public static void main(String[] args) {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARD);
        QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);

        System.out.println("Yard vs Feet: " + q1.equals(q2)); // true

        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.CENTIMETER);
        QuantityLength q4 = new QuantityLength(0.393701, LengthUnit.INCH);

        System.out.println("CM vs Inch: " + q3.equals(q4)); // true
    }
}