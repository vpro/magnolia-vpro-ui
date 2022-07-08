package nl.vpro.irma;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class ProofOfProvenanceServiceImpl implements ProofOfProvenanceService {

    private final String privateKey;

    @Getter
    private final String baseUrl;

    private final String cdnBaseUrl;


    @Inject
    public ProofOfProvenanceServiceImpl(
        @Named("irma.key") String privateKey,
        @Named("irma.baseUrl") String baseUrl,
        @Named("irma.cdn.baseUrl") String cdnBaseUrl
        ) {
        this.privateKey = privateKey;
        this.baseUrl = baseUrl;
        this.cdnBaseUrl = cdnBaseUrl;
    }

    //@Override
    public String sign(String message) {

        return "signed:" + message;
    }

    @Override
    public List<String> getJavaScripts() {
        String popBase = cdnBaseUrl + "pop/";
        return Arrays.asList(
            popBase + "irma.js"
            //popBase + "blockhash.js",
            //popBase + "zlib.js",
            //popBase + "pop.js"
        );
    }
}
