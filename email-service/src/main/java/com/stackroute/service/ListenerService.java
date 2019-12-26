package com.stackroute.service;

import java.text.ParseException;
import java.time.*;
import java.util.Date;

import com.stackroute.domain.Email;
import com.stackroute.domain.RsvpEventProducer;
import com.stackroute.job.JobFunction;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static org.quartz.DateBuilder.evenMinuteDate;


@Service
public class ListenerService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String[] email;
    private int len;
    private String message;


    //the show data is consumed here
    @KafkaListener(topics = "kafkarsvpjson", groupId = "rsvpJson", containerFactory = "kafkaListenerContainerFactory")
    public void consumeKafka(RsvpEventProducer rsvpEventProducer) throws ParseException, SchedulerException {

        logger.info("{}",rsvpEventProducer);
        	
        //populate the  Email Class
        Email schdEmail = new Email();
        len = rsvpEventProducer.getEvents().size();
        email=rsvpEventProducer.getEvents().get(len-1).getInvitations();
        message=
                "\nEvent Name: "+rsvpEventProducer.getEvents().get(len-1).getEventName()+
                "\nEvent Venue: "+rsvpEventProducer.getEvents().get(len-1).getEventVenue()+
                "\nScheduled Date: "+rsvpEventProducer.getEvents().get(len-1).getScheduledDate()+
                "\nEvent Description: "+rsvpEventProducer.getEvents().get(len-1).getEventDescription()+
                "\n\n"+rsvpEventProducer.getEvents().get(len-1).getInvitationMessage()+
                "\n\n\nThanks & Regards, \n"+rsvpEventProducer.getEmail();

        logger.info("{}",message);
        schdEmail.setFrom(rsvpEventProducer.getEmail());
        schdEmail.setBcc(email);
        schdEmail.setSubject(rsvpEventProducer.getEvents().get(len-1).getEventName());
        schdEmail.setBody(message);
        schdEmail.setSendAt(rsvpEventProducer.getEvents().get(len-1).getPublishDate());
        logger.info("{}",schdEmail);

        Scheduler sc = null;
        for (int i = 0; i < email.length; i++) {
            Date runTime = evenMinuteDate(new Date());

            sc = StdSchedulerFactory.getDefaultScheduler();

            // define the job and tie it to our JobFunction class
            JobKey jobKey = JobKey.jobKey("job" + i + schdEmail.getSubject() + LocalDateTime.now());
            JobDetail job = JobBuilder.newJob(JobFunction.class)
                    .usingJobData("from", schdEmail.getFrom())
                    .usingJobData("bcc",  email[i])
                    .usingJobData("subject", schdEmail.getSubject())
                    .usingJobData("body", schdEmail.getBody())
                    .withIdentity(jobKey).build();


            TriggerKey triggerKey = TriggerKey.triggerKey("trigger" + i + schdEmail.getSubject() + LocalDateTime.now());
            if (sc.getTriggerState(triggerKey) == TriggerState.ERROR) {
                sc.resumeTrigger(triggerKey);
            }

            // Trigger the job
            SimpleTrigger trigger = TriggerBuilder.newTrigger().forJob(job).withIdentity(triggerKey)
                    .withDescription("Sample trigger").startAt(runTime).withSchedule(SimpleScheduleBuilder.simpleSchedule())
                    .build();

            // Tell quartz to schedule the job using our trigger
            sc.scheduleJob(job, trigger);

        }

        sc.start();
    }
}
