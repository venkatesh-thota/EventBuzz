package com.stackroute.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NGramTokenizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NGramTokenizer.class);

    /**
     * This function performs N-Gram analysis on a String.We begin with setting how many N-grams we want to deal with.
     * For example,if a combination of 5 words makes sense while individual words don't,then we might wan't to set N-grams to 5.
     * If N=3,then we will be able to make sense of "United States Of America" if  we encounter this in a text as these
     * individual words don't make much sense.
     *
     * @param paragraph: inout String paragraph
     * @return: Map of {key,value} pairs where key is the keyword and value is the score of the keyword.
     */
    public static List<String> performNGramAnalysis(String paragraph) {

        List<Integer> ns = Arrays.asList(1, 2, 3);

        TfIdf.Normalization normalization = TfIdf.Normalization.COSINE;
        boolean smooth = true;
        boolean noAddOne = false;

        List<String> text = Arrays.asList(paragraph.trim().split("\\.|\\n"));

        Iterable<Collection<String>> documents = NGramTfIdf.ngramDocumentTerms(ns, text);

        Iterable<Map<String, Double>> tfs = TfIdf.tfs(documents);
        Map<String, Double> idf = TfIdf.idfFromTfs(tfs, smooth, !noAddOne);
        List<String> keywords = new ArrayList();
        for (Map<String, Double> tf : tfs) {
            Map<String, Double> tfIdf = TfIdf.tfIdf(tf, idf, normalization);
            for (Map.Entry<String, Double> entry : tfIdf.entrySet()) {
                keywords.add(entry.getKey());
            }
        }
        return keywords;
    }

}
