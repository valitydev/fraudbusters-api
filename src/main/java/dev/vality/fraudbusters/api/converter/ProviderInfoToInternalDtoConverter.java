package dev.vality.fraudbusters.api.converter;

import dev.vality.damsel.fraudbusters.ProviderInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProviderInfoToInternalDtoConverter
        implements Converter<dev.vality.swag.fraudbusters.model.ProviderInfo, ProviderInfo> {

    @Override
    public ProviderInfo convert(dev.vality.swag.fraudbusters.model.ProviderInfo providerInfo) {
        return new ProviderInfo()
                .setProviderId(providerInfo.getProviderId())
                .setCountry(providerInfo.getCountry())
                .setTerminalId(providerInfo.getTerminalId());
    }

}
