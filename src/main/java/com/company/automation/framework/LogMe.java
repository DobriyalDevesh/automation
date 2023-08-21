package com.company.automation.framework;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LogMe {
	private static Logger LOGGER;
	private static ExtentTest extentTest;

	public static ExtentTest getExtentTest() {
		return extentTest;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public LogMe(String loggerClass) {
		LOGGER = Logger.getLogger(loggerClass);
	}

	@SuppressWarnings("rawtypes")
	public LogMe(Class loggerClass) {
		LOGGER = Logger.getLogger(loggerClass);
	}

	public void logInfo(String message) {
		LOGGER.info("---INFO--- " + message);

		if (extentTest != null) {
			extentTest.log(LogStatus.INFO, "---INFO--- " + message);
		}
	}

	public void logInfo(String message, Throwable t) {
		LOGGER.info("---INFO--- " + message, t);

		if (extentTest != null) {
			extentTest.log(LogStatus.INFO, "---INFO--- " + message + ". Exception message is " + t.getMessage());
		}
	}

	public void logWarn(String message) {
		LOGGER.warn("---WARN--- " + message);

		if (extentTest != null) {
			extentTest.log(LogStatus.WARNING, "---WARN--- " + message);
		}
	}

	public void logWarn(String message, String screenshotPath) {
		LOGGER.warn("---WARN--- " + message);

		if (extentTest != null) {
			extentTest.log(LogStatus.WARNING, "---WARN--- " + message + extentTest.addScreenCapture(screenshotPath));
		}
	}

	public void logWarn(String message, Throwable t, String... screenshotPath) {
		LOGGER.warn("---WARN--- " + message, t);

		if (extentTest != null) {
			if (screenshotPath == null) {
				extentTest.log(LogStatus.WARNING, "---WARN--- " + message + ". Exception message is " + t.getMessage());
			} else {
				extentTest.log(LogStatus.WARNING, "---WARN--- " + message + ". Exception message is " + t.getMessage()
						+ extentTest.addScreenCapture(screenshotPath[0]));
			}
		}
	}

	public void logError(String message) {
		LOGGER.error("---ERROR--- " + message);

		if (extentTest != null) {
			extentTest.log(LogStatus.FAIL, "---ERROR--- " + message);
		}
	}

	public void logError(String message, String screenshotPath) {
		LOGGER.error("---ERROR--- " + message);

		if (extentTest != null) {
			extentTest.log(LogStatus.FAIL, "---ERROR--- " + message + extentTest.addScreenCapture(screenshotPath));
		}
	}

	public void logError(String message, Throwable t, String... screenshotPath) {
		LOGGER.error("---ERROR--- Exception " + t.getClass().getSimpleName() + " encountered");
		LOGGER.error("---ERROR--- " + message, t);

		if (extentTest != null) {
			if (screenshotPath == null) {
				extentTest.log(LogStatus.FAIL, "---ERROR--- " + message + ". Exception message is " + t.getMessage());
			} else {
				extentTest.log(LogStatus.FAIL, "---ERROR--- " + message + ". Exception message is " + t.getMessage()
						+ extentTest.addScreenCapture(screenshotPath[0]));
			}
		}
	}

	public void logAssert(boolean assertion, String msg) {
		if (assertion) {
			msg = "---INFO--- " + msg;

			if (extentTest != null) {
				extentTest.log(LogStatus.INFO, msg);
			}
		} else {
			msg = "---ERROR--- " + msg;

			if (extentTest != null) {
				extentTest.log(LogStatus.ERROR, msg);
			}
		}
	}

	public void logBeginTestCase(String tsName) {
		LOGGER.info("---INFO---Starting test case " + tsName);
		extentTest = ExtentManager.getInstance().startTest(tsName);
	}

	public void logBeginTestCase(String tsName, String desc) {
		LOGGER.info("---INFO---Strating Test Case : " + tsName);
		LOGGER.info("---INFO---Test Case Description : " + desc);
		extentTest = ExtentManager.getInstance().startTest(tsName, desc);
	}

	public void logEndTestCase(String tsName) {
		LOGGER.info("---INFO---Test Case : " + tsName + " finished");
		ExtentManager.getInstance().endTest(extentTest);
	}

	public void logTestStep(String status, String message, Throwable... throwables) throws IOException {
		LogStatus stepStatus = LogStatus.valueOf(status.toUpperCase());

		switch (stepStatus) {
		case INFO:
			if (throwables.length != 0) {
				LOGGER.info("---INFO--- " + message + " Exception " + throwables[0].getClass().getSimpleName()
						+ " occured");
				extentTest.log(LogStatus.INFO, "---INFO--- " + message + " Exception "
						+ throwables[0].getClass().getSimpleName() + " occured");
			} else {
				LOGGER.info("---INFO---" + message);
				extentTest.log(LogStatus.INFO, "---INFO---" + message);
			}
			break;
		case PASS:
			if (throwables.length != 0) {
				LOGGER.info(
						"---PASS---" + message + " Exception " + throwables[0].getClass().getSimpleName() + " occured");
				extentTest.log(LogStatus.PASS,
						"---PASS---" + message + " Exception " + throwables[0].getClass().getSimpleName() + " occured");
			} else {
				LOGGER.info("---PASS---" + message);
				extentTest.log(LogStatus.PASS, "---PASS---" + message);
			}
			break;
		case FAIL:
			if (throwables.length != 0) {
				logError("---FAIL---" + message, throwables[0]);
			} else {
				logError("---FAIL---" + message);
			}
			break;
		case SKIP:
		case WARNING:
			if (throwables.length != 0) {
				logWarn("---WARNING---" + message, throwables[0]);
			} else {
				logWarn("---WARNING---" + message);
			}
			break;
		default:
			break;
		}
	}

	public void logTestStep(String status, String message, String filePath, Throwable... throwables) {
		LogStatus stepStatus = LogStatus.valueOf(status.toUpperCase());

		String split[] = StringUtils.split(filePath, "\\");
		filePath = split[split.length - 1];

		switch (stepStatus) {
		case INFO:
			if (throwables.length != 0) {
				LOGGER.info(
						"---INFO---" + message + " Exception " + throwables[0].getClass().getSimpleName() + " occured");
				extentTest.log(LogStatus.INFO,
						"---INFO---" + message + " Exception " + throwables[0].getClass().getSimpleName() + " occured"
								+ extentTest.addScreenCapture(filePath));
			} else {
				LOGGER.info("---INFO---" + message);
				extentTest.log(LogStatus.INFO, "---INFO---" + message + extentTest.addScreenCapture(filePath));
			}
			break;
		case PASS:
			if (throwables.length != 0) {
				LOGGER.info(
						"---PASS---" + message + " Exception " + throwables[0].getClass().getSimpleName() + " occured");
				extentTest.log(LogStatus.PASS,
						"---PASS---" + message + " Exception " + throwables[0].getClass().getSimpleName() + " occured"
								+ extentTest.addScreenCapture(filePath));
			} else {
				LOGGER.info("---PASS---" + message);
				extentTest.log(LogStatus.PASS, "---PASS---" + message + extentTest.addScreenCapture(filePath));
			}
			break;
		case FAIL:
			if (throwables.length != 0) {
				logError("---FAIL---" + message, throwables[0], filePath);
			} else {
				logError("---FAIL---" + message, filePath);
			}
			break;
		case SKIP:
		case WARNING:
			if (throwables.length != 0) {
				logWarn("---WARNING---" + message, throwables[0], filePath);
			} else {
				logWarn("---WARNING---" + message, filePath);
			}
			break;
		default:
			break;
		}
	}
}