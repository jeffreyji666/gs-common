package com.ctrip.gs.common.exceptions;

/**
 * Preserves debugging information by chaining. Extends BaseException. This Exception is thrown when Exception occurs in
 * the Services layer.
 * 
 */
public class ServicesException extends BaseException {

    private static final long serialVersionUID = -6125240595762157738L;

    /**
     * Constructor that initializes the RepositoryException with the given String
     * 
     * @param reason
     *            Description of the exception
     * 
     */
    public ServicesException(String reason) {
        super(reason);
    }

    /**
     * Constructor that initializes the ServicesException with the given String and the Exception
     * 
     * @param reason
     *            Description of the exception
     * @param cause
     *            root exception
     */
    public ServicesException(String reason, Throwable cause) {
        super(reason, cause);
    }

    /**
     * Constructor that initializes the ServicesException with the given Exception
     * 
     * @param cause
     *            root exception
     */
    public ServicesException(Throwable cause) {
        super(cause);
    }
}
