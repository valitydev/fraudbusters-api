package dev.vality.fraudbusters.api.converter;

import dev.vality.damsel.fraudbusters.MerchantInfo;
import dev.vality.damsel.fraudbusters.ReferenceInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReferenceInfoToInternalDtoConverter
        implements Converter<dev.vality.swag.fraudbusters.model.MerchantInfo, ReferenceInfo> {

    @Override
    public ReferenceInfo convert(dev.vality.swag.fraudbusters.model.MerchantInfo merchantInfo) {
        ReferenceInfo referenceInfo = new ReferenceInfo();
        referenceInfo.setMerchantInfo(new MerchantInfo()
                .setPartyId(merchantInfo.getPartyId())
                .setShopId(merchantInfo.getShopId()));
        return referenceInfo;
    }

}
