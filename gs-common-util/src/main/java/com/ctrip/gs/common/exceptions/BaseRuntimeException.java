package com.ctrip.gs.common.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author wgji
 * 
 */
public class BaseRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -2595013099192839769L;

    private static Logger log = LoggerFactory.getLogger(BaseException.class);

    private Throwable cause;

    public BaseRuntimeException() {
        super();
        if (log.isDebugEnabled()) {
            logMessage();
        }
    }

    public BaseRuntimeException(String reason) {
        super(reason);
        if (log.isDebugEnabled()) {
            logMessage();
        }
    }

    public BaseRuntimeException(String reason, Throwable cause) {
        super(reason, cause);
        this.cause = cause;
        if (log.isDebugEnabled()) {
            logMessage();
        }
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
        this.cause = cause;
        if (log.isDebugEnabled()) {
            logMessage();
        }
    }

    public void logMessage() {
        log.debug("Exception: " + this.getClass().getName() + ", message: " + this.getMessage());
        if (cause != null) {
            log.debug("Caused by: " + cause.getClass().getName() + ", message: " + cause.getMessage());
        }
    }

    public void logStackTrace() {
        StringWriter sw = new StringWriter();
        printStackTrace(new PrintWriter(sw));
        log.error(sw.toString());
    }

    public void printStackTrace() {
        super.printStackTrace();
        if (cause != null) {
            System.err.println(this.getClass().getName() + " caused by :");
            cause.printStackTrace();
        }
    }

    public void printStackTrace(PrintStream ps) {
        super.printStackTrace(ps);
        if (this.cause != null) {
            ps.println(this.getClass().getName() + " caused by :");
            cause.printStackTrace(ps);
        }
    }

    public void printStackTrace(PrintWriter pw) {
        super.printStackTrace(pw);
        if (this.cause != null) {
            pw.println(this.getClass().getName() + " caused by :");
            cause.printStackTrace(pw);
        }
    }
}
