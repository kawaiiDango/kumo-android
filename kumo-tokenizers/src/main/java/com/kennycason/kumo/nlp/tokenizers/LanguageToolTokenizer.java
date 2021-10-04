package com.kennycason.kumo.nlp.tokenizers;

import org.languagetool.Language;
import org.languagetool.tokenizers.Tokenizer;

import java.util.ArrayList;
import java.util.List;

class LanguageToolTokenizer implements Tokenizer {

    private final Language language;

    public LanguageToolTokenizer(final Language language) {
        this.language = language;
    }

    public List<String> tokenize(final String sentence) {
        final Tokenizer tokenizer = language.getWordTokenizer();
        final List<String> rawTokens = tokenizer.tokenize(sentence);
        final List<String> tokens = new ArrayList<>();
        for (final String rawToken : rawTokens) {   // parse parts-of-speech tags away (政府/n, 依照/p, 法律/n, 行/ng, 使/v, 执法/vn)
            final String token = parseToken(rawToken);
            if (token.trim().isEmpty()) { continue; }
            tokens.add(token);
        }
        return tokens;
    }

    private static String parseToken(final String rawToken) {
        if (rawToken.contains("/")) {
            return rawToken.substring(0, rawToken.indexOf('/')).trim();
        }
        return rawToken.trim();
    }

}
