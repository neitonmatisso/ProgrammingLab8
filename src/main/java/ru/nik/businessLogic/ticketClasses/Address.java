package ru.nik.businessLogic.ticketClasses;

public class Address {
    private String street; //Длина строки не должна быть больше 119, Поле может быть null
    private String zipCode; //Длина строки должна быть не меньше 8, Поле может быть null
    public Address(String street, String zipCode){
        this.street = street;
        this.zipCode = zipCode;
    }
}
