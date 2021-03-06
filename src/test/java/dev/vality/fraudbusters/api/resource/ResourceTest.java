package dev.vality.fraudbusters.api.resource;

import dev.vality.damsel.fraudbusters.PaymentServiceSrv;
import dev.vality.damsel.proxy_inspector.InspectorProxySrv;
import dev.vality.fraudbusters.api.service.FraudbustersDataService;
import dev.vality.fraudbusters.api.service.FraudbustersInspectorService;
import dev.vality.fraudbusters.api.utils.ApiBeanGenerator;
import dev.vality.swag.fraudbusters.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResourceTest {

    public static final String CHARGEBACKS = "/chargebacks";
    public static final String REFUNDS = "/refunds";
    public static final String PAYMENTS = "/payments";
    public static final String FRAUD_PAYMENTS = "/fraud-payments";
    public static final String WITHDRAWALS = "/withdrawals";
    public static final String INSPECTOR = "/inspect-payment";
    public static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    int serverPort;

    @MockBean
    FraudbustersDataService fraudbustersDataService;
    @MockBean
    FraudbustersInspectorService fraudbustersInspectorService;
    @MockBean
    PaymentServiceSrv.Iface paymentServiceSrv;
    @MockBean
    InspectorProxySrv.Iface proxyInspectorSrv;

    RestTemplate restTemplate = new RestTemplate();

    @Test
    void insertChargebacksTest() {
        ChargebacksRequest request = new ChargebacksRequest();
        restTemplate.postForEntity(initUrl(CHARGEBACKS), request, Void.class);
        verify(fraudbustersDataService, times(0)).insertChargebacks(any());

        Chargeback chargeback = new Chargeback();
        request.setChargebacks(List.of(chargeback));
        assertThrows(HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(initUrl(CHARGEBACKS), request, Void.class)
        );

        chargeback = ApiBeanGenerator.initChargeback();
        ChargebacksRequest requestFull = new ChargebacksRequest()
                .chargebacks(List.of(chargeback));
        restTemplate.postForEntity(initUrl(CHARGEBACKS), requestFull, Void.class);
        verify(fraudbustersDataService, times(1)).insertChargebacks(any());
    }

    @Test
    void insertRefundsTest() {
        RefundsRequest request = new RefundsRequest();
        restTemplate.postForEntity(initUrl(REFUNDS), request, Void.class);
        verify(fraudbustersDataService, times(0)).insertRefunds(any());

        request.refunds(List.of(new Refund()));
        assertThrows(HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(initUrl(REFUNDS), request, Void.class)
        );

        request.refunds(List.of(ApiBeanGenerator.initRefund()));
        restTemplate.postForEntity(initUrl(REFUNDS), request, Void.class);
        verify(fraudbustersDataService, times(1)).insertRefunds(any());
    }

    @Test
    void insertPaymentsTest() {
        PaymentsChangesRequest request = new PaymentsChangesRequest();
        restTemplate.postForEntity(initUrl(PAYMENTS), request, Void.class);
        verify(fraudbustersDataService, times(0)).insertPaymentsChanges(any());

        request.paymentsChanges(List.of(new PaymentChange()));
        assertThrows(HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(initUrl(PAYMENTS), request, Void.class)
        );

        request.paymentsChanges(List.of(ApiBeanGenerator.initPaymentChange()));
        restTemplate.postForEntity(initUrl(PAYMENTS), request, Void.class);
        verify(fraudbustersDataService, times(1)).insertPaymentsChanges(any());
    }

    @Test
    void insertFraudPaymentsTest() {
        FraudPaymentsRequest request = new FraudPaymentsRequest();
        restTemplate.postForEntity(initUrl(FRAUD_PAYMENTS), request, Void.class);
        verify(fraudbustersDataService, times(0)).insertFraudPayments(any());

        request.fraudPayments(List.of(new FraudPayment()));
        assertThrows(HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(initUrl(FRAUD_PAYMENTS), request, Void.class)
        );

        request.fraudPayments(List.of(ApiBeanGenerator.initFraudPayment()));
        restTemplate.postForEntity(initUrl(FRAUD_PAYMENTS), request, Void.class);
        verify(fraudbustersDataService, times(1)).insertFraudPayments(any());
    }

    @Test
    void insertWithdrawalsTest() {
        WithdrawalsRequest request = new WithdrawalsRequest();
        restTemplate.postForEntity(initUrl(WITHDRAWALS), request, Void.class);
        verify(fraudbustersDataService, times(0)).insertWithdrawals(any());

        request.withdrawals(List.of(new Withdrawal()));
        assertThrows(HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(initUrl(WITHDRAWALS), request, Void.class)
        );

        request.withdrawals(List.of(ApiBeanGenerator.initWithdrawal()));
        restTemplate.postForEntity(initUrl(WITHDRAWALS), request, Void.class);
        verify(fraudbustersDataService, times(1)).insertWithdrawals(any());
    }

    @Test
    void inspectPaymentsTest() {
        when(fraudbustersInspectorService.inspectPayment(any())).thenReturn(RiskScore.FATAL);

        PaymentInspectRequest request = new PaymentInspectRequest();
        assertThrows(HttpClientErrorException.BadRequest.class, () ->
                restTemplate.postForEntity(initUrl(INSPECTOR), request, RiskScoreResult.class));

        request.payment(new Payment());
        assertThrows(HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(initUrl(INSPECTOR), request, RiskScoreResult.class)
        );

        request.payment(ApiBeanGenerator.initPayment());
        restTemplate.postForEntity(initUrl(INSPECTOR), request, RiskScoreResult.class);
        verify(fraudbustersInspectorService, times(1)).inspectPayment(any());
    }

    private String initUrl(String fraudbustersRefunds) {
        return BASE_URL + serverPort + fraudbustersRefunds;
    }

}
