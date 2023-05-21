package com.angelozero.cl0ud.usecase.utils;

public class ExceptionMessage {

    private ExceptionMessage() {
    }

    /**
     * CreatePerson
     */

    public static final String ERROR_CREATE_A_PERSONs = "Error to create a person: ";

    /**
     * DeletePersonById / GetPersonById
     */
    public static final String PERSON_ID_IS_NULL = "Person ID is null";
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
