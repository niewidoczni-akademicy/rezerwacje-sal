package org.niewidoczniakademicy.rezerwacje.service.exception;

public class PdfGenerationFailureException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Pdf generation failed";

    public PdfGenerationFailureException() {
        super(EXCEPTION_MESSAGE);
    }

}
