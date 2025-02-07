package pe.edu.vallegrande.vgmscapacity.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.vallegrande.vgmscapacity.application.service.CapacityService;
import pe.edu.vallegrande.vgmscapacity.domain.dto.CapacityDto;
import pe.edu.vallegrande.vgmscapacity.domain.dto.CompentencyDto;
import pe.edu.vallegrande.vgmscapacity.domain.model.Capacity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/management/${api.version}/capacity")
public class CapacityController {

    @Autowired
    private  CapacityService capacityService;

    @GetMapping("/list/{id}")
    public Mono<Capacity> findById(@PathVariable String id) {
        return capacityService.findById(id);
    }

    @GetMapping("/list/active")
    public ResponseEntity<Flux<CapacityDto>> listActive()  {
        return ResponseEntity.ok(capacityService.findByStatus("A"));
    }

    @GetMapping("/list/inactive")
    public ResponseEntity<Flux<CapacityDto>> listInactive()  {
        return ResponseEntity.ok(capacityService.findByStatus("I"));
    }

    @GetMapping("/competency")
    public Flux<CompentencyDto> listCompetecy() {
        return capacityService.findAll();
    }

    @PostMapping("/save")
    public Mono<Capacity> create(@RequestBody Capacity capacity) {
        return capacityService.save(capacity);
    }

    @PutMapping("/update/{id}")
    public Mono<Capacity> update(@PathVariable String id, @RequestBody Capacity capacity) {
        return capacityService.update(id, capacity);
    }

    @PutMapping("/activate/{id}")
    public Mono<Capacity> activate(@PathVariable String id) {
        return capacityService.changeStatus(id, "A");
    }

    @PutMapping("/inactive/{id}")
    public Mono<Capacity> deactivate(@PathVariable String id) {
        return capacityService.changeStatus(id, "I");
    }


}
