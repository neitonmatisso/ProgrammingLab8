package ru.nik.clientPackage;

import java.util.Scanner;

public class Account {
    private String login;
    private String password;

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public Account(){

    }

    public void enterData(){
        Scanner sc = new Scanner(System.in);
        System.out.print("введите логин: ");
        login = sc.nextLine();
        System.out.print( "ввдеите пароль: ");
        password = sc.nextLine();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return
                "login='" + login + '\'' +
                ", password='" + password ;
    }
}
