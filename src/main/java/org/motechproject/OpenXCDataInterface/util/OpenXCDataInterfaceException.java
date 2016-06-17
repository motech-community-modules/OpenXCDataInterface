package org.motechproject.OpenXCDataInterface.util;

import java.lang.Exception;

/**
 * Class Name : OpenXCDataInterfaceException
 * Purpose    : Custom Exception Class for this Module.
 */

    public class OpenXCDataInterfaceException extends Exception {

        public OpenXCDataInterfaceException(String message) {
            super(message);
        }

        public OpenXCDataInterfaceException(String message, Throwable cause) {
            super(message, cause);
        }

    }
