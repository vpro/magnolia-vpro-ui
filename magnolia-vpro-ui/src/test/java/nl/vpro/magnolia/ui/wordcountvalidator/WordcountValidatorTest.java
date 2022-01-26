package nl.vpro.magnolia.ui.wordcountvalidator;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author rico
 */
public class WordcountValidatorTest {

    // Atze zegt: ‘De dj shortlist'
    String example1="<p>Komend weekend vindt in Amsterdam Sonic Acts plaats, een vooruitstrevend festival met kunst en elektronische muziek. Daar is ook DJ Earl, een van de toonaangevende artiesten in het footwork genre. Footwork? Da&rsquo;s een super high energy variant op house.&nbsp;<br /><br /><strong>De classic waarmee je footwork het best introduceert: DJ Rashad - Ghost</strong><br />&lsquo;De perfecte battle track, een instant classic toen ie uitkwam. Het is een track met veel bass en knisperige snares en catchy loops. Een track die het energieniveau hoog houdt, ik draai hem nog vaak.<br /><br /><strong>De track die jou van klassieke muziek naar de footwork trok: Traxman - Kill Me In The Cirle</strong><br />&lsquo;Voor het eerst begreep ik hoe je verhalen kunt vertellen met samples. Halverwege zit een break waarna de drumprogrammering totaal verandert.&rsquo;<br /><br /><strong>Een recente track waar je gek op bent: DJ Spinn - On Deck</strong><br />&lsquo;Zoals we van Spinn gewend zijn high energy, maar toch iets meer &lsquo;vibey&rsquo;, met een langzame opbouw. Geweldige track voor als het je geluid fris wil houden.&rsquo;<br /><br /><strong>Een track die je dit weekend zeker draait: DJ Earl &amp; DJ Manny - 144</strong><br />&lsquo;Een recente track. Hij is &lsquo;moody&rsquo;, maar de respons op de dansvloer is geweldig. Ik heb hem de laatste keer dat ik in Nederland was nog niet gedraaid, dus ik ben benieuwd.&rsquo;<br /><br /><strong>De plaat die jij op zondagochtend draait om tot rust te komen: Ab-Soul - The Book Of Soul</strong><br />&lsquo;Het album Control System van Ab-Soul draai ik minstens eens per week, altijd op zondag. Ab-Soul is een intelligente rapper uit de TDE stal.&rsquo;<br /><br /><strong>Een&nbsp;eigen productie die je vaak&nbsp;draait: DJ Earl - Too Much Smoke In The Air</strong><br />&lsquo;Ja, ik rook graag, dat mag iedereen weten, maar deze track was ge&iuml;nspireerd op die keer dat ik op een festival in Polen optrad en een van de dansers een allergische reactie kreeg door de rookmachines.&#39;</p>";

    @Test
    public void testCount() {
        WordcountValidatorDefinition definition = new WordcountValidatorDefinition();
        definition.setParseHtml(true);
        definition.setWordcount(304);
        definition.setErrorMessage("some message");
        WordcountValidator validator = new WordcountValidator(definition);
        boolean result = validator.isValidValue(example1);
        assertThat(result).isTrue();

        definition.setWordcount(303);
        result = validator.isValidValue(example1);
        assertThat(result).isFalse();
    }

    @Test
    public void testCountHtml() {
        WordcountValidatorDefinition definition = new WordcountValidatorDefinition();
        definition.setParseHtml(false);
        definition.setWordcount(358);
        definition.setErrorMessage("some message");
        WordcountValidator validator = new WordcountValidator(definition);
        boolean result = validator.isValidValue(example1);
        assertThat(result).isTrue();

        definition.setWordcount(357);
        result = validator.isValidValue(example1);
        assertThat(result).isFalse();
    }
}
