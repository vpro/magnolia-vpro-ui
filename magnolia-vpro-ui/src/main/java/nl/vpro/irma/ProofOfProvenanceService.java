package nl.vpro.irma;

import java.net.URI;
import java.util.List;

public interface ProofOfProvenanceService {


    /**
     * The javascripts (from some CDN) needed for implementing {@link nl.vpro.magnolia.ui.irma.ProofOfProvenanceField}
     */
    List<URI> getJavaScripts();

    String getBaseUrl();

    boolean isDebugging();
}
