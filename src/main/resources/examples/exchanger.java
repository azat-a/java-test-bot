import java.util.*;

class main {

    public static void main(String... args) {
        var exchanger = (Exchanger) new ExchangerImpl();
        exchanger.setCurrencyPair("USD", "EUR", 1.1F);
        exchanger.setCommissionInPercent(2.5F);
        float result = exchanger.makeAnExchange("USD", "EUR", 300);
        double expected = 321.75;
        if (result != expected) {
            throw new RuntimeException("Result is wrong: expected to be " + expected +
                    " but was " + result);
        }
    }

}

//Реализуйте ниже интерфейс Exchanger
interface Exchanger {

    //Задаёт пару валют и отношение между ними
    void setCurrencyPair(String currentCurrency, String targetCurrency, float quotientCurrentByTarget);

    //задаёт комиссию в %
    void setCommissionInPercent(float commissionInPercent);


    //Производит обмен с учетом отношения пары валют и комиссии. В случае отсутствия пары в памяти или комиссии (выдает RuntimeException)
    float makeAnExchange(String currentCurrency, String targetCurrency, float amountOfCurrent);

}

class ExchangerImpl implements Exchanger {

    private final Map<String, Float> holder = new HashMap<>();
    private Float fee;

    @Override
    public void setCurrencyPair(String currentCurrency, String targetCurrency, float quotientCurrentByTarget) {
        holder.put(currentCurrency + targetCurrency, quotientCurrentByTarget);
    }

    @Override
    public void setCommissionInPercent(float commissionInPercent) {
        fee = commissionInPercent;
    }

    @Override
    public float makeAnExchange(String currentCurrency, String targetCurrency, float amountOfCurrent) {
        return Optional.ofNullable(holder.get(currentCurrency + targetCurrency))
                .filter(it -> fee != null)
                .map(it -> (amountOfCurrent * it) - (amountOfCurrent * it) * fee / 100)
                .orElseThrow();
    }
}
