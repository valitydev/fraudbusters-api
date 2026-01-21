package dev.vality.fraudbusters.api.converter;

import dev.vality.damsel.proxy_inspector.ShopContext;
import dev.vality.swag.fraudbusters.model.Merchant;
import dev.vality.swag.fraudbusters.model.Shop;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ShopContextToMerchantConverter implements Converter<ShopContext, Merchant> {

    @Override
    public Merchant convert(ShopContext shopContext) {
        var shop = shopContext.getShop();
        Shop swaggerShop = new Shop();
        if (shop != null) {
            swaggerShop
                    .id(shop.getShopRef() != null ? shop.getShopRef().getId() : null)
                    .name(shop.getName())
                    .category(shop.getCategory() != null ? shop.getCategory().getName() : null)
                    .location(shop.getLocation() != null ? shop.getLocation().getUrl() : null);
        }
        var party = shopContext.getParty();
        return new Merchant()
                .id(party != null && party.getPartyRef() != null ? party.getPartyRef().getId() : null)
                .shop(swaggerShop);
    }
}

