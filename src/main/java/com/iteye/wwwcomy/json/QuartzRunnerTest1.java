package com.iteye.wwwcomy.json;
//package test.json;
//
//import java.util.Collection;
//import java.util.Date;
//
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.quartz.SchedulerFactory;
//import org.quartz.Trigger;
//import org.quartz.impl.StdSchedulerFactory;
//
//public class QuartzRunnerTest1 {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//
//	}
//
//	public static void runTask() throws SchedulerException {
//		// SchedulerFactory quartzFactory = new StdSchedulerFactory();
//		// Scheduler sched = quartzFactory.getScheduler();
//		// JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1")
//		// .build();
//		SchedulerFactory sf = new StdSchedulerFactory();
//		Scheduler sched = sf.getScheduler();
//
//		// computer a time that is on the next round minute
//		Date runTime = evenMinuteDate(new Date());
//		// define the job and tie it to our HelloJob class
//		JobDetail job = newJob(stringTest.class).withIdentity("job1", "group1")
//				.build();
//
//		// Trigger the job to run on the next round minute
//		Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
//				.startAt(runTime).build();
//
//		// Tell quartz to schedule the job using our trigger
//		sched.scheduleJob(job, trigger);
//
//		// Start up the scheduler (nothing can actually run until the
//		// scheduler has been started)
//		sched.start();
//
//		try {
//			// wait 65 seconds to show job
//			Thread.sleep(65L * 1000L);
//			// executing...
//		} catch (Exception e) {
//		}
//
//		// shut down the scheduler
//		sched.shutdown(true);
//	}
//}
