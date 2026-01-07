package service;

import domain.Account;
import domain.Transaction;

import java.util.List;

public interface BankService {
    String openAccount(String name,String email,String accountType);
    List<Account> listofAccounts();

    void deposit(String accountNumber, Double amount, String note);

    void withdraw(String accountNumber, Double amount, String s);

    void transfer(String accountNumber, String accountNumber2, Double amount, String transfer);
    List<Transaction>getStatement(String accountNumber);

    List<Account> searchAccountByName(String q);
}
