package federation.agricole.api.service;

import federation.agricole.api.dto.CollectivityLocalStatisticsRest;
import federation.agricole.api.dto.CollectivityOverallStatisticsRest;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.repository.CollectivityRepository;
import federation.agricole.api.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatisticsService {
    CollectivityRepository collectivityRepository;
    StatisticsRepository statisticsRepository;

    public StatisticsService(CollectivityRepository collectivityRepository,
                             StatisticsRepository statisticsRepository) {
        this.collectivityRepository = collectivityRepository;
        this.statisticsRepository = statisticsRepository;
    }

    // GET /collectivites/{id}/statistics
    public List<CollectivityLocalStatisticsRest> getLocalStatistics(
            String collectivityId, LocalDate from, LocalDate to) {

        if (collectivityRepository.findById(collectivityId).isEmpty()) {
            throw new NotFoundException("Collectivity.id=" + collectivityId + " is not found");
        }
        validatePeriod(from, to);

        return statisticsRepository.getLocalStatistics(collectivityId, from, to);
    }
    // GET /collectivites/statistics
    public List<CollectivityOverallStatisticsRest> getOverallStatistics(
            LocalDate from, LocalDate to) {
        validatePeriod(from, to);
        return statisticsRepository.getOverallStatistics(from, to);
    }

    private void validatePeriod(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new BadRequestException("Query parameters 'from' and 'to' are required");
        }
        if (from.isAfter(to)) {
            throw new BadRequestException("'from' must be before or equal to 'to'");
        }
    }
}