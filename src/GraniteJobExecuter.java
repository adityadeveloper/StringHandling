import java.sql.SQLException;
import java.util.List;

import com.granite.filereader.GraniteDBWriter;
import com.granite.filereader.GraniteFileReader;
import com.granite.model.GraniteVO;
import com.granite.validation.GraniteDBReader;
import com.granite.validation.GraniteDataValidation;
import com.granite.validation.GraniteFinalDBWriter;

public class GraniteJobExecuter {

	public static void main(String[] args) {
		GraniteFileReader gr = new GraniteFileReader();
		GraniteDBWriter db = new GraniteDBWriter();
		GraniteDBReader reader = new GraniteDBReader();
		GraniteDataValidation validator = new GraniteDataValidation();
		GraniteFinalDBWriter dbf = new GraniteFinalDBWriter();
		
		List<GraniteVO> graniteCSV = gr.createGraniteList("wifi_ap_141020161848.csv");
		
		try{
			db.insertIntoGraniteTable(graniteCSV);
		}
		
		catch(SQLException sq){
			sq.getMessage();
			sq.printStackTrace();
		}
		
		try{
			List<GraniteVO> dbData =  reader.readFromGraniteTable();
			List<GraniteVO> validatedData = validator.graniteValidator(dbData);
			
			try{
				dbf.insertIntoFinalGraniteTable(validatedData);
			}
			
			catch(SQLException sq){
				sq.getMessage();
				sq.printStackTrace();
			}
		}
		
		catch (SQLException sq){
			sq.printStackTrace();
		}
	}

}
