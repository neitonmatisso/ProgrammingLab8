package ru.nik.businessLogic.ticketClasses;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
@Entity
@Table(name = "tickets")
public class Ticket implements Comparable<Ticket> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "coordinatesid")
    private Coordinates coordinates;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private java.util.Date creationDate;

    @Column(name = "price")
    private long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TicketType type;
    @Column(name = "owner")
    private String owner;
    @Transient
    private String bruhDate;
    @Transient
    private double x;
    @Transient
    private double y;
    public Ticket(String name, Coordinates coordinates, long price,TicketType type){
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.type = type;
        creationDate = new Date();
    }

    public Ticket(Ticket ticket) {
        this.name = ticket.getName();
        this.price = ticket.getPrice();
        this.type  = ticket.getType();
        this.coordinates = new Coordinates();
        this.coordinates.setX( ticket.getCoordinates().getX());
        this.coordinates.setY(ticket.getCoordinates().getY());
        this.creationDate = ticket.getCreationDate();
        this.owner = ticket.getOwner();
    }
    public Ticket(){
        creationDate = new Date();
    }
    public Ticket(String name, Coordinates coordinates, long price,TicketType type, long ID){
        id = ID;
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.type = type;
        creationDate = new Date();

    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double getY(){
        return y;
    }
    public double getX(){
        return x;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBruhDate() {
        return bruhDate;
    }

    public void setBruhDate(String bruhDate) {
        this.bruhDate = bruhDate;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", type=" + type +
                ", owner='" + owner + '\'' +
                '}';
    }

    @Override
    public int compareTo(Ticket ticket) {
        return (int)( ticket.getPrice() - this.getPrice() );
    }
}
