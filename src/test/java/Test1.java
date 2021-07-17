import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.qa.api.handler.TestHandler;
import com.qa.api.utils.ExcelUtil;
import com.qa.api.utils.Reusable;

import io.qameta.allure.Description;

public class Test1 {

	Reusable util=new Reusable();
	ExcelUtil excel=new ExcelUtil();
	TestHandler handler=new TestHandler();
	private static Map<String,HashMap<String,String>> createSheetDataMap;
	private static String postTemplate;


	@DataProvider(name="postCallData")
	public Object[][] postDataCall(){
		return excel.getExcelDataMap(createSheetDataMap);
	}

	@BeforeClass	
	public void loadUtils() throws IOException {


		try {
			createSheetDataMap = util.getTestInputDataMap("featureRes_excelPath", "postData");
			postTemplate = util.getTestJsonPath("featureRes_jsonPath");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(priority=1,dataProvider="postCallData")
	@Description("post call test")
	public void postTesS(Map<String,HashMap<String,String>> postExcelDataSet) throws Exception {
	Map<String,String> postDataSet=	util.getTestCaseRecordValuesMap(postExcelDataSet);
	
	/*if(postDataSet.containsKey("binNumber") && StringUtils.equalsIgnoreCase(postDataSet.get("binNumber"), "RANDOM_NUMBER")){
		postDataSet.put("binNumber", String.valueOf(util.randomNumber()));
	}*/
	handler.validatePostCall("api/users", postExcelDataSet, postTemplate, null, null, postDataSet);
	

	}
}
