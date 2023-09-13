package com.example.springbootCRUDOperationsLearning.springbootCRUDOperationsLearning;

import com.example.springbootCRUDOperationsLearning.springbootCRUDOperationsLearning.model.Ticket;
import com.example.springbootCRUDOperationsLearning.springbootCRUDOperationsLearning.service.TicketService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringbootCrudOperationsLearningApplication implements CommandLineRunner {

	@PersistenceContext
	public EntityManager entityManager;
	@Autowired
	TicketService ticketService;
	public static void main(String[] args) {
		SpringApplication.run(SpringbootCrudOperationsLearningApplication.class, args);


	}

	@Override
	public void run(String... args) throws Exception {
		//add();
		//addAll();
		// getBySourceStation();
		// getBySourceStationAndDestinationStation();
		//getByPassengerNameAndSourceStation();
		//getByPassengerNameAndDestinationStation ();
		//getByDestinationStation();
//		CompletableFuture<Ticket> completableFuture=this.ticketService.findByPassengerName ("Sam");
//		Ticket t=completableFuture.get(20, TimeUnit.SECONDS);
//		System.out.println (t);
//		updateTicketPassengerNameById();
	}

	private void updateTicketPassengerNameById() {
		this.ticketService.updateTicketPassengerNameById ( 1,"Kim Taehyung" );
	}


	public void getByDestinationStation()
	{
		//We can also do like this
		//Sort sort=Sort.by ( Sort.Order.asc ( "passengerName" ) );
		//Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		//or we can do like this, here page(first argument)  shows which page you want to see  and 2nd argument (size)
		// shows how many records will a page contain
		List<Ticket> lst=this.ticketService.findByDestinationStation ( "Seol", PageRequest.of ( 0,2, Sort.Direction.ASC,"passengerName" ));

		lst.forEach ( System.out::println );
	}
	private void getBySourceStationAndDestinationStation() {
		List<Ticket> lst=this.ticketService.findBySourceStationAndDestinationStation ( "Vrindavan","Dwarka" );
		lst.forEach ( System.out::println );
	}

	public void getByPassengerNameAndDestinationStation()
	{
		List<Ticket> ls=this.ticketService.findByPassengerNameAndDestinationStation ( "Radha","Vrindawan" );
		ls.forEach ( System.out::println );
	}

	public void getByPassengerNameAndSourceStation()
	{
		List<Ticket> ls=this.ticketService.findByPassengerNameAndSourceStation ("Sam","London");
		ls.forEach ( System.out::println );
	}

	public void getBySourceStation(){
		List<Ticket> lst=this.ticketService.findBySourceStation ( "London" );
		lst.forEach ( System.out::println );
	}
	public void add()
	{
		Ticket ticket=new Ticket();
		ticket.setPassengerName ( "Ram" );
		ticket.setSourceStation ( "Ayodhya" );
		ticket.setDestinationStation ( "Mithila" );
		ticket.setBookingDate ( new Date () );
		ticketService.add ( ticket );
	}
	public  void showTickets(){
		System.out.println ("Hello");
		TypedQuery<Ticket> tickets=entityManager.createNamedQuery ( "fetchPassengersFromGivenSourceStation",Ticket.class );
		tickets.setParameter ( "sourceStation","Ayodhya" );
		List<Ticket> list=tickets.getResultList ();
		for(Ticket t:list)
		{
			System.out.println (t.getId ()+" "+t.getPassengerName ()+" "+
					t.getSourceStation ()+" "+t.getDestinationStation ()+" "+t.getBookingDate ());
		}
	}

	public Iterable<Ticket> addAll()
	{
		List<Ticket> tickets= Arrays.asList ( new Ticket ("Shyam","Vrindavan","Dwarka",new Date ()),
				new Ticket ("Sita","Mithila","Ayodhya",new Date ()),
				new Ticket ("Radha","Barsana","Vrindawan",new Date ())
		);
		return ticketService.addAll ( tickets );
	}
//in this way you can add all the methods that are present in that CRUDRepository interface

}
