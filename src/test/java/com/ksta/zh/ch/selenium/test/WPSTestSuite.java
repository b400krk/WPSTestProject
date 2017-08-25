package com.ksta.zh.ch.selenium.test;

import org.testng.annotations.Test;

import com.ksta.zh.ch.selenium.DriverFactory;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeSuite;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;

public class WPSTestSuite extends DriverFactory {

	private String baseUrl = "https://int2.sam.ktzh.ch/";
	private String isiwebuserid = "wpstestuser2";
	private String isiwebpasswd = "123Wp$_2014";
	private int timeout = 10;
	private static WebDriver driver;


	@BeforeSuite
	private WebDriver login() throws Exception {
		driver = DriverFactory.getDriver();
		driver.get(baseUrl + "/wps/home.seam");
		driver.findElement(By.name("isiwebuserid")).clear();
		driver.findElement(By.name("isiwebuserid")).sendKeys(isiwebuserid);
		driver.findElement(By.name("isiwebpasswd")).clear();
		driver.findElement(By.name("isiwebpasswd")).sendKeys(isiwebpasswd);
		driver.findElement(By.xpath("//div[@id='loginSubmitButton']/input")).click();
		getWhenVisible(By.xpath("//table[@id='valor_title_tab_id_shifted']/tbody/tr/td[2]/table/tbody/tr/td"));
		return driver;
	}

	@Test
	public void getVal() throws Exception {
		driver.get(baseUrl + "/wps/home.seam");
		assertEquals("Kurslisten-Titel suchen", getWhenVisible(By.xpath("//form[@id='valorSearchForm']/h4")).getText());
	    driver.findElement(By.xpath("//input[@id='valorSearchForm:searchTitleValidation:searchTitle']")).clear();
	    driver.findElement(By.xpath("//input[@id='valorSearchForm:searchTitleValidation:searchTitle']")).sendKeys("qiagen");
	    clickWhenReady(By.xpath("//input[@id='valorSearchForm:search']"));
	    waitForElementPresent(By.xpath("//td[@id='valor_title_tab_id']/table/tbody/tr/td/h4"));
	    assertEquals("Qiagen N.V., Venlo NL, Namenaktie", getWhenVisible(By.xpath("//span[@id='valorInfo_titleValue']")).getText());

	}

	private WebElement getWhenVisible(By finder) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(finder));
		return element;
	}

	private void clickWhenReady(By finder) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(finder));
		element.click();
	}
	private void waitForElementPresent (By finder) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.elementToBeClickable(finder));
	}
}
