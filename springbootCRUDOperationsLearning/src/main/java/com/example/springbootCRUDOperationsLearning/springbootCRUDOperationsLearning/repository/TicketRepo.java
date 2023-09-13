package com.example.springbootCRUDOperationsLearning.springbootCRUDOperationsLearning.repository;

import org.springframework.data.repository.CrudRepository;


import com.example.springbootCRUDOperationsLearning.springbootCRUDOperationsLearning.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface TicketRepo extends CrudRepository<Ticket,Integer> {
    // using named methods technique out of the 3 JPA Query methods. Three JPA Query methods are :
    // named methods, named queries and @Query annotation
    List<Ticket> findBySourceStation(String sourceStation);
    List<Ticket> findBySourceStationAndDestinationStation(String sourceStation,String destinationStation);
    //    //we will see the example of Query annotation, here we are using JPQL
    @Query("select t from Ticket t where t.passengerName= :passengerName AND t.sourceStation=:sourceStation")
    List<Ticket> findByPassengerNameAndSourceStation(String passengerName,String sourceStation);
    //
//    //here we are using native query(SQL query)
    @Query(value = "select * from Ticket where passenger_name=?1 AND destination_station=?2",nativeQuery = true)
    List<Ticket> findByPassengerNameAndDestinationStation(String passengerName,String destinationStation);
    //
//    //now we will see the example of Pageable
    List<Ticket> findByDestinationStation(String destinationStation, Pageable pageable);


    @Async
    CompletableFuture<Ticket> findByPassengerName(String passengerName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Ticket t set t.passengerName=:passengerName where t.id=:id ")
    void updateTicketPassengerNameById(@Param ( "id" ) Integer id,@Param ( "passengerName" ) String passengerName);
}
