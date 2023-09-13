package com.example.springbootCRUDOperationsLearning.springbootCRUDOperationsLearning.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;


//Here we have used NamedQueries which keeps the database platform indepdendent but if we will use NamedNativeQueries then we use native SQL queries which
// makes our db platform dependent
//@NamedQueries({
//        @NamedQuery(
//                name = "findByPassengerNameAndSourceStation",
//                query = "SELECT t FROM Ticket t WHERE t.passengerName = :passengerName AND t.sourceStation = :sourceStation"
//        ),
//        @NamedQuery(
//                name = "findByPassengerNameAndDestinationStation",
//                query = "SELECT t FROM Ticket t WHERE t.passengerName= :passengerName AND t.destinationStation = :destinationStation"
//        )
//})



//@NamedNativeQueries (value = {
//        @NamedNativeQuery ( name="findByPassengerNameAndSourceStation",query = "select * from Ticket t where t.passenger_name=?1 AND t.source_station=?2",resultClass = Ticket.class),
//        @NamedNativeQuery ( name="findByPassengerNameAndDestinationStation" , query = "select * from Ticket t where t.passenger_name=?1 AND t.destination_name=?2",resultClass = Ticket.class)
//})

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    public Ticket(String passengerName , String sourceStation , String destinationStation , Date bookingDate) {
        this.passengerName = passengerName;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.bookingDate = bookingDate;
    }

    public Ticket() {
    }

    @Column(name = "passenger_name",nullable = false)
    private String passengerName;

    @Column(name = "source_station",nullable = false)
    private String sourceStation;

    @Column(name = "destination_station",nullable = false)
    private String destinationStation;

    @Column(name = "booking_date",nullable = false)
    private Date bookingDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public void setSourceStation(String sourceStation) {
        this.sourceStation = sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String toString()
    {
        return this.passengerName;
    }
}
