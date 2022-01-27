package dev.vality.fraudbusters.api.converter;

import dev.vality.damsel.fraudbusters.ClientInfo;
import dev.vality.fraudbusters.api.utils.ApiBeanGenerator;
import org.apache.thrift.TException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientInfoToInternalDtoConverterTest {

    @Test
    void convert() throws TException {
        ClientInfoToInternalDtoConverter clientInfoToInternalDtoConverter = new ClientInfoToInternalDtoConverter();

        ClientInfo clientInfo = clientInfoToInternalDtoConverter.convert(ApiBeanGenerator.initUserInfo());

        assertNotNull(clientInfo);
        clientInfo.validate();

        assertEquals(ApiBeanGenerator.EMAIL, clientInfo.getEmail());
        assertEquals(ApiBeanGenerator.FINGERPRONT, clientInfo.getFingerprint());
        assertEquals(ApiBeanGenerator.IP, clientInfo.getIp());
    }
}
