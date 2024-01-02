package com.learning.orderservice.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.orderservice.exception.ExternalClientException;
import com.learning.orderservice.model.common.ApiResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class CustomErrorDecoder implements ErrorDecoder {
    private static final Logger logger = LoggerFactory.getLogger(CustomErrorDecoder.class);

    @Override
    public Exception decode(String s, Response response) {
        ApiResponse apiResponse;
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            apiResponse = mapper.readValue(bodyIs, ApiResponse.class);
            logger.error("Error occurred in feign client: {}", apiResponse.errorList());
            throw new ExternalClientException(apiResponse.errorList().toString(), apiResponse.messageCode());
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
    }


}
