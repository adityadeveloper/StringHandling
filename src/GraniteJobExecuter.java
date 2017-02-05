import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.granite.filereader.GraniteDBWriter;
import com.granite.filereader.GraniteFileReader;
import com.granite.model.*;
import com.granite.validation.GraniteDBReader;
import com.granite.validation.GraniteDataValidation;
import com.granite.validation.GraniteFinalDBWriter;

public class GraniteJobExecuter {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis(); System.out.println("\nGranite Data processing started at "+new Timestamp(startTime));
		GraniteFileReader gr = new GraniteFileReader();
		GraniteDBWriter db = new GraniteDBWriter();
		GraniteDBReader reader = new GraniteDBReader();
		GraniteDataValidation validator = new GraniteDataValidation();
		GraniteFinalDBWriter dbf = new GraniteFinalDBWriter();
		List<GraniteVO> graniteCSV = gr.createGraniteList("new1.csv");
		
		try{
			db.insertIntoGraniteTable(graniteCSV);
			List<GraniteVO> dbData =  reader.readFromGraniteTable();
			List<GraniteVO> validatedData = validator.graniteValidator(dbData);
			List<GraniteFinalVO> finalData = validator.graniteFinalListMaker(validatedData);
			dbf.insertIntoFinalGraniteTable(finalData);
		}
		catch (SQLException sq){
			sq.printStackTrace();
		}
		long endTime = System.currentTimeMillis(); System.out.println("\nGranite Data processing completed at "+new Timestamp(endTime)+"\nTotal time taken : "+(endTime-startTime));

	}
}
