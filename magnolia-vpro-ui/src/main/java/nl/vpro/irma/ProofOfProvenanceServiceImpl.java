package nl.vpro.irma;

import javax.inject.Inject;
import javax.inject.Named;

public class ProofOfProvenanceServiceImpl implements ProofOfProvenanceService {

    private final String privateKey;

    private final String url;

    @Inject
    public ProofOfProvenanceServiceImpl(
        @Named("irma.key") String privateKey,
        @Named("irma.baseUrl") String url) {
        this.privateKey = privateKey;
        this.url = url;
    }

    @Override
    public String sign(String message) {
        IrmaDisclosureRequest request = IrmaDisclosureRequest.builder()
            .message(message)
            .build();
        // TODO: fire
        return "signed:" + message;
    }
}
