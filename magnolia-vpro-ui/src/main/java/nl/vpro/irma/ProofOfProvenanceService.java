package nl.vpro.irma;

import java.util.List;

public interface ProofOfProvenanceService {


    List<String> getJavaScripts();

    String getBaseUrl();

    boolean isDebugging();
}
