package nl.vpro.magnolia.ui.htmlembedvalidator;

import info.magnolia.i18nsystem.SimpleTranslator;

import org.junit.jupiter.api.Test;

import com.vaadin.data.ValidationResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @author Michiel Meeuwissen
 */
class HtmlEmbedValidatorTest {

    final HtmlEmbedValidatorDefinition definition = new HtmlEmbedValidatorDefinition();
    final SimpleTranslator simpleTranslator = mock(SimpleTranslator.class);
    {
        when(simpleTranslator.translate(anyString())).then(returnsFirstArg());
    }
    final HtmlEmbedValidator validator = new HtmlEmbedValidator(simpleTranslator, definition);

    @Test
    void emptyIsValid() {
        ValidationResult apply = validator.apply("", null);
        assertThat(apply.isError()).isFalse();
    }

    @Test
    void invalid() {
        ValidationResult apply = validator.apply("<a", null);
        assertThat(apply.isError()).isTrue();
        assertThat(apply.getErrorMessage()).isEqualTo("embed.invalid.html");
    }

    @Test
    void validBody() {
        ValidationResult apply = validator.apply("<body><h1>hello</h1></body>", null);
        assertThat(apply.isError()).isFalse();
    }

    @Test
    void invalidForm() {
        ValidationResult apply = validator.apply("<body><form action='http://nothttps.nl'></form></body>", null);
        assertThat(apply.isError()).isTrue();
        assertThat(apply.getErrorMessage()).isEqualTo("embed.invalid");

    }
}
