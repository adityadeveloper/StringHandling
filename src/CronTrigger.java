import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import com.configurations.ConfigReader;

public class CronTrigger{

	private static final String CRON_STATEMENT;

	static{
		CRON_STATEMENT = ConfigReader.getInstance().getCron_statement();
	}
    public static void main( String[] args ) throws Exception
    {
    	JobDetail job = JobBuilder.newJob(GraniteJobExecuter.class)
		.withIdentity("GraniteJob", "LBSDataIntegration").build();


    	Trigger trigger = TriggerBuilder
		.newTrigger()
		.withIdentity("GraniteJobTrigger", "LBSDataIntegration")
		.withSchedule(
			CronScheduleBuilder.cronSchedule(CRON_STATEMENT))
		.build();

    	//schedule it
    	Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    	scheduler.start();
    	scheduler.scheduleJob(job, trigger);

    }
}
