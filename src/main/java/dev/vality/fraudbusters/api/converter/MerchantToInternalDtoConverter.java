package dev.vality.fraudbusters.api.converter;

import dev.vality.damsel.fraudbusters.MerchantInfo;
import dev.vality.damsel.fraudbusters.ReferenceInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MerchantToInternalDtoConverter
        implements Converter<dev.vality.swag.fraudbusters.model.Merchant, ReferenceInfo> {

    @Override
    public ReferenceInfo convert(dev.vality.swag.fraudbusters.model.Merchant merchant) {
        ReferenceInfo referenceInfo = new ReferenceInfo();
        referenceInfo.setMerchantInfo(new MerchantInfo()
                .setPartyId(merchant.getId())
                .setShopId(merchant.getShop().getId()));
        return referenceInfo;
    }

}
