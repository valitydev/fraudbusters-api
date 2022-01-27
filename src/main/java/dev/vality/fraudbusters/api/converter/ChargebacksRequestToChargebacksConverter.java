package dev.vality.fraudbusters.api.converter;

import dev.vality.damsel.fraudbusters.Chargeback;
import dev.vality.damsel.fraudbusters.ChargebackCategory;
import dev.vality.damsel.fraudbusters.ChargebackStatus;
import dev.vality.damsel.fraudbusters.PayerType;
import dev.vality.swag.fraudbusters.model.ChargebacksRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChargebacksRequestToChargebacksConverter implements Converter<ChargebacksRequest, List<Chargeback>> {

    private final CacheToInternalDtoConverter cacheToInternalDtoConverter;
    private final ClientInfoToInternalDtoConverter clientInfoToInternalDtoConverter;
    private final PaymentToolToInternalDtoConverter paymentToolToInternalDtoConverter;
    private final ProviderInfoToInternalDtoConverter providerInfoToInternalDtoConverter;
    private final ReferenceInfoToInternalDtoConverter referenceInfoToInternalDtoConverter;

    @Override
    public List<Chargeback> convert(ChargebacksRequest chargebacksRequest) {
        return chargebacksRequest.getChargebacks().stream()
                .map(this::mapChargeback)
                .collect(Collectors.toList());
    }

    private Chargeback mapChargeback(dev.vality.swag.fraudbusters.model.Chargeback item) {
        return new Chargeback()
                .setChargebackCode(item.getChargebackCode())
                .setCategory(ChargebackCategory.valueOf(item.getCategory().getValue()))
                .setCost(cacheToInternalDtoConverter.convert(item.getCashInfo()))
                .setId(item.getId())
                .setEventTime(item.getEventTime().toInstant().toString())
                .setClientInfo(clientInfoToInternalDtoConverter.convert(item.getPayerInfo()))
                .setPayerType(PayerType.valueOf(item.getPayerType().getValue()))
                .setPaymentId(item.getPaymentId())
                .setPaymentTool(paymentToolToInternalDtoConverter.convert(item.getBankCard()))
                .setProviderInfo(providerInfoToInternalDtoConverter.convert(item.getProviderInfo()))
                .setReferenceInfo(referenceInfoToInternalDtoConverter.convert(item.getMerchantInfo()))
                .setStatus(ChargebackStatus.valueOf(item.getStatus().getValue()));
    }

}
