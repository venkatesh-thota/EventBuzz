package com.stackroute.service;

import com.stackroute.domain.*;
import com.stackroute.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;

    private BookingArena bookingArena;

    private RedisService redisService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,RedisService redisService){
        this.bookingRepository=bookingRepository;
        this.redisService=redisService;
    }

    @Override
    public Booking add(Booking booking) {

        bookingArena=new BookingArena();

        if(booking != null) {
            bookingArena.setCityName(booking.getCityName());
            bookingArena.setMovieId(booking.getMovieBookings().get(0).getMovieId());
            bookingArena.setMovieTitle(booking.getMovieBookings().get(0).getMovieTitle());
            bookingArena.setTheatreId(booking.getMovieBookings().get(0).getTheatreBookings().get(0).getTheatreId());
            bookingArena.setTheatreName(booking.getMovieBookings().get(0).getTheatreBookings().get(0).getTheatreName());
        }
        System.out.println("inside add booking of booking");
        int flag=1;
        int flag2=0;
        if(bookingRepository.exists(booking.getCityName())){
            System.out.println("City exists case");

            int movieId=booking.getMovieBookings().get(0).getMovieId();

            Booking booking1 = bookingRepository.findOne(booking.getCityName());

            for (int i = 0; i < booking1.getMovieBookings().size(); i++) {

                if (booking1.getMovieBookings().get(i).getMovieId() == movieId) {
                    System.out.println("Movie Id Exists");
                    flag2=1;
                    List<TheatreBooking> theatreList = booking1.getMovieBookings().get(i).getTheatreBookings();
                    for(int j=0;j<theatreList.size();j++){
                        if(theatreList.get(j).getTheatreId() == booking.getMovieBookings().get(0).getTheatreBookings().get(0).getTheatreId()){
                            flag=0;
                            break;
                        }
                    }
                    if (flag==1) {
                        booking1.getMovieBookings().get(i).getTheatreBookings().add(booking.getMovieBookings().get(0).getTheatreBookings().get(0));

                        List<TimingBooking> timingBookingList =booking.getMovieBookings().get(0).getTheatreBookings().get(0).getTimingBookings();
                        for(int k = 0; k< timingBookingList.size(); k++){
                            bookingArena.setShowId(timingBookingList.get(k).getShowId());
                            bookingArena.setShowTime(timingBookingList.get(k).getShowTime());
                            bookingArena.setScreenLayout(timingBookingList.get(k).getScreenLayout());
                            //calling redis servicce save method to save booking arena
                            redisService.saveBookingArena(bookingArena);
                        }
                    }
                    break;
                }
            }
            if(flag2==0){

                System.out.println("booking Movie not found case");
                booking1.getMovieBookings().add(booking.getMovieBookings().get(0));

                List<TimingBooking> timingBookingList =booking.getMovieBookings().get(0).getTheatreBookings().get(0).getTimingBookings();
                for(int k = 0; k< timingBookingList.size(); k++){
                    bookingArena.setShowId(timingBookingList.get(k).getShowId());
                    bookingArena.setShowTime(timingBookingList.get(k).getShowTime());
                    bookingArena.setScreenLayout(timingBookingList.get(k).getScreenLayout());

                    //here call to save booking arena into redis
                    redisService.saveBookingArena(bookingArena);

                }
            }
            bookingRepository.save(booking1);
            booking=booking1;
        }
        else{
            bookingRepository.save(booking);
            System.out.println("booking city not found case");

            List<TimingBooking> timingBookingList =booking.getMovieBookings().get(0).getTheatreBookings().get(0).getTimingBookings();
            for(int k = 0; k< timingBookingList.size(); k++){
                bookingArena.setShowId(timingBookingList.get(k).getShowId());
                bookingArena.setShowTime(timingBookingList.get(k).getShowTime());
                bookingArena.setScreenLayout(timingBookingList.get(k).getScreenLayout());

                //here call to save booking arena into redis
                redisService.saveBookingArena(bookingArena);
            }
        }
        return booking;
    }

    @Override
    public Booking update(String cityName, int movieId, Booking booking) {

        if(!bookingRepository.exists(cityName)){
            //throw new ShowNotFoundException(message1);
        }

        bookingArena=new BookingArena();

        TheatreBooking theatreBooking=booking.getMovieBookings().get(0).getTheatreBookings().get(0);

        Booking booking1=bookingRepository.findOne(cityName);
        int flag=0;
        for(int i = 0; i<booking1.getMovieBookings().size(); i++){

            if(booking1.getMovieBookings().get(i).getMovieId() == movieId){

                List<TheatreBooking> theatreList = booking1.getMovieBookings().get(i).getTheatreBookings();
                for(int j=0;j<theatreList.size();j++){
                    if(theatreList.get(j).getTheatreId() == theatreBooking.getTheatreId()){
                        flag=1;
                        booking1.getMovieBookings().get(i).getTheatreBookings().get(j).setTimingBookings(theatreBooking.getTimingBookings());
                        List<TimingBooking> timingBookingList=theatreBooking.getTimingBookings();
                        for(int b=0;b<timingBookingList.size();b++){
                            bookingArena.setShowId(timingBookingList.get(b).getShowId());
                            bookingArena.setShowTime(timingBookingList.get(b).getShowTime());

                            bookingArena.setTheatreId(theatreBooking.getTheatreId());
                            bookingArena.setTheatreName(theatreBooking.getTheatreName());

                            bookingArena.setMovieTitle(booking.getMovieBookings().get(0).getMovieTitle());
                            bookingArena.setMovieId(booking.getMovieBookings().get(0).getMovieId());
                            bookingArena.setCityName(booking.getCityName());

                            bookingArena.setScreenLayout(theatreBooking.getTimingBookings().get(b).getScreenLayout());

                            //here call to save booking arena into redis
                            redisService.saveBookingArena(bookingArena);
                        }
                        break;
                    }
                }
                break;
            }
        }
        bookingRepository.save(booking1);
        if(flag == 0){
            //throw new ShowNotFoundException(message1);
        }
        return booking1;
    }


    public Booking updateArena(BookingArena bookingArena1) {

        System.out.println("inside update arena");
        
        if(!bookingRepository.exists(bookingArena1.getCityName())){
            //exception
        }
        Booking booking=bookingRepository.findOne(bookingArena1.getCityName());

        List<MovieBooking> movieList=booking.getMovieBookings();
        for(int i=0;i<movieList.size();i++){
            if(booking.getMovieBookings().get(i).getMovieId() == bookingArena1.getMovieId()){
                List<TheatreBooking> theatreList=booking.getMovieBookings().get(i).getTheatreBookings();
                for(int j=0;j<theatreList.size();j++){
                    if(theatreList.get(j).getTheatreId() == bookingArena1.getTheatreId()){
                        List<TimingBooking> timingList=theatreList.get(j).getTimingBookings();
                        for(int k=0;k<timingList.size();k++){
                            if(timingList.get(k).getShowId() == bookingArena1.getShowId()){
                                booking.getMovieBookings().get(i).getTheatreBookings().get(j).getTimingBookings().get(k).setScreenLayout(bookingArena.getScreenLayout());
                                bookingRepository.save(booking);
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }

        return booking;
    }

    @Override
    public List<Booking> getAll(){
        return bookingRepository.findAll();
    }

    @KafkaListener(topics="booking-scheduler",containerFactory ="kafkaScheduleStoppingListenerContainerFactory")
    public void deleteBooking(@Payload ScheduleStopping scheduleStopping){
        System.out.println("Consumed Json Message: "+ scheduleStopping.toString());
        BookingArena bookingArena=redisService.deleteArena(scheduleStopping.getShowId());
        if(bookingArena != null){
            Booking booking=updateArena(bookingArena);
            System.out.println(booking);
        }
    }

}
