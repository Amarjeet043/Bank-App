package app;

import service.BankService;
import service.impl.BankServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankService bankService=new BankServiceImpl();
        boolean running=true;

        System.out.println("Welcome to Console Bank");

        while(running){
            System.out.println(
                    "1) Open Account \n" +
                            "2) Deposit\n" +
                            "3) Withdraw\n" +
                            "4) Transfer\n" +
                            "5) Account Statement\n" +
                            "6) List Accounts\n" +
                            "7) Search Account by Customer Name\n" +
                            "0) Exit"
            );
            System.out.println("CHOOSE :");
            String choice=scanner.nextLine().trim();
//            System.out.println("Choice:"+choice);
            switch (choice){
                case "1":openAccount(scanner,bankService);
                break;
                case "2":Deposit(scanner,bankService);
                break;
                case "3":Withdraw(scanner, bankService);
                break;
                case "4":Transfer(scanner,bankService);
                break;
                case "5":statemet(scanner,bankService);
                break;
                case "6":listAccounts(scanner,bankService);
                break;
                case "7":searchAccount(scanner,bankService);
                break;
                case "0":running=false;break;
            }

        }

    }

    private static void openAccount(Scanner scanner, BankService bankService) {
        System.out.println("Customer Name: ");
        String name=scanner.nextLine().trim();

        System.out.println("Customer Email:");
        String email=scanner.nextLine().trim();
        System.out.println("Account Type(SAVINGS/CURRENT):");
        String type=scanner.nextLine().trim();
        System.out.println("Initial Deposit(Optional ,blank for 0): ");

        String amountStr=scanner.nextLine().trim();
        if(amountStr.isBlank()) amountStr="0";
        Double initial=Double.valueOf(amountStr);
        String AccountNumber=bankService.openAccount(name,email,type);
        if(initial>0){
            bankService.deposit(AccountNumber,initial,"Initial Amount Deposited");

        }
        System.out.println("Account Opened Successfully :"+AccountNumber);


    }

    private static void Deposit(Scanner scanner, BankService bankService) {
        System.out.println("Account Number:");
        String accountNumber=scanner.nextLine().trim();
        System.out.println("Amount to Deposit:");
        Double amount=Double.valueOf(scanner.nextLine().trim());
        bankService.deposit(accountNumber,amount,"Deposit");
        System.out.println("Deposited Successfully"+accountNumber);
    }

    private static void Withdraw(Scanner scanner,BankService bankService) {
        System.out.println("Account Number:");
        String accountNumber=scanner.nextLine().trim();
        System.out.println("Amount to Withdraw:");
        Double amount=Double.valueOf(scanner.nextLine().trim());
        bankService.withdraw(accountNumber,amount,"Withdrawl::");
        System.out.println("Deposited Successfully"+accountNumber);
    }

    private static void Transfer(Scanner scanner,BankService bankService) {
        System.out.println("From Account:");
        String accountNumber=scanner.nextLine().trim();
        System.out.println("To Account:");
        String accountNumber2=scanner.nextLine().trim();
        System.out.println("Amount to Transfer:");
        Double amount=Double.valueOf(scanner.nextLine().trim());
        bankService.transfer(accountNumber,accountNumber2,amount,"Transfer");
    }

    private static void statemet(Scanner scanner, BankService bankService) {
        System.out.println("Account Number:");
        String accountNumber=scanner.nextLine().trim();
        bankService.getStatement(accountNumber).forEach(n-> System.out.println(n.getTimeStamp() + " "+ n.getType()+ " | "+n.getAmount() +"|"+n.getNote()));

    }

    private static void listAccounts(Scanner scanner, BankService bankService) {

        bankService.listofAccounts().forEach(a->System.out.println(a.getAccountNumber() + "|" +a.getAccountType() + "| " + a.getBalance()));
    }

//    private static void searchAccount(Scanner scanner, BankService bankService) {
//        System.out.println("Customer Name:");
//        String q=scanner.nextLine().trim();
//        bankService.searchAccountByName(q).forEach(account-> System.out.println(account.getAccountNumber() + "|" + account.getAccountType() + "| " + account.getBalance()));
//    }
private static void searchAccount(Scanner scanner, BankService bankService) {
    System.out.println("Customer name contains: ");
    String q = scanner.nextLine().trim();
    bankService.searchAccountByName(q).forEach(account ->
            System.out.println(account.getAccountNumber() + " | " + account.getAccountType() + " | " + account.getBalance())
    );
    System.out.println("HEllo");
}
}
