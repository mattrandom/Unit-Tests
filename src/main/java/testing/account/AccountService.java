package testing.account;

import java.util.List;
import java.util.stream.Collectors;

public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllActiveAccounts() {
        return accountRepository.getAllAccounts()
                .stream()
                .filter(Account::isActive)
                .collect(Collectors.toList());
    }
}
