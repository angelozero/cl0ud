package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.exception.exs.DeletePersonException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class DeletePersonById {

    private final GetPersonById getPersonById;
    private final DataBaseGateway dataBaseGateway;


    public void execute(Long id) {
        log.info("[DELETE_PERSON_BY_ID] - Deleting a person by id: {}", id);

        try {
            if (getPersonById.execute(id) != null) {
                dataBaseGateway.deletePersonEntityById(id);
            }

        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to delete a person\n");
            throw new DeletePersonException("[ERROR] - Error to delete a person");
        }
    }
}

