package service.impl;

import domain.Account;
import domain.Customer;
import domain.Transaction;
import domain.Type;
import exeptions.AccountNotFoundException;
import exeptions.InsufficentFundException;
import exeptions.ValidationException;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;
import service.BankService;
import util.Validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {
    private final AccountRepository accountRepository=new AccountRepository();
    private final TransactionRepository transactionRepository=new TransactionRepository();
    private final CustomerRepository customerRepository=new CustomerRepository();
    private final Validation<String> validateName=name->{
        if(name==null||name.isBlank()){
            throw new ValidationException("name is empty");
        }
    };
    private final Validation<String> validateEmail=email->{
        if(email==null||!email.contains("@")||!email.contains(".com")){
            throw new ValidationException("Enter correct email");
        }
    };
    private final Validation<String> validateAccountType=type->{
        if (type == null || !(type.equalsIgnoreCase("SAVINGS") || type.contains("CURRENT"))){
            throw new ValidationException("Give right AccountType");
        }
    };
    private final Validation<Double> validateamount=amount->{
        if(amount<0 || amount==null){
            throw new ValidationException("Enter valid amount");
        }
    };


    @Override
    public String openAccount(String name,String email,String accountType) {
        validateName.validate(name);
        validateEmail.validate(email);
        validateAccountType.validate(accountType);

        String customerId= UUID.randomUUID().toString();
        Customer c=new Customer(customerId,name,email);
        customerRepository.save(c);

        //Change Later

        String accountNumber = getAccountNumber();
        Account account=new Account(accountNumber,customerId,(double)0,accountType);
        accountRepository.save(account);

        //SAVE


        return accountNumber;
    }

    @Override
    public List<Account> listofAccounts() {
        return accountRepository.findAll().stream().sorted(Comparator.comparing(Account::getAccountNumber)).toList();
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note) {
        validateamount.validate(amount);
        Account account=accountRepository.findByNumber(accountNumber).orElseThrow(()->new AccountNotFoundException("Account not found"+accountNumber));
        account.setBalance(account.getBalance()+amount);
//        Transaction transaction=new Transaction(account.getAccountNumber(),amount,UUID.randomUUID().toString(),note, LocalDateTime.now(), Type.DEPOSIT);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                account.getAccountNumber(),
                Type.DEPOSIT,
                amount,
                LocalDateTime.now(),
                note
        );

        transactionRepository.add(transaction);
    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {
        validateamount.validate(amount);
        Account account=accountRepository.findByNumber(accountNumber).orElseThrow(()->new AccountNotFoundException("Account not found"+accountNumber));

        if(account.getBalance()<amount){
            throw new InsufficentFundException("Insufficient Balance");
        }
        account.setBalance(account.getBalance()-amount);
//        Transaction transaction=new Transaction(account.getAccountNumber(),amount,UUID.randomUUID().toString(),note, LocalDateTime.now(), Type.DEPOSIT);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                account.getAccountNumber(),
                Type.WITHDRAW,
                amount,
                LocalDateTime.now(),
                note
        );

        transactionRepository.add(transaction);

    }

    @Override
    public void transfer(String accountNumber, String accountNumber2, Double amount, String note) {
        validateamount.validate(amount);
        if(accountNumber2.equals(accountNumber)){
            throw new ValidationException("Can not transfer to your own account :");
        }
        Account from=accountRepository.findByNumber(accountNumber).orElseThrow(()->new AccountNotFoundException("Account not found"+accountNumber));
        Account to=accountRepository.findByNumber(accountNumber2).orElseThrow(()->new AccountNotFoundException("Account not found"+accountNumber2));
        if(from.getBalance()<amount){
            throw new InsufficentFundException("Insufficient Balance");
        }
        from.setBalance(from.getBalance()-amount);
        to.setBalance(to.getBalance()+amount);

        Transaction Fromtransaction = new Transaction(
                UUID.randomUUID().toString(),
                from.getAccountNumber(),
                Type.TRANSFER_IN,
                amount,
                LocalDateTime.now(),
                note
        );
        transactionRepository.add(Fromtransaction);
        Transaction Totransaction = new Transaction(
                UUID.randomUUID().toString(),
                to.getAccountNumber(),
                Type.TRANSFER_OUT,
                amount,
                LocalDateTime.now(),
                note
        );
        transactionRepository.add(Totransaction);




    }

    @Override
    public List<Transaction> getStatement(String accountNumber) {
        return transactionRepository.findByAccount(accountNumber).stream().sorted(Comparator.comparing(Transaction::getTimeStamp)).toList();


    }

    @Override
    public List<Account> searchAccountByName(String q) {
//        String query=(q==null||q.isEmpty())?"":q.toLowerCase();
        String query = (q == null) ? "" : q.toLowerCase();
//        List<Account> result=new ArrayList<>();
//        for(Customer c:customerRepository.findAll()){
//            if(c.getName().toLowerCase().contains(query)){
//                result.addAll(accountRepository.findByCustomerId(c.getId()));
//            }
//        }
//        result.sort(Comparator.comparing(Account::getAccountNumber));


        return customerRepository.findAll().stream()
                .filter(c -> c.getName().toLowerCase().contains(query))
                .flatMap(c -> accountRepository.findByCustomerId(c.getId()).stream())
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());

//        return result;
    }

    private String getAccountNumber() {
        int temp=accountRepository.findAll().size()+1;

        return String.format("AC%06d",temp);
    }

}
