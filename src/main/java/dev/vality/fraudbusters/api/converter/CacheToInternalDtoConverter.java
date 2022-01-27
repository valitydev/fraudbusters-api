package dev.vality.fraudbusters.api.converter;

import dev.vality.damsel.domain.Cash;
import dev.vality.damsel.domain.CurrencyRef;
import dev.vality.swag.fraudbusters.model.CashInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CacheToInternalDtoConverter
        implements Converter<CashInfo, Cash> {

    @Override
    public Cash convert(CashInfo cashInfo) {
        return new Cash()
                .setAmount(cashInfo.getAmount())
                .setCurrency(new CurrencyRef()
                        .setSymbolicCode(cashInfo.getCurrency()));
    }

}
