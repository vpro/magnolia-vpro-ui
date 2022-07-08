package nl.vpro.irma;

import org.junit.jupiter.api.Test;

class ProofOfProvenanceServiceImplTest {

    @Test
    public void test() {

        ProofOfProvenanceService proofOfProvenanceService = new ProofOfProvenanceServiceImpl(
            "",
            "https://pop.waag.org",
            "https://snotolf.waag.org/"
            );

        // TODO: This is mock
        //assertThat(proofOfProvenanceService.sign("bla bla")).isEqualTo("signed:bla bla");

    }

}
