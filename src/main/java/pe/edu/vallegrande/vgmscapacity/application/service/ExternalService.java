package pe.edu.vallegrande.vgmscapacity.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import pe.edu.vallegrande.vgmscapacity.domain.dto.CompentencyDto;
import reactor.core.publisher.*;

@Slf4j
@Service
public class ExternalService {
    private final WebClient.Builder webClientBuilder;

    @Value("${services.competency.url}")
    private String competencyUrl;

    public ExternalService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<CompentencyDto> findCompetencyById(String competencyId) {
        return fetchData(competencyUrl + "/", 
                competencyId, CompentencyDto.class);
    }

    public Flux<CompentencyDto> findByAll() {
        return fetchDataList(competencyUrl + "/list/active", 
                CompentencyDto.class);
    }

    private <T> Mono<T> fetchData(String baseUrl, String id, Class<T> responseType) {
        return webClientBuilder.build()
                .get()
                .uri(baseUrl + id)
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(e -> {
                    log.error("Error fetching data: ", e);
                    return Mono.empty();
                });
    }

    private <T> Flux<T> fetchDataList(String baseUrl, Class<T> responseType) {
        return webClientBuilder.build()
                .get()
                .uri(baseUrl) 
                .retrieve()
                .bodyToFlux(responseType) 
                .onErrorResume(e -> {
                    log.error("Error fetching data: ", e);
                    return Flux.empty(); 
                });
    }

}
