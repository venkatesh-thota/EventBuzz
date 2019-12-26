package com.stackroute.service;


import com.stackroute.domain.TokenTags;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EntityRecognition {

    TokenTags findEntityDomain(List<String> tokenList) throws IOException;

}
