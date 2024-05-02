package Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import TestComponents.BaseTest;
import pageObjects.CheckoutPage;
import pageObjects.ProductPage;

public class RegisterWhileCheckout extends BaseTest{
@Test(dataProvider="getData")
public void placeOrder(HashMap<String,String> input)
{
	ProductPage productpage = loginPage.productslink();
	productpage.addProductToCart();
	productpage.continueShopping();
	productpage.goTOCart();
	String cartPageTitle = loginPage.getPageTitle();
	Assert.assertTrue(cartPageTitle.equalsIgnoreCase("Automation Exercise - Checkout"));
	loginPage.proceedTocheckout();
	loginPage.registerOrLogin();
	loginPage.NewUserSignup(input.get("userName"), input.get("userEmail"));
	loginPage.registerAccount(input.get("password"),input.get("fName"),input.get("lName"),input.get("add"),input.get("userState"),input.get("userCity"),input.get("code"),input.get("userNumber"));
	loginPage.createAcc();
	String successMsg = loginPage.getMessage();
	Assert.assertTrue(successMsg.equalsIgnoreCase("Account Created!"));
	loginPage.Continue();
	String LoggedName = loginPage.verifyUser();
	Assert.assertTrue(LoggedName.equalsIgnoreCase("Logged in as Sana"));
	productpage.goTOCart();
	CheckoutPage checkoutpage = loginPage.proceedTocheckout();
	String add = checkoutpage.getAddressTxt();
	Assert.assertTrue(add.equalsIgnoreCase("Address Details"));
	String review = checkoutpage.getReviewTxt();
	Assert.assertTrue(review.equalsIgnoreCase("Review Your Order"));
	checkoutpage.enterComment(input.get("Message"));
	loginPage.proceedTocheckout();
	checkoutpage.cardDetails(input.get("cardName"),input.get("cardNumber"),input.get("cvv"),input.get("expMonth"),input.get("expYear"));
	String successMsg2 = checkoutpage.getOrderSuccessMsg();
	Assert.assertTrue(successMsg2.equalsIgnoreCase("Congratulations! Your order has been confirmed!"));
	checkoutpage.downloadInvoice();
	checkoutpage.continueButton();
	loginPage.DeleteAcc();
	String dltTxt = loginPage.AccountDeletion();
	Assert.assertTrue(dltTxt.equalsIgnoreCase("Account Deleted!"));	
}
@DataProvider
public Object[][] getData() throws IOException
{
	List<HashMap<String,String>> data = getJsonData(System.getProperty("user.dir")+"\\src\\test\\java\\data\\UserInfo.json");
	return new Object[][] {{data.get(0)}};
	
}
}
