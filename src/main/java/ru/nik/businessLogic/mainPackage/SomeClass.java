package ru.nik.businessLogic.mainPackage;

public class SomeClass implements Comparable<SomeClass>{
    private int a;
    public void setA(int a ){
        this.a = a;
    }
    @Override
    public String toString(){
        return "a =" + a;
    }
    @Override
    public int compareTo(SomeClass someClass) {
        return a ;
    }
}
