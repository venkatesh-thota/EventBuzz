package com.stackroute.service;

import com.stackroute.domain.*;
import com.stackroute.repository.RedisRepository;
import com.stackroute.exception.SeatAlreadyBookedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisServiceImpl implements RedisService {

    private RedisRepository redisRepository;
    private SimpMessagingTemplate template;

    @Value("${app.service.exception1}")
    private String exception1;

    @Autowired
    public RedisServiceImpl(RedisRepository redisRepository,SimpMessagingTemplate template){
        this.redisRepository=redisRepository;
        this.template=template;
    }

    @Override
    public BookingArena saveBookingArena(BookingArena bookingArena) {
        BookingArena bookingArena1=redisRepository.save(bookingArena);
        System.out.println("insid redis save");
        return bookingArena1;
    }


    @Override
    public BookingArena update(String email, String showId, List<SeatLayout> seats, int bookingId, String type,int totalPrice)
     throws SeatAlreadyBookedException {

        int flag=0;
        BookingArena arenaObject =redisRepository.findOne(showId);

        BookingDetails bookingDetails=new BookingDetails(bookingId,email,arenaObject.getMovieTitle()
                ,arenaObject.getMovieId(),arenaObject.getTheatreName(),arenaObject.getTheatreId(),
                arenaObject.getShowTime(),arenaObject.getShowId(),1,false,seats,totalPrice);

        if(arenaObject.getBookingList() != null)
            arenaObject.getBookingList().add(bookingDetails);
        else {
            List<BookingDetails> userBookingDetailsList=new ArrayList<>();
            userBookingDetailsList.add(bookingDetails);
            arenaObject.setBookingList(userBookingDetailsList);
        }
        List<CategoryBooking> categoryBookingList =arenaObject.getScreenLayout().getCategoryBookingList();
        for(int i = 0; i< categoryBookingList.size(); i++){
            if(categoryBookingList.get(i).getType().equals(type)){
                List<SeatLayout> seatLayouts= categoryBookingList.get(i).getSeatLayoutList();
                for(int j=0;j<seats.size();j++){
                    for(int l=0;l<seatLayouts.size();l++){
                        if(seats.get(j).getSeatNumber().equals(seatLayouts.get(l).getSeatNumber())){
                            if(arenaObject.getScreenLayout().getCategoryBookingList().get(i).getSeatLayoutList().get(l).getBookingSeatStatus() == 0)
                                arenaObject.getScreenLayout().getCategoryBookingList().get(i).getSeatLayoutList().get(l).setBookingSeatStatus(1);
                            else
                                throw new SeatAlreadyBookedException(exception1);
                            break;
                        }
                    }
                }
                break;
            }
        }
        redisRepository.save(arenaObject);
        return arenaObject;
    }



    @Override
    public BookingArena freeSeats(String email, String showId, List<SeatLayout> seats, int bookingId, String type,int totalPrice) {

        int flag=0;
        BookingArena arenaObject =redisRepository.findOne(showId);

        List<CategoryBooking> categoryBookingList =arenaObject.getScreenLayout().getCategoryBookingList();
        for(int i = 0; i< categoryBookingList.size(); i++){
            if(categoryBookingList.get(i).getType().equals(type)){
                List<SeatLayout> seatLayouts= categoryBookingList.get(i).getSeatLayoutList();
                for(int j=0;j<seats.size();j++){
                    for(int l=0;l<seatLayouts.size();l++){
                        if(seats.get(j).getSeatNumber().equals(seatLayouts.get(l).getSeatNumber())){
                            arenaObject.getScreenLayout().getCategoryBookingList().get(i).getSeatLayoutList().get(l).setBookingSeatStatus(0);
                            break;
                        }
                    }
                }
                break;
            }
        }
        redisRepository.save(arenaObject);
        this.template.convertAndSend("/topic/seat", arenaObject);
        return arenaObject;
    }

    @Override
    public BookingDetails blockSeats(String email, String showId, List<SeatLayout> seats, int bookingId, String type,int totalPrice) {

        int flag=0;
        BookingArena arenaObject =redisRepository.findOne(showId);

        List<CategoryBooking> categoryBookingList =arenaObject.getScreenLayout().getCategoryBookingList();
        for(int i = 0; i< categoryBookingList.size(); i++){
            if(categoryBookingList.get(i).getType().equals(type)){
                List<SeatLayout> seatLayouts= categoryBookingList.get(i).getSeatLayoutList();
                for(int j=0;j<seats.size();j++){
                    for(int l=0;l<seatLayouts.size();l++){
                        if(seats.get(j).getSeatNumber().equals(seatLayouts.get(l).getSeatNumber())){
                            arenaObject.getScreenLayout().getCategoryBookingList().get(i).getSeatLayoutList().get(l).setBookingSeatStatus(2);
                            break;
                        }
                    }
                }
                break;
            }
        }
        List<BookingDetails> bookingList=arenaObject.getBookingList();
        BookingDetails bookingDetails1=new BookingDetails();

        for(int p=0;p<bookingList.size();p++){
            if(bookingList.get(p).getBookingId() == bookingId){

                bookingList.get(p).setBookingStatus(2);
                bookingList.get(p).setPaymentStatus(true);
                bookingList.get(p).setEmail(email);
                bookingList.get(p).setTotalPrice(totalPrice);
                List<SeatLayout> seatsList=bookingList.get(p).getSeats();
                for(int x=0;x<seatsList.size();x++){
                    seatsList.get(x).setBookingSeatStatus(2);
                }
                bookingList.get(p).setSeats(seatsList);
                bookingDetails1=bookingList.get(p);
                arenaObject.setBookingList(bookingList);
                break;
            }
        }
        redisRepository.save(arenaObject);
        this.template.convertAndSend("/topic/seat",arenaObject);
        return bookingDetails1;
    }

    @Override
    public BookingArena deleteArena(String showId) {
        System.out.println("inside delete arena redis");
        BookingArena bookingArena1=redisRepository.findOne(showId);
        redisRepository.delete(showId);
        return bookingArena1;
    }

    @Override
    public List<BookingArena> getAll() {
        return (List<BookingArena>) redisRepository.findAll();
    }

    @Override
    public BookingArena getBookingArenaById(String showId) {
        if(redisRepository.exists(showId)) {
            return redisRepository.findOne(showId);
        }
        else return null;
    }

}
