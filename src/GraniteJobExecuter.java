import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.granite.filereader.*;
import com.granite.model.*;
import com.granite.validation.*;



public class GraniteJobExecuter implements Job{

	static Logger logger = Logger.getLogger(GraniteFileSearch.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		GraniteJobExecuter.mainJob();
	}
	
	public static void main(String args[]){
		 GraniteJobExecuter.mainJob();
	}
	
	public static void mainJob(){
		
		GraniteFileReader gr = null;
		GraniteDBWriter db = null;
		GraniteDBReader reader = null;
		GraniteDataValidation validator = null;
		GraniteFinalDBWriter dbf = null;
		GraniteFileSearch gfs = new GraniteFileSearch();
		
		String fileName = gfs.graniteFilePicker();
		
		if (fileName == null){
			logger.info("No New File Present");
		}
		else{
			long startTime = System.currentTimeMillis(); 
			logger.info("New Granite File found : "+fileName);
			logger.info("Phase - 1 started");
			gr = new GraniteFileReader();
			List<GraniteVO> graniteCSV = gr.createGraniteList(fileName);
			
			try{
				if(graniteCSV.size()>0){
					db = new GraniteDBWriter();
					db.insertIntoGraniteTable(graniteCSV, fileName);
					logger.info("Phase - 1 completed");
					reader = new GraniteDBReader();
					logger.info("Phase - 2 started");
					List<GraniteVO> dbData =  reader.readFromGraniteTable();
					
					if (dbData.size()>0){
						logger.info("Phase - 3 started");
						validator = new GraniteDataValidation();
						List<GraniteVO> validatedData = validator.graniteValidator(dbData);
						List<GraniteFinalVO> finalData = validator.graniteFinalListMaker(validatedData);
						if(finalData.size()>0){
							dbf = new GraniteFinalDBWriter();
							dbf.insertIntoFinalGraniteTable(finalData);
							logger.info("Phase - 3 completed");
						}
						else{
							logger.info("No Records present to insert in places table");
						}
					}
					else{
						logger.info("No Records present in Granite table");
					}
				}
				else{
					logger.info("No Records present to insert into Granite table");
				}
			}
			catch (SQLException sq){
				sq.printStackTrace();
			}
			long endTime = System.currentTimeMillis(); 
			logger.info("Granite Data processing completed"+new Timestamp(endTime)+"\nTotal time taken : "+(endTime-startTime)+" ms");
		}
	}
}
