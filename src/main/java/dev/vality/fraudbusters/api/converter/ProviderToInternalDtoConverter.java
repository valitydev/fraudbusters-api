package dev.vality.fraudbusters.api.converter;

import dev.vality.damsel.fraudbusters.ProviderInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProviderToInternalDtoConverter
        implements Converter<dev.vality.swag.fraudbusters.model.Provider, ProviderInfo> {

    @Override
    public ProviderInfo convert(dev.vality.swag.fraudbusters.model.Provider provider) {
        return new ProviderInfo()
                .setProviderId(provider.getId())
                .setCountry(provider.getCountry())
                .setTerminalId(provider.getTerminalId());
    }

}
