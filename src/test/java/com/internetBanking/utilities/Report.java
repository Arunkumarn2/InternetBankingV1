package com.internetBanking.utilities;


//Listener class used to generate Extent reports
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Report extends TestListenerAdapter  {


	public static ExtentReports extent;
	public static ExtentSparkReporter spark;
	public static ExtentTest log1;

	public void onTestStart(ITestResult result) {

		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //time stamp
		String repName="Test-Report-"+timestamp+".html";
		//Reporter=new ExtentHtmlReporter(System.getProperty("user.dir")+ "/test-output/"+repName);//specify location
		//htmlReporter.loadXML Config(System.getProperty("user.dir")+ "/extent-config.xml");
		extent =new ExtentReports();
		spark = new ExtentSparkReporter(System.getProperty("user.dir")+ "/test-output/"+repName);
		extent.attachReporter(spark);
		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("Environemnt", "QA");
		extent.setSystemInfo("user", "Arun");
		spark.config().setDocumentTitle("Internet Banking Test Project"); // Tile of report
		spark.config().setReportName("Functional Test Report"); // name of the report
		//spark.config().setTestViewChartLocation(ChartLocation.); //location of the chart
		spark.config().setTheme(Theme.DARK);
	}
	public void onTestSuccess (ITestResult tr) {

		log1=extent.createTest(tr.getName()); // create new entry in th report
		log1.log(Status.PASS,MarkupHelper.createLabel(tr.getName(),ExtentColor.GREEN)); // send the passed information
	}
	public void onTestFailure (ITestResult tr) {

		log1=extent.createTest (tr.getName()); // create new entry in th report
		log1.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED)); // send the passed information t
		String screenshotPath=System.getProperty("user.dir")+"\\Screenshots\\"+tr.getName()+".png";
		File f = new File(screenshotPath);
		if(f.exists())
		{
			log1.fail("Screenshot is below:" + log1.addScreenCaptureFromPath(screenshotPath));
		}
	}
	public void onTestSkipped (ITestResult tr) {
		log1=extent.createTest(tr.getName()); // create new entry in th report
		log1.log(Status.SKIP,MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
	}
	public void onFinish (ITestContext testContext)
	{
		System.out.println("done");
		extent.flush();
	}

}

 