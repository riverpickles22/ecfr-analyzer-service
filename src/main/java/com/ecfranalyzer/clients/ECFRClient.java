package com.ecfranalyzer.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.boot.web.client.RestTemplateBuilder;

@Configuration
public class ECFRClient {

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
        this.restTemplate = new RestTemplateBuilder()
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .rootUri(baseUrl)
                .build();
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
} 