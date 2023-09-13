package com.example.springbootCRUDOperationsLearning.springbootCRUDOperationsLearning.service;


import com.example.springbootCRUDOperationsLearning.springbootCRUDOperationsLearning.model.*;
import  com.example.springbootCRUDOperationsLearning.springbootCRUDOperationsLearning.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class TicketService {
    @Autowired
    private TicketRepo ticketRepo;
    public  Ticket add(Ticket ticket)
    {
        return this.ticketRepo.save( ticket );
    }
    public Optional<Ticket> getTicketById(int id)
    {
        return this.ticketRepo.findById(id);
    }
    public Iterable<Ticket> getAllTickets()
    {
        return this.ticketRepo.findAll ();
    }
    public void delete(int id)
    {
        this.ticketRepo.deleteById ( id );
    }
    public  Ticket update(Ticket ticket)
    {
        Optional<Ticket> ticketOptional=getTicketById ( ticket.getId () );
        Ticket t=null;
        if (ticketOptional.isPresent()) {
            t = ticketOptional.get ();
            t.setPassengerName ( ticket.getPassengerName () );
            t.setSourceStation ( ticket.getSourceStation () );
            t.setDestinationStation ( ticket.getDestinationStation () );
            t.setBookingDate ( ticket.getBookingDate () );
            this.ticketRepo.save ( t );
        }
        return t;
    }

    public Iterable<Ticket> addAll(Iterable<Ticket> entities)
    {
        return ticketRepo.saveAll ( entities );
    }

    public List<Ticket> findBySourceStation(String sourceStation)
    {
        return this.ticketRepo.findBySourceStation ( sourceStation );
    }
    public List<Ticket> findBySourceStationAndDestinationStation(String sourceStation,String destinationStation)
    {
        return this.ticketRepo.findBySourceStationAndDestinationStation ( sourceStation,destinationStation );
    }
    public List<Ticket> findByPassengerNameAndSourceStation(String passengerName,String sourceStation)
    {
        return this.ticketRepo.findByPassengerNameAndSourceStation(passengerName,sourceStation);
    }
    public List<Ticket> findByPassengerNameAndDestinationStation(String passengerName,String destinationStation)
    {
        return this.ticketRepo.findByPassengerNameAndDestinationStation (passengerName,destinationStation );
    }
    public List<Ticket> findByDestinationStation(String destinationStation, Pageable pageable)
    {
        return this.ticketRepo.findByDestinationStation ( destinationStation,pageable );
    }



    public CompletableFuture<Ticket> findByPassengerName(String passengerName)
    {
        return this.ticketRepo.findByPassengerName(passengerName);
    }

    public void updateTicketPassengerNameById(Integer id,String newPassengerName)
    {
        this.ticketRepo.updateTicketPassengerNameById ( id,newPassengerName );
    }
}
