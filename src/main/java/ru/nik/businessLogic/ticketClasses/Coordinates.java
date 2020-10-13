package ru.nik.businessLogic.ticketClasses;

import javax.persistence.*;

@Entity
@Table(name = "coordinates")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "x")
    private double x;
    @Column (name = "y")
    private double y; //Максимальное значение поля: 594
    public Coordinates(double x ,double y){
        this.x = x;
        this.y = y;
    }
    public Coordinates(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return " x-coordinate " + x +
                " y-coordinate " + y;
    }
}
