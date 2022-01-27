package dev.vality.fraudbusters.api.service;

import dev.vality.damsel.domain.RiskScore;
import dev.vality.damsel.proxy_inspector.Context;
import dev.vality.damsel.proxy_inspector.InspectorProxySrv;
import dev.vality.fraudbusters.api.exceptions.RemoteInvocationException;
import dev.vality.swag.fraudbusters.model.RiskScoreResult;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FraudbustersInspectorService {

    private final InspectorProxySrv.Iface proxyInspectorSrv;

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
}
