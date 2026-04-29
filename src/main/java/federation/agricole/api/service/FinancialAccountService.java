package federation.agricole.api.service;

import federation.agricole.api.entity.FinancialAccount;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.repository.CollectivityRepository;
import federation.agricole.api.repository.FinancialAccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialAccountService {
    FinancialAccountRepository financialAccountRepository;
    CollectivityRepository collectivityRepository;

    public FinancialAccountService(FinancialAccountRepository financialAccountRepository,
                                   CollectivityRepository collectivityRepository) {
        this.financialAccountRepository = financialAccountRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<FinancialAccount> getFinancialAccounts(String collectivityId, LocalDate at) {
        if (collectivityRepository.findById(collectivityId).isEmpty()) {
            throw new NotFoundException("Collectivity.id=" + collectivityId + " is not found");
        }
        if (at != null) {
            return financialAccountRepository.findAllByCollectivityIdAt(collectivityId, at);
        }
        return financialAccountRepository.findAllByCollectivityId(collectivityId);
    }
}
