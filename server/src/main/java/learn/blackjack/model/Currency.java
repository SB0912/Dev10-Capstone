package learn.blackjack.model;

import java.math.BigDecimal;

public enum Currency {
    DOLLARS("$",new BigDecimal("1")),
    EUROS("Euro", new BigDecimal("1.05"));

    final String type;

    BigDecimal total;
    final BigDecimal converter;

    Currency(String type, BigDecimal converter) {
        this.type = type;
        this.converter = converter;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDollarValue() {
        return converter.multiply(total);
    }

}
