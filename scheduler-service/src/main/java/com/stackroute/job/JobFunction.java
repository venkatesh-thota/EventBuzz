package com.stackroute.job;

import com.stackroute.configuration.KafkaProducer;
import com.stackroute.domain.ScheduleStopping;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class JobFunction implements Job {

	public static String THEATREID;
	public static String SHOWTIMING;
	public static String SHOWID;
	public static String CITY;
	public static String MOVIE;
	public static String MOVIEID;
	public static String MESSAGE;
	

	private static KafkaTemplate<String, ScheduleStopping> objectKafkaTemplate;
	private static KafkaTemplate<String, String> kafkaTemplate;



	/**
	 * <p>
	 * Empty constructor for job initilization
	 * </p>
	 * <p>
	 * Quartz requires a public empty constructor so that the
	 * scheduler can instantiate the class whenever it needs.
	 * </p>
	 */

	public JobFunction() {
	}

	@Autowired
	public JobFunction(KafkaTemplate<String, String> kafkaTemplate,
			KafkaTemplate<String, ScheduleStopping> objectKafkaTemplate) {
		if (JobFunction.kafkaTemplate == null) {
			JobFunction.kafkaTemplate = kafkaTemplate;
		}
		if (JobFunction.objectKafkaTemplate == null) {
			JobFunction.objectKafkaTemplate = objectKafkaTemplate;
		}
	}

    //job is executed here
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Inside execute of scheduler");
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		ScheduleStopping message = new ScheduleStopping();
		message.setCity(dataMap.getString("city"));
		message.setTheatreId(dataMap.getInt("theatreId"));
		message.setShowTiming(dataMap.getString("showTiming"));
		message.setMovie(dataMap.getString("movie"));
		message.setMovieId(dataMap.getInt("movieId"));
		message.setMessage(dataMap.getString("message"));
		message.setShowId(dataMap.getString("showId"));
		String topic1 = KafkaProducer.TOPIC;
		String topic2=KafkaProducer.TOPIC2;
		objectKafkaTemplate.send(topic1, message);
		System.out.println("topic send to show"+message);
		objectKafkaTemplate.send(topic2,message);
		System.out.println("topic send to booking"+message);

	}
}
