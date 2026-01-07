package repository;

import domain.Account;
import domain.Customer;

import java.util.*;

public class AccountRepository {

//    public AccountRepository(AccountRepository accountRepository) {}
    private final Map<String, Account> accountBynumber=new HashMap<>();
    public void save(Account account){
        accountBynumber.put(account.getAccountNumber(),account);
    }

    public List<Account> findAll() {
        return new ArrayList<>(accountBynumber.values());
    }

    public Optional<Account> findByNumber(String accountNumber) {
        return Optional.ofNullable(accountBynumber.get(accountNumber));
    }


    public List<Account> findByCustomerId(String customerId) {
        List<Account> result = new ArrayList<>();
        for (Account a : accountBynumber.values()){
            if (a.getCustomerId().equals(customerId))
                result.add(a);
        }
        return result;
    }
}
