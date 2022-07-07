package nl.vpro.irma;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
public abstract class IrmaRequest {

    @JsonProperty("@context")
    private final String context;

    protected IrmaRequest(String context) {
        this.context = context;
    }


}
