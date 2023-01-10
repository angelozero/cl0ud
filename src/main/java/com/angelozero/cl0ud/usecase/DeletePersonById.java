package com.angelozero.cl0ud.usecase;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class DeletePersonById {

    public void execute(int id) {
        log.info("[CLOUD-APP] - Deleting a person by id: {}", id);
    }
}

