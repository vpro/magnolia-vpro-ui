/*
 * Copyright (C) 2018 All rights reserved
 * VPRO The Netherlands
 */
package nl.vpro.magnolia.ui.htmlembedvalidator;

import info.magnolia.i18nsystem.SimpleTranslator;
import lombok.extern.log4j.Log4j2;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;

/**
 * @author r.jansen
 */
@Log4j2
public class HtmlEmbedValidator extends AbstractValidator<String> {
    private final Map<String, List<String>> elementsAndAttributesToCheck = defaultAttributes();
    private final HtmlEmbedValidatorDefinition definition;
    private final SimpleTranslator simpleTranslator;

    public HtmlEmbedValidator(SimpleTranslator simpleTranslator, HtmlEmbedValidatorDefinition definition) {
        // We set the error message in de validation.
        super("");
        this.simpleTranslator = simpleTranslator;
        this.definition = definition;
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if (StringUtils.isEmpty(value)) {
            return toResult(value, true);
        }

        Parser parser = Parser.htmlParser().setTrackErrors(10);
        Document doc = Jsoup.parse(value, "", parser);
        if (!parser.getErrors().isEmpty()) {
            log.warn(parser.getErrors());
            return ValidationResult.error(simpleTranslator.translate(definition.getHtmlInvalidMessage()));
        }
        Element body = doc.body();
        if (body.getAllElements().size() <= 1) {// only the body element itself
            return ValidationResult.error(simpleTranslator.translate(definition.getHtmlInvalidMessage()));

        }
        for (Map.Entry<String, List<String>> elementAndAttributes : elementsAndAttributesToCheck.entrySet()) {
            Elements embeds = body.getElementsByTag(elementAndAttributes.getKey());
            for (Element embed : embeds) {
                for (String attr : elementAndAttributes.getValue()) {
                    String val = embed.attr(attr);
                    try {
                        URI uri = new URI(val);
                        if (uri.getScheme() != null && !uri.getScheme().equals("https")) {
                            return ValidationResult.error(simpleTranslator.translate(definition.getErrorMessage()));
                        }
                    } catch (URISyntaxException e) {
                        return ValidationResult.error(simpleTranslator.translate(definition.getUriInvalidMessage()));
                    }
                }
            }
        }
        return toResult(value, true);
    }

    private static Map<String, List<String>> defaultAttributes() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("applet", Collections.singletonList("codebase"));
        map.put("audio", Collections.singletonList("src"));
        map.put("body", Collections.singletonList("background"));
        map.put("button", Collections.singletonList("formaction"));
        map.put("command", Collections.singletonList("icon"));
        map.put("embed", Collections.singletonList("src"));
        map.put("form", Collections.singletonList("action"));
        map.put("frame", Collections.singletonList("src"));
        map.put("frameset", Collections.singletonList("src"));
        map.put("iframe", Collections.singletonList("src"));
        map.put("input", Arrays.asList("src", "formaction"));
        map.put("object", Arrays.asList("classid", "codebase", "data", "usemap", "data"));
        map.put("script", Collections.singletonList("src"));
        map.put("source", Collections.singletonList("src"));
        map.put("track", Collections.singletonList("src"));
        map.put("video", Arrays.asList("src", "poster"));
        return map;
    }
}
