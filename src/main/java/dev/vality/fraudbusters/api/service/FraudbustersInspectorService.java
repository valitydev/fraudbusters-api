package dev.vality.fraudbusters.api.service;

import dev.vality.damsel.domain.RiskScore;
import dev.vality.damsel.proxy_inspector.*;
import dev.vality.fraudbusters.api.exceptions.RemoteInvocationException;
import dev.vality.swag.fraudbusters.model.Merchant;
import dev.vality.swag.fraudbusters.model.RiskScoreResult;
import dev.vality.swag.fraudbusters.model.UserInspectResult;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FraudbustersInspectorService {

    private final InspectorProxySrv.Iface proxyInspectorSrv;
    private final Converter<ShopContext, Merchant> shopContextToMerchantConverter;

    public dev.vality.swag.fraudbusters.model.RiskScore inspectPayment(Context context) {
        try {
            RiskScore riskScore = proxyInspectorSrv.inspectPayment(context);
            RiskScoreResult riskScoreResult = new RiskScoreResult();
            riskScoreResult.setResult(dev.vality.swag.fraudbusters.model.RiskScore.fromValue(riskScore.name()));
            return dev.vality.swag.fraudbusters.model.RiskScore.fromValue(riskScore.name());
        } catch (TException e) {
            throw new RemoteInvocationException(e);
        }
    }

    public UserInspectResult inspectUser(InspectUserContext context) {
        try {
            BlockedShops blockedShops = proxyInspectorSrv.inspectUser(context);
            UserInspectResult result = new UserInspectResult();
            if (blockedShops != null && blockedShops.getShopList() != null) {
                result.setBlockedMerchants(blockedShops.getShopList().stream()
                        .map(shopContextToMerchantConverter::convert)
                        .toList());
            }
            return result;
        } catch (TException e) {
            throw new RemoteInvocationException(e);
        }
    }

}
