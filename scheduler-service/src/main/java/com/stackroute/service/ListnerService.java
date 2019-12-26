package com.stackroute.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.stackroute.domain.ScheduleStopping;
import com.stackroute.domain.Show;
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


@Service
public class ListnerService {
	private Logger logger = LoggerFactory.getLogger(getClass());

    //the show data is consumed here
	@KafkaListener(topics = "show-json", groupId = "showJson", containerFactory = "kafkaListenerContainerFactory")
	public void consumeKafka(Show show) throws ParseException, SchedulerException {

		logger.info("{}",show);

		//populate the  ScheduleStopping Class
		List<ScheduleStopping> schdStop = new ArrayList<>();
		for (int i = 0; i < show.getMovies().size(); i++) {
			for (int j = 0; j < show.getMovies().get(i).getTheatres().size(); j++) {
				for (int k = 0; k < show.getMovies().get(i).getTheatres().get(j).getTimings().size(); k++) {
					ScheduleStopping scheduleStopping = new ScheduleStopping();
					scheduleStopping.setCity(show.getCityName());
					scheduleStopping.setMovie(show.getMovies().get(i).getMovieTitle());
					scheduleStopping.setMovieId(show.getMovies().get(i).getMovieId());
					scheduleStopping.setTheatreId(show.getMovies().get(i).getTheatres().get(j).getTheatreId());
					scheduleStopping.setShowId(show.getMovies().get(i).getTheatres().get(j).getTimings().get(k).getShowId());
					scheduleStopping.setShowTiming(show.getMovies().get(i).getTheatres().get(j).getTimings().get(k).getShowTime());
					scheduleStopping.setMessage("stop booking");
					schdStop.add(scheduleStopping);
				}
			}
		}

		Scheduler sc = null;

		for (int i = 0; i < schdStop.size(); i++) {
			String startDateStr = schdStop.get(i).getShowTiming();
			Calendar c = Calendar.getInstance();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime ldt = LocalDateTime.parse(schdStop.get(i).getShowTiming(), formatter);
			LocalDateTime ldt2 = ldt.minusMinutes(360);
			Instant instant = ldt2.atZone(ZoneId.systemDefault()).toInstant();
			Date runTime = Date.from(instant);

			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date dateStart = dateformatter.parse(startDateStr);
			c.setTime(dateStart);
			c.add(Calendar.DATE, 7);
			Date endDate = c.getTime();
			logger.info("{}",endDate);
			System.out.println(schdStop);
			sc = StdSchedulerFactory.getDefaultScheduler();

			// define the job and tie it to our JobFunction class
			JobKey jobKey = JobKey.jobKey("job" + i + schdStop.get(i).getTheatreId() + LocalDateTime.now());
			JobDetail job = JobBuilder.newJob(JobFunction.class)
					.usingJobData("theatreId", schdStop.get(i).getTheatreId())
					.usingJobData("showTiming", schdStop.get(i).getShowTiming())
					.usingJobData("city", schdStop.get(i).getCity())
					.usingJobData("movie", schdStop.get(i).getMovie())
					.usingJobData("movieId", schdStop.get(i).getMovieId())
					.usingJobData("message", schdStop.get(i).getMessage())
					.usingJobData("showId", schdStop.get(i).getShowId())
					.withIdentity(jobKey).build();


			TriggerKey triggerKey = TriggerKey.triggerKey("trigger" + i + schdStop.get(i).getTheatreId() + LocalDateTime.now());
			if (sc.getTriggerState(triggerKey) == TriggerState.ERROR) {
				sc.resumeTrigger(triggerKey);
			}

			// Trigger the job
			SimpleTrigger trigger = TriggerBuilder.newTrigger().forJob(job).withIdentity(triggerKey)
					.withDescription("Sample trigger").startAt(runTime).endAt(endDate).withSchedule(SimpleScheduleBuilder.simpleSchedule())
					.build();

            // Tell quartz to schedule the job using our trigger
			sc.scheduleJob(job, trigger);

		}
		sc.start();
	}
}
