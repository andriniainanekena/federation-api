package federation.agricole.api.service;

import federation.agricole.api.entity.CollectivityTransaction;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.repository.CollectivityRepository;
import federation.agricole.api.repository.CollectivityTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CollectivityTransactionService {
    CollectivityTransactionRepository transactionRepository;
    CollectivityRepository collectivityRepository;

    public CollectivityTransactionService(CollectivityTransactionRepository transactionRepository,
                                          CollectivityRepository collectivityRepository) {
        this.transactionRepository = transactionRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<CollectivityTransaction> getTransactions(String collectivityId,
                                                          LocalDate from, LocalDate to) {
        if (collectivityRepository.findById(collectivityId).isEmpty()) {
            throw new NotFoundException("Collectivity.id=" + collectivityId + " is not found");
        }
        if (from == null || to == null) {
            throw new BadRequestException("Query parameters 'from' and 'to' are required");
        }
        if (from.isAfter(to)) {
            throw new BadRequestException("'from' date must be before or equal to 'to' date");
        }
        return transactionRepository.findByCollectivityIdAndPeriod(collectivityId, from, to);
    }
}
