package com.iteye.wwwcomy.quartz;

import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzMultiJobTest {
	public static void main(String[] args) throws Throwable {
		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();
		// @NOTICE 任务的开始时间，nextGivenSecondDate方法表示：当前时间之后，每当秒数是13的倍数都是触发时间，当然只触发一次
		// 比如：00:00:12秒开始主线程，则13秒就会触发任务，如果00:00:14秒开始主线程，则在26秒触发任务
		Date runTime = DateBuilder.nextGivenSecondDate(null, 13);
		JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();
		scheduler.scheduleJob(job, trigger);
		
		// @NOTICE 将同一个Job实现作为另外一个任务注册到scheduler，注意名字要区分
		job = JobBuilder.newJob(MyJob.class).withIdentity("job2", "group1").build();
		trigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1").startAt(runTime).build();
		scheduler.scheduleJob(job, trigger);

		// @NOTICE 重复执行,job3表示第一次执行完之后，每隔3秒钟执行一次，重复5次，重复的那个参数不包括第一次执行那次，即job3总共执行6次
		job = JobBuilder.newJob(MyJob.class).withIdentity("job3", "group1").build();
		trigger = TriggerBuilder.newTrigger().withIdentity("trigger3", "group1")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(5).withIntervalInSeconds(3)).startAt(runTime).build();
		scheduler.scheduleJob(job, trigger);

		scheduler.start();
		try {
			// 等待20秒
			Thread.sleep(20L * 1000L);
		} catch (Exception e) {

		}
		scheduler.shutdown(true);
	}
}
