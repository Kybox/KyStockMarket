package fr.kybox.kystockmarket.web.controller;

import fr.kybox.kystockmarket.domain.entity.Society;
import fr.kybox.kystockmarket.repository.SocietyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class SocietyController {

    private final SocietyRepository societyRepository;

    @Autowired
    public SocietyController(SocietyRepository societyRepository) {
        this.societyRepository = societyRepository;
    }

    @GetMapping(value = "/companies")
    public String findSocieties(final Model model) {

        model.addAttribute("companies", societyRepository.findAll());
        return "index";
    }

    @GetMapping(value = "/society/all")
    public Flux<Society> findAll() {

        return societyRepository.findAll();
    }

    @GetMapping(value = "/society/{id}")
    public Mono<Society> findById(@PathVariable String id) {

        return societyRepository.findById(id);
    }

    @PostMapping(value = "society/add")
    public Mono<Society> save(@RequestBody Society society) {

        return societyRepository.save(society);
    }

    @DeleteMapping(value = "society/delete/{id}")
    public Mono<Void> delete(@PathVariable String id) {

        return societyRepository.deleteById(id);
    }

    @PutMapping(value = "society/update/{id}")
    public Mono<Society> update(@RequestBody Society society, @PathVariable String id) {

        society.setId(id);
        return societyRepository.save(society);
    }
}
