package pe.edu.vallegrande.vgmscapacity.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import pe.edu.vallegrande.vgmscapacity.domain.model.Capacity;
import reactor.core.publisher.Flux;

@Repository
public interface CapacityRepository extends ReactiveMongoRepository<Capacity, String>{

    Flux<Capacity> findByStatus(String status);

}
