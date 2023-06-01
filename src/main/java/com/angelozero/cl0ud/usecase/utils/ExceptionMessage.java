package com.angelozero.cl0ud.usecase.utils;

public class ExceptionMessage {

    private ExceptionMessage() {
    }

    /**
     * DeletePersonById / GetPersonById
     */

    public static final String ERROR_DELETE_A_PERSON = "Error to delete a person: ";

    /**
     * GetAllPersons
     */
    public static final String ERROR_GET_ALL_PERSONS = "Error to get all persons: ";


    /**
     * GetPersonById
     */
    public static final String ERROR_FIND_A_PERSON_BY_ID = "Error to find a person by ID: ";

    /**
     * UpdatePerson
     */
    public static final String ERROR_PERSON_DATA_OR_ID = "Person Data and/or Person ID is null";
    public static final String ERROR_UPDATE_PERSON = "Error to update a person: ";

}
