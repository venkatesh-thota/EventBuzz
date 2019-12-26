package com.stackroute.service;


import org.apache.commons.lang.StringUtils;

import java.util.*;


public class NGramTfIdf {

    private NGramTfIdf() {
    }

    /**
     * Tokenize a set of documents and extract n-gram terms
     *
     * @param tokenizer document tokenizer
     * @param ns        n-gram orders
     * @param documents set of documents from which to extract terms
     * @return iterator over document terms, where each document's terms is an iterator over strings
     */
    public static Iterable<Collection<String>> ngramDocumentTerms(Tokenizer tokenizer, List<Integer> ns, Iterable<String> documents) {

        List<List<String>> tokenizedDocuments = new ArrayList<>();
        for (String document : documents) {
            List<String> tokens = tokenizer.tokenize(document);
            tokenizedDocuments.add(tokens);
        }
        List<Collection<String>> documentTerms = new ArrayList<>();
        for (List<String> tokenizedDocument : tokenizedDocuments) {
            Collection<String> terms = new ArrayList<>();
            for (int n : ns) {
                for (List<String> ngram : ngrams(n, tokenizedDocument)) {
                    String term = StringUtils.join(ngram, " ");
                    terms.add(term);
                }
            }
            documentTerms.add(terms);
        }
        return documentTerms;
    }

    /**
     * Tokenize a set of documents as alphanumeric words and extract n-gram terms
     *
     * @param ns        n-gram orders
     * @param documents set of documents from which to extract terms
     * @return iterator over document terms, where each document's terms is an iterator over strings
     */
    public static Iterable<Collection<String>> ngramDocumentTerms(List<Integer> ns, Iterable<String> documents) {
        return ngramDocumentTerms(new TokenizerImpl(), ns, documents);
    }

    private static List<List<String>> ngrams(int n, List<String> tokens) {
        List<List<String>> ngrams = new ArrayList<>();
        for (int i = 0; i < tokens.size() - n + 1; i++) {
            ngrams.add(tokens.subList(i, i + n));
        }
        return ngrams;
    }

    private static String termStatistics(Map<String, Double> stats) {

        List<Map.Entry<String, Double>> es = new ArrayList<>(stats.entrySet());

        Collections.sort(es, (o1, o2) ->
                ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue()));

        List<String> fields = new ArrayList<>();
        for (Map.Entry<String, Double> e : es) {
            fields.add(String.format("%s score : %6f", e.getKey(), e.getValue()));
        }

        return StringUtils.join(fields, "\n");
    }

}


