package pe.edu.vallegrande.vgmscapacity.application.service;

import org.springframework.stereotype.Service;

import pe.edu.vallegrande.vgmscapacity.domain.dto.CapacityDto;
import pe.edu.vallegrande.vgmscapacity.domain.dto.CompentencyDto;
import pe.edu.vallegrande.vgmscapacity.domain.model.Capacity;
import pe.edu.vallegrande.vgmscapacity.domain.repository.CapacityRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CapacityService {
    private final CapacityRepository capacityRepository;
    private final ExternalService competencyService;

    public CapacityService(CapacityRepository capacityRepository, ExternalService competencyService) {
        this.capacityRepository = capacityRepository;
        this.competencyService = competencyService;
    }

    public Flux<CapacityDto> findByStatus(String status) {
        return capacityRepository.findByStatus(status)
                .flatMap(this::convertToDto)
                .collectList()
                .flatMapMany(Flux::fromIterable);
    }

    public Mono<Capacity> findById(String capacityId) {
        return capacityRepository.findById(capacityId);
    }

    public Mono<Capacity> save(Capacity capacity) {
        capacity.setStatus("A");
        return capacityRepository.save(capacity);
    }

    public  Mono<Capacity> update(String id, Capacity capacity) {
        return capacityRepository.findById(id)
                .flatMap(sp -> {
                    sp.setName(capacity.getName());
                    sp.setCompetencyId(capacity.getCompetencyId());
                    sp.setStatus(("A"));
                    return capacityRepository.save(sp);
                });
    }

    public  Mono<Capacity> changeStatus(String id, String status) {
        return capacityRepository.findById(id)
                .flatMap(sp -> {
                    sp.setStatus(status);
                    return capacityRepository.save(sp);
                });
    }

    public Flux<CompentencyDto> findAll() {
        return competencyService.findByAll();
    }

    private Mono<CapacityDto> convertToDto(Capacity capacity) {
        CapacityDto dto = new CapacityDto();
        dto.setCapacityId(capacity.getCapacityId());
        dto.setName(capacity.getName());
        dto.setStatus(capacity.getStatus());

        Mono<CompentencyDto> competencyMono = competencyService.findCompetencyById(capacity.getCompetencyId());

        return Mono.zip(competencyMono, Mono.just(capacity.getCapacityId())
                , (competency, capacityId) -> {
                    dto.setCompetencyId(competency);
                    dto.setCapacityId(capacityId);
                    return dto;
                });
    }

}
