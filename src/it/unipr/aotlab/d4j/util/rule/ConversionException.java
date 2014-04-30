package it.unipr.aotlab.d4j.util.rule;

/**
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: ConversionException.java,v 1.1 2004/10/01 15:24:07 mic Exp $
 */
public class ConversionException extends Exception {

    public ConversionException(String message) {
        super(message);
    }

    public ConversionException(Throwable cause) {
        super(cause);
    }

}
