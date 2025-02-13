package com.quality.heygia;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class loginDemoQA {
	
	private WebDriver driver;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://demoqa.com/login");
	}
	
	@Test
	public void testLoginPage() throws InterruptedException {
		WebElement username = driver.findElement(By.id("userName"));
		username.clear();
		username.sendKeys("test01");
		
		WebElement passwd = driver.findElement(By.id("password"));
		passwd.clear();
		passwd.sendKeys("Test2024*");
		
		WebElement login = driver.findElement(By.id("login"));
		login.click();
		
		Thread.sleep(5000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		 try {
			 WebElement errorMessage = (WebElement) wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
	            String errorText = errorMessage.getText();
	            
	            // Verificar que el mensaje de error sea el esperado
	            if (!errorText.contains("Invalid username or password")) {
	                captureScreenshot("loginError.png");
	                System.out.println("⚠️ Test falló: Mensaje de error inesperado.");
	            } else {
	            	captureScreenshot("loginSucess.png");
	                System.out.println("✅ Test exitoso: Mensaje de error validado correctamente.");
	            }
	        } catch (TimeoutException e) {
	            captureScreenshot("noErrorMessage.png");
	            System.out.println("⚠️ Test falló: No se encontró el mensaje de error.");
	        }
		 
			Thread.sleep(5000);

	}

	@After
	public void teatDown() {
		driver.quit();
	}
	
	private void captureScreenshot(String fileName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File("./src/test/resources/screenshots/" + fileName));
            System.out.println("📸 Captura de pantalla guardada: " + fileName);
        } catch (IOException e) {
            System.out.println("❌ Error al guardar la captura de pantalla: " + e.getMessage());
        }
    }
}
