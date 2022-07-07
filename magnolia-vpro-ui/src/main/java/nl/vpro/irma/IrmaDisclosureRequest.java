package nl.vpro.irma;


import lombok.Getter;

@Getter
public class IrmaDisclosureRequest extends IrmaRequest {


    private final String[][] disclose;

    private final String message;

    @lombok.Builder
    protected IrmaDisclosureRequest(String context, String[][] disclose, String message) {
        super(context);
        this.disclose = disclose;
        this.message = message;
    }
}
