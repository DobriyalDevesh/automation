package com.company.automation;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IRetryAnalyzer;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.TestListenerAdapter;

import com.company.automation.framework.LogMe;

/**
 * @author ADhuria Class to listen for any skip exception and on basis skip run
 *         and set result for test .
 *
 */
public class TestResultRunControllerList extends TestListenerAdapter
		implements ITestListener, IInvokedMethodListener, IRetryAnalyzer {

	LogMe LOGGER = new LogMe(TestResultRunControllerList.class);
	private static boolean istestSkipped = false;
	private static boolean skipTestRun = false;
	private static ITestNGMethod testMethod;

	private static int retryCount = 0;
	private static final int maxRetryCount = 3;

	public void onTestFailure(ITestResult result) {

		Object[] parameters = result.getParameters();
		String name = result.getName();

		if (parameters == null || parameters.length == 0) {
			System.out.println(result.getName() + " :  test case has been Failed ");
		}

		else {
			System.out.println(result.getParameters()[0] + " :  test case has been Failed ");
		}

	}

	public void onTestSkipped(ITestResult result) {
		testMethod = result.getMethod();
		if (!istestSkipped) {
			LOGGER.logInfo("Test iteration/case-" + result.getName() + "-" + result.getParameters()[0]
					+ " has been Skipped and Set to Fail");
			istestSkipped = true;
			result.setStatus(ITestResult.FAILURE);
// 	        System.out.println(result.getName() + " : skipping and setting status to fail");
			if (result.getThrowable() instanceof SkipException) {
				skipTestRun = true;
			}
//	        istestSkipped=true;
//	        result.setStatus(ITestResult.FAILURE);
//	        System.out.println(result.getName() + " : skipping and setting status to fail");
//	        //result.getThrowable().printStackTrace();
//        	}
		} else
			result.setStatus(ITestResult.SKIP);
	}

	public void onTestSuccess(ITestResult result) {
		LOGGER.logInfo(
				"Test iteration/case " + result.getName() + "-" + result.getParameters()[0] + " has been Passed ");
		System.out.println(result.getName() + " :  test case has been Passed ");
	}

	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult result) {
	}
	// checking if a test is already been skipped and trying to iterate on different
	// data provider
//		if (method.isTestMethod() && istestSkipped && method.getTestMethod().equals(testMethod)) {
//		Object[] paramters=result.getParameters();
//	       for(Object param:paramters) {
//	        	if(!(param==null))
//	        		skipTestRun=true ;
//	        		break;
//	        		}
//		
//		}else {
//			skipTestRun=false ;}

//	@Override
//	public void run(IHookCallBack callBack, ITestResult result) {
//		if(skipTestRun) {
////			result.setThrowable(new SkipException("Skipped through last Iteration"));
//			System.out.println(result.getMethod());
//			result.setStatus(ITestResult.FAILURE);
//			skipTestRun=false;
//		}else {
//			callBack.runTestMethod(result);
//			istestSkipped=false;}
//	} 

	@Override
	public boolean retry(ITestResult result) {
		if (!result.isSuccess()) { // Check if test not succeed
			if (retryCount < maxRetryCount && !skipTestRun) { // Check if maxtry count is reached
				retryCount++;
				LOGGER.logInfo("Retrying same Test Iteration/Case - " + retryCount);// Increase the maxTry count by 1
				// result.setStatus(ITestResult.FAILURE); //Mark test as failed
				return true; // Tells TestNG to re-run the test
			} else {
				retryCount = 0;
				// result.setStatus(ITestResult.FAILURE); //If maxCount reached,test marked as
				// failed
			}
		}
//        } else {
//        	//result.setStatus(ITestResult.SUCCESS);      //If test passes, TestNG marks it as passed
//        }
		return false;
	}

	public static int getRetryCount() {
		return retryCount;
	}
//	@Override
//	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
//		String dbConnection = TestConfig.getConfig().getPropertyValue("dbConnection");
//		if(dbConnection.equalsIgnoreCase("Yes")) {
//			if(testMethod.getName().equalsIgnoreCase("duplicateSearch_DL")) {
//				annotation.setEnabled(false);
//			}
//		}else {
//			if(testMethod.getName().equalsIgnoreCase("duplicateSearch_Db")) {
//				annotation.setEnabled(false);
//			}
//		}
//		
//	}
}
