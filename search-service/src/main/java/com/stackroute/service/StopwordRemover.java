package com.stackroute.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StopwordRemover {


    private static Logger logger;
    private String stopWords = "stopwords.txt";

    /*
    Function to read stopwords from stopwords.txt. returns a list containing all the stopwords.
     */

    public static List<String> readStopWords(String stopWordsFilename) {
        List<String> stopWords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(stopWordsFilename))) {
            String line = br.readLine();


            while (line != null) {
                stopWords.add(line);
                line = br.readLine();
            }

        } catch (Exception e) {
            logger.info(e.getMessage());
        }


        return stopWords;
    }

    /*
    Function to remove stopwords from a document i.e. a list of tokenized and lemmatized words. Returns a new list
    free of stopwords
     */

    public List<String> removeStopwords(List<String> terms) {
        List<String> resultingdoc = new ArrayList<>();

        List<String> stopwords = readStopWords(stopWords);


        for (String term : terms) {
            boolean flag = true;
            for (String stopword : stopwords) {
                if (term.equalsIgnoreCase(stopword)) {
                    flag = false;
                    break;
                }
            }
            if (flag)
                resultingdoc.add(term);
        }
        return resultingdoc;
    }

}
