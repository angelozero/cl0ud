package com.angelozero.cl0ud.usecase.utils;

public class LogMessage {

    private LogMessage() {
    }

    /**
     * CreatePerson
     */
    public static final String LOG_INFO_CREATE_PERSON = "\n[CREATING_PERSON] - Creating a person: {}\n";
    public static final String LOG_ERROR_CREATE_PERSON = "\n[ERROR] - Error to create a person\n";

    /**
     * DeletePersonById
     */
    public static final String LOG_INFO_DELETE_PERSON_BY_ID = "\n[DELETE_PERSON_BY_ID] - Deleting a person by id: {}\n";
    public static final String LOG_ERROR_DELETE_PERSON = "\n[ERROR] - Error to delete a person\n";

    /**
     * GetAllPersons
     */
    public static final String LOG_INFO_GET_ALL_PERSONS = "[GET_ALL_PERSONS] - Get a list of persons";
    public static final String LOG_ERROR_GET_ALL_PERSONS = "[GET_ALL_PERSONS] - Get a list of persons";
}
