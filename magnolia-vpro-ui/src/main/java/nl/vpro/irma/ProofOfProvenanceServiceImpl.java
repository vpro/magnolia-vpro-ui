package nl.vpro.irma;

import lombok.Getter;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class ProofOfProvenanceServiceImpl implements ProofOfProvenanceService {

    private final String privateKey;

    @Getter
    private final String baseUrl;

    private final String cdnBaseUrl;

    @Getter
    private final boolean debugging;


    @Inject
    public ProofOfProvenanceServiceImpl(
        @Named("irma.key") String privateKey,
        @Named("irma.baseUrl") String baseUrl,
        @Named("irma.cdn.baseUrl") String cdnBaseUrl,
        @Named("irma.debugging") boolean debugging

        ) {
        this.privateKey = privateKey;
        this.baseUrl = baseUrl;
        this.cdnBaseUrl = cdnBaseUrl;
        this.debugging = debugging;
    }

    //@Override
    public String sign(String message) {

        return "signed:" + message;
    }

    @Override
    public List<URI> getJavaScripts() {
        String popBase = cdnBaseUrl + "pop/";
        return Arrays.asList(
            URI.create(popBase + "irma.js"),
            URI.create(popBase + "pop.js")
            //popBase + "blockhash.js",
            //popBase + "zlib.js",
            //popBase + "pop.js"
        );
    }
}
