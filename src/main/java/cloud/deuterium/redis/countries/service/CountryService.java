package cloud.deuterium.redis.countries.service;

import cloud.deuterium.redis.countries.model.Country;
import cloud.deuterium.redis.countries.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

/**
 * Created by Milan Stojkovic 01-Dec-2022
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public Iterable<Country> findAll(String region, String continent) {

        if (isNull(region) && isNull(continent)) {
            return countryRepository.findAll();
        }

        if (isNull(continent)) {
            return countryRepository.findAllByRegion(region);
        }

        if (isNull(region)) {
            return countryRepository.findAllByContinent(continent);
        }

        return countryRepository.findAllByContinentAndRegion(continent, region);
    }

    public Iterable<Country> findAllByPopulation(int min, int max) {
        return countryRepository.findAllByPopulationBetween(min, max);
    }

    public Iterable<Country> findByAverageTemperature(int min, int max) {
        return countryRepository.findAllByYearlyAverageTemperatureBetween(min, max);
    }

    public Iterable<Country> findByLocationNear(double lat, double lon, double distance) {
        return countryRepository.findAllByCapitalGeolocationNear(new Point(lat, lon), new Distance(distance, Metrics.KILOMETERS));
    }

    public Iterable<Country> search(String q) {
        Collection<Country> byName = countryRepository.searchByName(q);
        Collection<Country> byCapital = countryRepository.searchByCapital(q);
        HashSet<Country> countries = new HashSet<>(byName);
        countries.addAll(byCapital);
        return countries;
    }

    public Iterable<Country> searchByLanguages(Set<String> languages) {
        return countryRepository.findByLanguages(languages);
    }

    public Country getById(String id) {
        return countryRepository.findById(id)
                .orElse(new Country());
    }

    public Country saveNew(Country country) {
        return countryRepository.save(country);
    }

    public Country update(String id, Country country) {
        return countryRepository.findById(id)
                .map(c -> countryRepository.save(country))
                .orElse(new Country());
    }

    public void deleteById(String id) {
        countryRepository.findById(id)
                .ifPresent(countryRepository::delete);
    }

}
