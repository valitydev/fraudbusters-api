package dev.vality.fraudbusters.api.converter;

import dev.vality.damsel.domain.*;
import dev.vality.damsel.proxy_inspector.InspectUserContext;
import dev.vality.damsel.proxy_inspector.Party;
import dev.vality.damsel.proxy_inspector.ShopContext;
import dev.vality.swag.fraudbusters.model.Contact;
import dev.vality.swag.fraudbusters.model.Customer;
import dev.vality.swag.fraudbusters.model.Merchant;
import dev.vality.swag.fraudbusters.model.UserInspectRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserInspectRequestToInspectUserContextConverter
        implements Converter<UserInspectRequest, InspectUserContext> {

    private static final String MOCK_UNUSED_DATA = "MOCK_UNUSED_DATA";

    @Override
    public InspectUserContext convert(UserInspectRequest request) {
        ContactInfo userInfo = buildContactInfo(request.getCustomer());
        List<ShopContext> shopContexts = Optional.ofNullable(request.getMerchants())
                .orElse(Collections.emptyList())
                .stream()
                .map(this::buildShopContext)
                .collect(Collectors.toList());
        return new InspectUserContext()
                .setUserInfo(userInfo)
                .setShopList(shopContexts);
    }

    private ContactInfo buildContactInfo(Customer customer) {
        Contact contact = Optional.ofNullable(customer)
                .map(Customer::getContact)
                .orElseGet(Contact::new);
        return new ContactInfo()
                .setEmail(contact.getEmail())
                .setPhoneNumber(contact.getPhone());
    }

    private ShopContext buildShopContext(Merchant merchant) {
        Party party = buildParty(merchant);
        dev.vality.damsel.proxy_inspector.Shop shop = buildShop(merchant);
        return new ShopContext()
                .setParty(party)
                .setShop(shop);
    }

    private Party buildParty(Merchant merchant) {
        return new Party()
                .setPartyRef(new PartyConfigRef()
                        .setId(merchant.getId()));
    }

    private dev.vality.damsel.proxy_inspector.Shop buildShop(Merchant merchant) {
        dev.vality.swag.fraudbusters.model.Shop shop = merchant.getShop();
        return new dev.vality.damsel.proxy_inspector.Shop()
                .setShopRef(new ShopConfigRef()
                        .setId(shop.getId()))
                .setName(shop.getName())
                .setCategory(new Category()
                        .setName(shop.getCategory())
                        .setDescription(MOCK_UNUSED_DATA))
                .setLocation(ShopLocation.url(shop.getLocation()));
    }
}
