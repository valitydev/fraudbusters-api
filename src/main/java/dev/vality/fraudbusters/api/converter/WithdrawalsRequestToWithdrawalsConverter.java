package dev.vality.fraudbusters.api.converter;

import dev.vality.damsel.domain.CurrencyRef;
import dev.vality.damsel.fraudbusters.Account;
import dev.vality.damsel.fraudbusters.Resource;
import dev.vality.damsel.fraudbusters.Withdrawal;
import dev.vality.damsel.fraudbusters.WithdrawalStatus;
import dev.vality.swag.fraudbusters.model.WithdrawalsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WithdrawalsRequestToWithdrawalsConverter implements Converter<WithdrawalsRequest, List<Withdrawal>> {

    public static final String UNKNOWN = "UNKNOWN";
    private final CacheToInternalDtoConverter cacheToInternalDtoConverter;
    private final ErrorToInternalDtoConverter errorToInternalDtoConverter;
    private final ProviderInfoToInternalDtoConverter providerInfoToInternalDtoConverter;
    private final BankCardToInternalDtoConverter bankCardToInternalDtoConverter;

    @Override
    public List<Withdrawal> convert(WithdrawalsRequest request) {
        return request.getWithdrawals().stream()
                .map(this::mapWithdrawal)
                .collect(Collectors.toList());
    }

    private Withdrawal mapWithdrawal(dev.vality.swag.fraudbusters.model.Withdrawal item) {
        return new Withdrawal()
                .setCost(cacheToInternalDtoConverter.convert(item.getCashInfo()))
                .setId(item.getId())
                .setEventTime(item.getEventTime().toInstant().toString())
                .setProviderInfo(providerInfoToInternalDtoConverter.convert(item.getProviderInfo()))
                .setStatus(WithdrawalStatus.valueOf(item.getStatus().getValue()))
                .setError(errorToInternalDtoConverter.convert(item.getError()))
                .setAccount(new Account()
                        .setId(item.getAccount().getAccountId())
                        .setIdentity(UNKNOWN)
                        .setCurrency(new CurrencyRef(item.getAccount().getCurrency())))
                .setDestinationResource(Resource.bank_card(bankCardToInternalDtoConverter.convert(item.getBankCard())));
    }

}
