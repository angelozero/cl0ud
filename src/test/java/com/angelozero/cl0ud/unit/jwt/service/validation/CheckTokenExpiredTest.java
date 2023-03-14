package com.angelozero.cl0ud.unit.jwt.service.validation;

import com.angelozero.cl0ud.jwt.service.validation.ExtractClaim;
import com.angelozero.cl0ud.jwt.service.validation.CheckTokenExpired;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CheckTokenExpiredTest {

    @Mock
    private ExtractClaim extractClaim;

    @InjectMocks
    private CheckTokenExpired checkTokenExpired;


    @DisplayName("Should test if token is not expired")
    @Test
    void testShouldCheckIfTokenIsNotExpired() throws ParseException {

        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

        int year = LocalDateTime.now().minusYears(1).getYear();
        int month = LocalDateTime.now().minusMonths(1).getMonthValue();
        int date = LocalDateTime.now().minusDays(1).getDayOfMonth();

        Date finalDate = dateformat.parse(date + "/" + month + "/" + year);

        when(extractClaim.execute(any(), any())).thenReturn(finalDate);

        assertTrue(checkTokenExpired.execute("JWT test"));
    }

    @DisplayName("Should test if token is expired")
    @Test
    void testShouldCheckIfTokenIsExpired() throws ParseException {

        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

        int year = LocalDateTime.now().plusYears(1).getYear();
        int month = LocalDateTime.now().plusMonths(1).getMonthValue();
        int date = LocalDateTime.now().plusDays(1).getDayOfMonth();

        Date finalDate = dateformat.parse(date + "/" + month + "/" + year);

        when(extractClaim.execute(any(), any())).thenReturn(finalDate);

        assertFalse(checkTokenExpired.execute("JWT test"));
    }
}
