package com.ecfranalyzer.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.web.client.RestTemplateBuilder;

@Configuration
public class ECFRClient {

    private static final Logger logger = LoggerFactory.getLogger(ECFRClient.class);

    private final String baseUrl;
    private final String adminApiPath;
    private final String searchApiPath;
    private final String versionerApiPath;
    private final RestTemplate restTemplate;

    public ECFRClient(
            @Value("${ecfr.base.url}") String baseUrl,
            @Value("${ecfr.admin.api}") String adminApiPath,
            @Value("${ecfr.search.api}") String searchApiPath,
            @Value("${ecfr.versioner.api}") String versionerApiPath) {
        this.baseUrl = baseUrl;
        this.adminApiPath = adminApiPath;
        this.searchApiPath = searchApiPath;
        this.versionerApiPath = versionerApiPath;
        this.restTemplate = new RestTemplateBuilder().defaultHeader(HttpHeaders.ACCEPT, "application/json").rootUri(baseUrl).build();
    }

    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    public String getAdminApiUrl(String endpoint) {
        return baseUrl + adminApiPath + endpoint;
    }

    public String getSearchApiUrl(String endpoint) {
        return baseUrl + searchApiPath + endpoint;
    }

    public String getVersionerApiUrl(String endpoint) {
        return baseUrl + versionerApiPath + endpoint;
    }

    public <T> T fetchContent(String urlPath, Class<T> responseType) {
        int maxAttempts = 5;
        int attempt = 0;
        long backoff = 6000L;
        while (attempt < maxAttempts) {
            try {
                ResponseEntity<T> response = getRestTemplate()
                        .exchange(
                            urlPath,
                            HttpMethod.GET,
                            null,
                            responseType
                        );
                return response.getBody();
            } catch (HttpClientErrorException.TooManyRequests ex) {
                attempt++;
                logger.warn("Too many requests for url {}. Attempt {} of {}. Pausing for {} ms", urlPath, attempt, maxAttempts, backoff);
                try {
                    Thread.sleep(backoff);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return null;
                }
                backoff *= 2;
            } catch (RestClientException e) {
                logger.error("Error fetching content from: {}, error: {}", urlPath, e.getMessage());
                return null;
            }
        }
        logger.error("Failed to fetch content from {} after {} attempts", urlPath, maxAttempts);
        return null;
    }
} 