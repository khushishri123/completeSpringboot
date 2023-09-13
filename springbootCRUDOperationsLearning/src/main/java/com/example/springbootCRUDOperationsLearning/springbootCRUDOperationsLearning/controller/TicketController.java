package com.example.springbootCRUDOperationsLearning.springbootCRUDOperationsLearning.controller;

import com.example.springbootCRUDOperationsLearning.springbootCRUDOperationsLearning.model.Ticket;
import com.example.springbootCRUDOperationsLearning.springbootCRUDOperationsLearning.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @PostMapping("/add")
    public Ticket add(@RequestBody Ticket ticket)
    {
        return this.ticketService.add ( ticket );
    }

    @GetMapping("/getTicketById/{id}")
    public Optional<Ticket> getTicketById(@PathVariable("id") int id)
    {
        return this.ticketService.getTicketById ( id );
    }
    @GetMapping("/getAllTickets")
    public Iterable<Ticket> getAllTickets()
    {
        return this.ticketService.getAllTickets ();
    }
    @DeleteMapping("/delete")
    public String delete(@RequestParam int id)
    {
        this.ticketService.delete ( id );
        return "Deleted successfully";
    }
    @PutMapping("/update")
    public Ticket update(@RequestBody Ticket ticket)
    {
        return this.ticketService.update ( ticket );
    }
}
