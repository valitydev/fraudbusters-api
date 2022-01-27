package dev.vality.fraudbusters.api.converter;

import dev.vality.damsel.fraudbusters.ClientInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClientInfoToInternalDtoConverter
        implements Converter<dev.vality.swag.fraudbusters.model.UserInfo, ClientInfo> {

    @Override
    public ClientInfo convert(dev.vality.swag.fraudbusters.model.UserInfo userInfo) {
        return new ClientInfo()
                .setFingerprint(userInfo.getFingerprint())
                .setEmail(userInfo.getEmail())
                .setPhone(userInfo.getPhone())
                .setIp(userInfo.getIp());
    }

}
