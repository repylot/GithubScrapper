package org.repylot.controller.scrapper.exceptions;

public class ElementNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "The element has not been found on the given document";
    }
}
