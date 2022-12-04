package cloud.deuterium.redis.countries.repository;

import cloud.deuterium.redis.countries.model.Country;
import com.redis.om.spring.repository.RedisDocumentRepository;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Milan Stojkovic 13-Nov-2022
 */
public interface CountryRepository extends RedisDocumentRepository<Country, String> {
    Collection<Country> findAllByRegion(String region);
    Collection<Country> findAllByContinent(String continent);
    Collection<Country> findAllByContinentAndRegion(String continent, String region);
    Collection<Country> findAllByPopulationBetween(int min, int max);
    Collection<Country> findAllByYearlyAverageTemperatureBetween(int min, int max);
    Collection<Country> findAllByCapitalGeolocationNear(Point point, Distance distance);
    Collection<Country> searchByName(String q);
    Collection<Country> searchByCapital(String q);
    Collection<Country> findByLanguages(Set<String> languages);
}
