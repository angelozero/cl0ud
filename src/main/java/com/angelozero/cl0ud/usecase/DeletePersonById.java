package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.exception.person.DeletePersonException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.usecase.utils.ExceptionMessage;
import com.angelozero.cl0ud.usecase.utils.LogMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class DeletePersonById {

    private final DataBaseGateway dataBaseGateway;
    private final GetPersonById getPersonById;


    public void execute(Long id) {
        log.info("\n[DELETE_PERSON_BY_ID] - Deleting a person by id: {}\n", id);
        Optional.ofNullable(id)
                .orElseThrow(()
                        -> new DeletePersonException("Person ID is null"));

        try {
            if (getPersonById.execute(id) != null) {
                dataBaseGateway.deletePersonEntityById(id);
            }

        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to delete a person\n");
            throw new DeletePersonException(ExceptionMessage.ERROR_DELETE_A_PERSON + ex.getMessage());
        }
    }
}

