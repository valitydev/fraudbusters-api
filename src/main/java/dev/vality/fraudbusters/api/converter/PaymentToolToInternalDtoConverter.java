package dev.vality.fraudbusters.api.converter;

import dev.vality.damsel.domain.PaymentTool;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentToolToInternalDtoConverter
        implements Converter<dev.vality.swag.fraudbusters.model.BankCard, PaymentTool> {

    private final BankCardToInternalDtoConverter bankCardToInternalDtoConverter;

    @Override
    public PaymentTool convert(dev.vality.swag.fraudbusters.model.BankCard bankCard) {
        return PaymentTool.bank_card(bankCardToInternalDtoConverter.convert(bankCard));
    }

}
