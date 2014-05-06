/**
 * CatMacArt Workbench
 * Feb 1, 2006
 * @version 1.0
 */
package com.ctrip.gs.common.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Preserves debugging information by chaining. It provides constructors that take an exception as one of the arguments,
 * and it overrides the printStackTrace() method to print both stack traces. Concrete exception classes need to extend
 * this class and provide desired constructors.
 * 
 */
public class BaseException extends Exception {

    private static final long serialVersionUID = 2004522449987574094L;
    /**
     * <p>
     * Represents the Log Class Object
     * </p>
     */
    private static Logger log = LoggerFactory.getLogger(BaseException.class);

    /**
     * Represents the root cause for the exception
     */
    private Throwable cause = null;

    /**
     * No agrs constructor for the Base Exception class
     * 
     */
    public BaseException() {
        super();
    }

    /**
     * Constructor that initializes the Base Exception with the given String
     * 
     * @param reason
     *            Description of the exception
     * 
     */
    public BaseException(String reason, boolean doLogStack) {
        super(reason);
        logStackTrace(doLogStack);
    }

    public BaseException(String reason) {
        this(reason, true);
    }

    /**
     * Constructor that initializes the Base Exception with the given String and the Exception
     * 
     * @param reason
     *            Description of the exception
     * @param cause
     *            root exception
     */
    public BaseException(String reason, Throwable cause, boolean doLogStack) {
        super(reason, cause);
        this.cause = cause;
        if (cause.getClass().getSuperclass().getName().equals(this.getClass().getSuperclass().getName())) {
            StackTraceElement[] elements = cause.getStackTrace();
            for (int i = 0; i < elements.length; i++) {
                log.error("\tat " + elements[i]);
                if (i == 4) {
                    break;
                }
            }
        } else {
            logStackTrace(doLogStack);
        }
    }

    public BaseException(String reason, Throwable cause) {
        this(reason, cause, true);
    }

    /**
     * Constructor that initializes the Base Exception with the Exception
     * 
     * @param cause
     *            root exception
     */
    public BaseException(Throwable cause, boolean doLogStack) {
        super(cause);
        this.cause = cause;
        if (cause.getClass().getSuperclass().getName().equals(this.getClass().getSuperclass().getName())) {
            StackTraceElement[] elements = cause.getStackTrace();
            for (int i = 0; i < elements.length; i++) {
                log.error("\tat " + elements[i]);
                if (i == 4) {
                    break;
                }
            }
            log.error("\n");
        } else {
            logStackTrace(doLogStack);
        }
    }

    public BaseException(Throwable cause) {
        this(cause, true);
    }

    /**
     * Logs the exception stack trace
     * 
     */
    private void logStackTrace(boolean doLogStack) {
        if (doLogStack) {
            StringWriter sw = new StringWriter();
            printStackTrace(new PrintWriter(sw));
            if (log.isErrorEnabled()) {
                log.error(sw.toString());
            }
        }
    }

    /**
     * extends base class printStackTrace method
     * 
     */
    public void printStackTrace() {
        super.printStackTrace();
        if (cause != null) {
            System.err.println("BaseException Caused by :");
            cause.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("BaseException Caused by :" + cause.getMessage());
            }
        }
    }

    /**
     * extends base class printStackTrace method
     * 
     * @param ps
     *            Print stream
     */
    public void printStackTrace(PrintStream ps) {
        super.printStackTrace(ps);
        if (this.cause != null) {
            ps.println("BaseException Caused by :");
            cause.printStackTrace(ps);
            if (log.isErrorEnabled()) {
                log.error("BaseException Caused by :" + cause.getMessage());
            }
        }
    }

    /**
     * extends base class printStackTrace method
     * 
     * @param pw
     *            Print Writer
     */
    public void printStackTrace(PrintWriter pw) {
        super.printStackTrace(pw);
    }
}
