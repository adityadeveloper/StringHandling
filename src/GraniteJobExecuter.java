import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.granite.filereader.GraniteDBWriter;
import com.granite.filereader.GraniteFileReader;
import com.granite.filereader.GraniteFileSearch;
import com.granite.model.*;
import com.granite.validation.GraniteDBReader;
import com.granite.validation.GraniteDataValidation;
import com.granite.validation.GraniteFinalDBWriter;

public class GraniteJobExecuter {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis(); System.out.println("Granite Data processing started at "+new Timestamp(startTime));
		GraniteFileReader gr = new GraniteFileReader();
		GraniteDBWriter db = new GraniteDBWriter();
		GraniteDBReader reader = new GraniteDBReader();
		GraniteDataValidation validator = new GraniteDataValidation();
		GraniteFinalDBWriter dbf = new GraniteFinalDBWriter();
		GraniteFileSearch gfs = new GraniteFileSearch();
		
		String fileName = gfs.graniteFilePicker();
		
		if (fileName == null){
			System.out.println("No New File Present");
		}
		else{
			List<GraniteVO> graniteCSV = gr.createGraniteList(fileName);
			
			try{
				db.insertIntoGraniteTable(graniteCSV, fileName);
				List<GraniteVO> dbData =  reader.readFromGraniteTable();
				List<GraniteVO> validatedData = validator.graniteValidator(dbData);
				List<GraniteFinalVO> finalData = validator.graniteFinalListMaker(validatedData);
				dbf.insertIntoFinalGraniteTable(finalData);
			}
			catch (SQLException sq){
				sq.printStackTrace();
			}
			long endTime = System.currentTimeMillis(); System.out.println("\nGranite Data processing completed at "+new Timestamp(endTime)+"\nTotal time taken : "+(endTime-startTime)+" ms");
		}
	}
}
