package nl.vpro.irma;

import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest
class ProofOfProvenanceServiceImplTest {

    @Test
    public void test(WireMockRuntimeInfo wmRuntimeInfo) {

        ProofOfProvenanceService proofOfProvenanceService = new ProofOfProvenanceServiceImpl("", "https://pop.waag.org/irma");

        // TODO: This is mock
        assertThat(proofOfProvenanceService.sign("bla bla")).isEqualTo("signed:bla bla");

    }

}
