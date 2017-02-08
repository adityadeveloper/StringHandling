import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.granite.filereader.*;
import com.granite.model.*;
import com.granite.validation.*;


public class GraniteJobExecuter implements Job{

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
			System.out.println("No New File Present");
		}
		else{
			long startTime = System.currentTimeMillis(); System.out.println("Granite Data processing started at "+new Timestamp(startTime)+"\nFile Name : "+fileName);
			gr = new GraniteFileReader();
			List<GraniteVO> graniteCSV = gr.createGraniteList(fileName);
			
			try{
				if(graniteCSV.size()>0){
					db = new GraniteDBWriter();
					db.insertIntoGraniteTable(graniteCSV, fileName);
				
					reader = new GraniteDBReader();
					List<GraniteVO> dbData =  reader.readFromGraniteTable();
					
					if (dbData.size()>0){
						validator = new GraniteDataValidation();
						List<GraniteVO> validatedData = validator.graniteValidator(dbData);
						List<GraniteFinalVO> finalData = validator.graniteFinalListMaker(validatedData);
						if(finalData.size()>0){
							dbf = new GraniteFinalDBWriter();
							dbf.insertIntoFinalGraniteTable(finalData);
						}
						else{
							System.out.println("No Records present to insert in places table");
						}
					}
					else{
						System.out.println("No Records present in Granite table");
					}
				}
				else{
					System.out.println("No Records present to insert into Granite table");
				}
			}
			catch (SQLException sq){
				sq.printStackTrace();
			}
			long endTime = System.currentTimeMillis(); System.out.println("\nGranite Data processing completed at "+new Timestamp(endTime)+"\nTotal time taken : "+(endTime-startTime)+" ms");
		}
	}
}
