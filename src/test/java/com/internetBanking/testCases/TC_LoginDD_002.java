package com.internetBanking.testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.internetBanking.pageObjects.LoginPage;
import com.internetBanking.utilities.XLUtils;

public class TC_LoginDD_002 extends BaseClass{


	@Test(dataProvider="LoginData")
	public void loginDDT(String username, String password)
	{
		logger.info("Website launched");
		LoginPage lp = new LoginPage(driver);
		lp.setUserName(username);
		logger.info("Username provided");
		lp.setPassword(password);
		logger.info("Password provided");
		lp.clickSubmit();
		if(isAlertPresent()) {
			driver.switchTo().alert().accept();
			logger.warn("Username or Password is invalid");
			Assert.assertTrue(false);
			driver.switchTo().defaultContent();
			logger.info("Control passed to main page");
		}
		else {
			logger.info("Login was successful");
			lp.clickLogout();
			Assert.assertTrue(true);
			logger.info("Logout performed");
			driver.switchTo().alert().accept();
			logger.info("Logout alert is accepted");
			driver.switchTo().defaultContent();
		}

	}

	public boolean isAlertPresent() {
		try
		{
			driver.switchTo().alert();
			logger.info("Alert is present");
			return true;
		}
		catch(Exception e) {
			logger.info("Alert not present");
			return false;
		}
	}

	@DataProvider(name="LoginData")
	String [][] getData() throws IOException
	{
		String path=System.getProperty("user.dir")+"/src/test/java/com/internetBanking/testData/LoginData.xlsx";
		int rownum=XLUtils.getRowCount (path, "Sheet1");
		int colcount=XLUtils.getCellCount (path, "Sheet1", 1);
		String logindata[][]=new String[rownum][colcount];
		for(int i=1;i<=rownum; i++)
		{
			for(int j=0; j<colcount; j++)
			{
				logindata[i-1][j]=XLUtils.getCellData(path, "Sheet1", i,j);//1 0
			}
		}
		return logindata;
	}
}
