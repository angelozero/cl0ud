package com.angelozero.cl0ud.usecase.utils;

public class LogMessage {

    private LogMessage() {
    }

    /**
     * DeletePersonById
     */
    public static final String INFO_DELETE_PERSON_BY_ID = "\n[DELETE_PERSON_BY_ID] - Deleting a person by id: {}\n";
    public static final String ERROR_DELETE_PERSON = "\n[ERROR] - Error to delete a person\n";

    /**
     * GetAllPersons
     */
    public static final String INFO_GET_ALL_PERSONS = "\n[GET_ALL_PERSONS] - Get a list of persons\n";
    public static final String ERROR_GET_ALL_PERSONS = "\n[ERROR] - Error to get all persons\n";

    /**
     * GetPersonById
     */
    public static final String INFO_GET_PERSON_BY_ID = "\n[GET_PERSON_BY_ID] - Getting a person by id: {}\n";
    public static final String ERROR_GET_PERSON_BY_ID = "\n[ERROR] - Error to find a person by ID\n";

    /**
     * UpdatePerson
     */
    public static final String INFO_UPDATE_PERSON = "\n[UPDATE_PERSON] - Updating a person: {}\n";
    public static final String ERROR_UPDATE_PERSON = "\n[ERROR] - Error to update a person\n";
}
