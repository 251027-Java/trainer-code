package com.revature.ExpenseReport.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.revature.ExpenseReport.Repository.ReportRepository;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTests {
    
        // Fields
    @Mock
    private ReportRepository repo;

    @InjectMocks
    private ReportService service;


    @Test
    public void testGetReportByIdInvalidId() {
        String id = "1";
        when(repo.findById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid ID"));

        var exception = assertThrows(
            ResponseStatusException.class, 
            () -> service.getById(id)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

    }
}
