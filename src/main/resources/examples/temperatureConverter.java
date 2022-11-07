class main {

    public static void main(String... args) {
        assertTemperature(new CelsiusConverter(), 1, 1);
        assertTemperature(new KelvinConverter(), 1, 274.15);
        assertTemperature(new FahrenheitConverter(), 1, 33.8);
        assertTemperature(new CelsiusConverter(), 100, 100);
        assertTemperature(new KelvinConverter(), 100, 373.15);
        assertTemperature(new FahrenheitConverter(), 100, 212);
    }

    private static void assertTemperature(Converter converter, double value, double expected) {
        double converted = converter.getConvertedValue(value);
        if (expected != converted) {
            throw new RuntimeException("Result is wrong: expected to be " + expected +
                    " but was " + converted);
        }
    }

}

//Реализуйте ниже три класса-наследника CelsiusConverter, KelvinConverter, FahrenheitConverter
interface Converter {
    Double getConvertedValue(double baseValue);
}

class CelsiusConverter implements Converter {
    @Override
    public Double getConvertedValue(double baseValue) {
        return baseValue;
    }
}

class KelvinConverter implements Converter {

    @Override
    public Double getConvertedValue(double baseValue) {
        return baseValue + 273.15;
    }
}

class FahrenheitConverter implements Converter {
    @Override
    public Double getConvertedValue(double baseValue) {
        return 1.8 * baseValue + 32;
    }
}
