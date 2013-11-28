package com.iteye.wwwcomy.quartz.util;

import java.text.ParseException;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

/**
 * TODO 此类待测试
 * 
 * @author wwwcomy
 * 
 */
public class QuartzManager {
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = "HAIYAN_JOBGROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "HAIYAN_TRIGGERGROUP_NAME";

	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobClass
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void addJob(String jobName, Class<? extends Job> jobClass, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();

			// 触发器
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME)
					.withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
			sched.scheduleJob(jobDetail, trigger);
			// 启动
			if (!sched.isShutdown())
				sched.start();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param jobClass
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
			Class<? extends Job> jobClass, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
			// 触发器
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME)
					.withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
			sched.scheduleJob(jobDetail, trigger);
		} catch (Throwable e) {

			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @param time
	 */
	public static void modifyJobTime(String jobName, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			TriggerKey key = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
			CronTrigger trigger = (CronTrigger) sched.getTrigger(key);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);
				JobDetail jobDetail = sched.getJobDetail(jobKey);
				Class<? extends Job> jobClass = jobDetail.getJobClass();

				removeJob(jobName);

				addJob(jobName, jobClass, time);
			}
		} catch (Throwable e) {

			throw new RuntimeException(e);
		}
	}

	/**
	 * TODO 这个方法待测试
	 * 
	 * 修改一个任务的触发时间
	 * 
	 * @param triggerName
	 * @param triggerGroupName
	 * @param time
	 */
	public static void modifyJobTime(String triggerName, String triggerGroupName, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			TriggerKey key = new TriggerKey(triggerName, triggerGroupName);
			CronTrigger trigger = (CronTrigger) sched.getTrigger(key);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				// TODO 这里待测试
				@SuppressWarnings("unused")
				CronTrigger ct = TriggerBuilder.newTrigger().withIdentity(triggerName, TRIGGER_GROUP_NAME)
						.withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
				// 重启触发器
				TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
				sched.resumeTrigger(triggerKey);
			}
		} catch (Throwable e) {

			throw new RuntimeException(e);
		}
	}

	/**
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 */
	public static void removeJob(String jobName) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();

			TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
			JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);

			sched.pauseTrigger(triggerKey);// 停止触发器
			sched.unscheduleJob(triggerKey);// 移除触发器
			sched.deleteJob(jobKey);// 删除任务
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 移除一个任务
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 */
	public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();

			TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
			JobKey jobKey = new JobKey(jobName, jobGroupName);

			sched.pauseTrigger(triggerKey);// 停止触发器
			sched.unscheduleJob(triggerKey);// 移除触发器
			sched.deleteJob(jobKey);// 删除任务
		} catch (Throwable e) {

			throw new RuntimeException(e);
		}
	}

	/**
	 * 启动所有定时任务
	 */
	public static void startJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.start();
		} catch (Throwable e) {

			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭所有定时任务
	 */
	public static void shutdownJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Throwable e) {

			throw new RuntimeException(e);
		}
	}
}
