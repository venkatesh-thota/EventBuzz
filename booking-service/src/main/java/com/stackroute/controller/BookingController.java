package com.stackroute.controller;

import com.stackroute.domain.*;
import com.stackroute.exception.SeatAlreadyBookedException;
import com.stackroute.service.BookingService;
import com.stackroute.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/booking")
public class BookingController {

    private RedisService redisService;
    private BookingService bookingService;
    private SimpMessagingTemplate template;

    @Autowired
    public BookingController(RedisService redisService,BookingService bookingService,SimpMessagingTemplate template){
        this.redisService=redisService;
        this.bookingService=bookingService;
        this.template=template;
    }

    @MessageMapping("/seat")
    public void updatelayout(Details layout) {
        System.out.println("jsdh");
        System.out.println(layout);
        ResponseEntity responseEntity;
        String[] seats=layout.getSeats();
        List<SeatLayout> seatLayoutList=new ArrayList<>();
        for(int i=0;i<seats.length;i++){
            SeatLayout seatLayout=new SeatLayout();
            seatLayout.setSeatNumber(seats[i]);
            seatLayoutList.add(seatLayout);
        }
        try {
            BookingArena bookingArena = redisService.update(layout.getEmail(), layout.getShowId(), seatLayoutList, layout.getBookingId(), layout.getType(), layout.getTotalPrice());
            System.out.println("update completed");
            this.template.convertAndSend("/topic/seat", bookingArena);
        }
        catch (SeatAlreadyBookedException ex){
            this.template.convertAndSend("/topic/seat", ex.getMessage());
        }

        //bookingRepository.save(layout);
        //System.out.println(bookingRepository.findById(layout.getScreenNo()));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Booking booking) {
        Booking booking1=bookingService.add(booking);
        ResponseEntity responseEntity = new ResponseEntity<Booking>(booking, HttpStatus.OK);
       return responseEntity;
    }

    @PutMapping("bookingArena/freeSeats")
    public ResponseEntity<?> freeSeats(@RequestBody Details details) {

        String[] seats=details.getSeats();
        List<SeatLayout> seatLayoutList=new ArrayList<>();
        for(int i=0;i<seats.length;i++){
            SeatLayout seatLayout=new SeatLayout();
            seatLayout.setSeatNumber(seats[i]);
            seatLayoutList.add(seatLayout);
        }
        BookingArena bookingArena=redisService.freeSeats(details.getEmail(),details.getShowId(),seatLayoutList,details.getBookingId(),details.getType(),details.getTotalPrice());

        ResponseEntity responseEntity = new ResponseEntity<BookingArena>(bookingArena, HttpStatus.OK);
        return responseEntity;
    }

    @PutMapping("bookingArena/blockSeats")
    public ResponseEntity<?> blockSeats(@RequestBody Details details) {

        String[] seats=details.getSeats();
        List<SeatLayout> seatLayoutList=new ArrayList<>();
        for(int i=0;i<seats.length;i++){
            SeatLayout seatLayout=new SeatLayout();
            seatLayout.setSeatNumber(seats[i]);
            seatLayoutList.add(seatLayout);
        }
         BookingDetails bookingDetails=redisService.blockSeats(details.getEmail(),details.getShowId(),seatLayoutList,details.getBookingId(),details.getType(),details.getTotalPrice());

        ResponseEntity responseEntity = new ResponseEntity<BookingDetails>(bookingDetails, HttpStatus.OK);
        return responseEntity;
    }



    @PutMapping
    public ResponseEntity<?> update(@RequestBody Booking booking){

        Booking booking1=bookingService.update(booking.getCityName(),booking.getMovieBookings().get(0).getMovieId(),booking);
        ResponseEntity responseEntity = new ResponseEntity<Booking>(booking1, HttpStatus.OK);
        return responseEntity;
    }


    @GetMapping
    public ResponseEntity<?> getAll() {

        List<Booking> bookings=bookingService.getAll();
        ResponseEntity responseEntity = new ResponseEntity<List<Booking>>(bookings, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("bookingArena/{showId}")
    public ResponseEntity<?> getArenaByid(@PathVariable("showId") String showId) {

        BookingArena bookingArena=redisService.getBookingArenaById(showId);
        ResponseEntity responseEntity = new ResponseEntity<BookingArena>(bookingArena, HttpStatus.OK);
        return responseEntity;
    }

    @PutMapping("bookingArena/{email}/{showId}/{bookingId}")
    public ResponseEntity<?> updateArena(@PathVariable("email") String email,@PathVariable("showId") String showId,
                                         @PathVariable("bookingId") int bookingId,
                                         @RequestBody CategoryBooking categoryBooking){
        ResponseEntity responseEntity;
        try{
        BookingArena bookingArena=redisService.update(email,showId, categoryBooking.getSeatLayoutList(),bookingId,
                categoryBooking.getType(), categoryBooking.getSeatLayoutList().size()* categoryBooking.getPrice());
        responseEntity = new ResponseEntity<BookingArena>(bookingArena, HttpStatus.OK);}
        catch (SeatAlreadyBookedException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.OK);
        }
        catch (Exception ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.OK);
        }
        return responseEntity;
    }

//    @DeleteMapping("/{showId}")
//    public ResponseEntity<?> delete(@PathVariable("showId") String showId) {
//        ResponseEntity responseEntity;
//        BookingArena bookingArena=redisService.deleteArena(showId);
//        if(bookingArena!= null) {
//            Booking booking1 = bookingService.updateArena(bookingArena);
//            responseEntity = new ResponseEntity<Booking>(booking1, HttpStatus.OK);
//        }
//        else
//            responseEntity = new ResponseEntity<BookingArena>(bookingArena, HttpStatus.OK);
//        return responseEntity;
//    }

}
