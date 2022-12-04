package cloud.deuterium.redis.countries.controller;

import cloud.deuterium.redis.countries.model.Country;
import cloud.deuterium.redis.countries.service.CountryFluidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Created by Milan Stojkovic 04-Dec-2022
 */

@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/countries/fluid")
public class CountryFluidController {

    private final CountryFluidService fluidService;

    @GetMapping("/{id}")
    public ResponseEntity<Country> getById(@PathVariable String id) {
        Country country = fluidService.getById(id);
        return ResponseEntity.ok(country);
    }

    @GetMapping
    public ResponseEntity<Iterable<Country>> getAll(@RequestParam(required = false) String region,
                                                    @RequestParam(required = false) String continent) {

        Iterable<Country> all = fluidService.findAll(region, continent);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/population")
    public ResponseEntity<Iterable<Country>> getAllByPopulationBetween(@RequestParam("min") int min,
                                                                       @RequestParam("max") int max) {
        Iterable<Country> all = fluidService.findAllByPopulation(min, max);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/temperature")
    public ResponseEntity<Iterable<Country>> getByAverageTemperatureBetween(@RequestParam("min") int min,
                                                                            @RequestParam("max") int max) {
        Iterable<Country> all = fluidService.findByAverageTemperature(min, max);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/geolocation")
    public ResponseEntity<Iterable<Country>> getNearCapitol(@RequestParam("lat") double lat,
                                                            @RequestParam("lon") double lon,
                                                            @RequestParam("d") double distance) {
        Iterable<Country> all = fluidService.findByLocationNear(lat, lon, distance);
        return ResponseEntity.ok(all);
    }

    // search only by full word
    @GetMapping("/search")
    public ResponseEntity<Iterable<Country>> searchNameAndCapitol(@RequestParam("q") String q) {
        Iterable<Country> all = fluidService.search(q);
        return ResponseEntity.ok(all);
    }

    // search only by full word - find one of multiple languages (OR condition)
    @GetMapping("/lang")
    public ResponseEntity<Iterable<Country>> searchByLanguages(@RequestParam("languages") Set<String> languages) {
        Iterable<Country> all = fluidService.searchByLanguages(languages);
        return ResponseEntity.ok(all);
    }


}
