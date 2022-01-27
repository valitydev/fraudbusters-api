package dev.vality.fraudbusters.api.converter;

import dev.vality.damsel.fraudbusters.MerchantInfo;
import dev.vality.damsel.fraudbusters.ReferenceInfo;
import dev.vality.fraudbusters.api.utils.ApiBeanGenerator;
import org.apache.thrift.TException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReferenceInfoToInternalDtoConverterTest {

    @Test
    void convert() throws TException {
        ReferenceInfoToInternalDtoConverter converter = new ReferenceInfoToInternalDtoConverter();

        ReferenceInfo referenceInfo = converter.convert(ApiBeanGenerator.initMerachantInfo());

        assertNotNull(referenceInfo);
        MerchantInfo merchantInfo = referenceInfo.getMerchantInfo();
        merchantInfo.validate();

        assertEquals(ApiBeanGenerator.PARTY_ID, merchantInfo.getPartyId());
        assertEquals(ApiBeanGenerator.SHOP_ID, merchantInfo.getShopId());
    }
}
