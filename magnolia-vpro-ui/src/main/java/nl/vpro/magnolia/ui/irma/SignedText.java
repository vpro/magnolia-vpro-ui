package nl.vpro.magnolia.ui.irma;

import lombok.Data;

@Data
public class SignedText {
    String text;
    String signature;

    public SignedText(String text, String signature) {
        this.text = text;
        this.signature = signature;
    }
}
