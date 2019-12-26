package com.stackroute.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;


/**
 * This class implements the Tokenizer interface to tokenize a text file for a particular regex pattern.
 * Just put a regex pattern with which you want to process a file and go ahead
 * In case of Paragraph-processor, we are treating every line of the paragraph as a document and all lines  collectively is our document corpus
 */
 @Service
public class TokenizerImpl implements Tokenizer {

    private Pattern regex;

    public TokenizerImpl(Pattern regex) {
        this.regex = regex;
    }

    public TokenizerImpl() {
        this(Pattern.compile("\\b\\w\\w+"));
    }

    @Override
    public List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();
     //   StopwordRemover stopwordRemover = new StopwordRemover();
        Matcher matcher = regex.matcher(text);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }
}
