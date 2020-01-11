package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrencyDTO {
    private String name;
    private String symbol;
    private Double rate;
    private boolean isBase;

    public CurrencyDTO(String name, String symbol, Double rate, boolean isBase) {
        this.name = name;
        this.symbol = symbol;
        this.rate = rate;
        this.isBase = isBase;
    }
}
