package cloud.deuterium.redis.countries.controller;

import cloud.deuterium.redis.countries.model.Country;
import cloud.deuterium.redis.countries.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


/**
 * Created by Milan Stojkovic 23-Nov-2022
 */

@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<Iterable<Country>> getAll(@RequestParam(required = false) String region,
                                                    @RequestParam(required = false) String continent) {

        Iterable<Country> all = countryService.findAll(region, continent);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/population")
    public ResponseEntity<Iterable<Country>> getAllByPopulationBetween(@RequestParam("min") int min,
                                                                       @RequestParam("max") int max) {
        Iterable<Country> all = countryService.findAllByPopulation(min, max);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/temperature")
    public ResponseEntity<Iterable<Country>> getByAverageTemperatureBetween(@RequestParam("min") int min,
                                                                            @RequestParam("max") int max) {
        Iterable<Country> all = countryService.findByAverageTemperature(min, max);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/geolocation")
    public ResponseEntity<Iterable<Country>> getNearCapitol(@RequestParam("lat") double lat,
                                                            @RequestParam("lon") double lon,
                                                            @RequestParam("d") double distance) {
        Iterable<Country> all = countryService.findByLocationNear(lat, lon, distance);
        return ResponseEntity.ok(all);
    }

    // search only by full word
    @GetMapping("/search")
    public ResponseEntity<Iterable<Country>> searchNameAndCapitol(@RequestParam("q") String q) {
        Iterable<Country> all = countryService.search(q);
        return ResponseEntity.ok(all);
    }

    // search only by full word - find one of multiple languages (OR condition)
    @GetMapping("/lang")
    public ResponseEntity<Iterable<Country>> searchByLanguages(@RequestParam("languages") Set<String> languages) {
        Iterable<Country> all = countryService.searchByLanguages(languages);
        return ResponseEntity.ok(all);
    }


    // CRUD
    @GetMapping("/{id}")
    public ResponseEntity<Country> getById(@PathVariable String id) {
        Country country = countryService.getById(id);
        return ResponseEntity.ok(country);
    }

    @PostMapping
    public ResponseEntity<Country> saveNewCountry(@RequestBody Country country) {
        Country savedCountry = countryService.saveNew(country);
        return ResponseEntity.ok(savedCountry);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable String id, @RequestBody Country country) {
        Country updatedCountry = countryService.update(id, country);
        return ResponseEntity.ok(updatedCountry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Country> deleteById(@PathVariable String id) {
        countryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
