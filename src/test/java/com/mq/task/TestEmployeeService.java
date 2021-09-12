package com.mq.task;

import com.mq.task.repository.EmployeeRepository;
import com.mq.task.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEmployeeService {


    private final static String csv = "Name,Email,Division,Age,Timezone\n" +
            "Gabrielle Clarkson,tamas@me_example.com,Accounting,25,2\n" +
            "Zoe Peters,gozer@icloud_example.com,Finance,30,3\n" +
            "Jacob Murray,lstein@me_example.com,Accounting,22,2\n" +
            "Nicholas Vance,saridder@outlook_example.com,Engineering,27,-3";

    @InjectMocks
    EmployeeService employeeService;

    @Mock
    EmployeeRepository employeeRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() throws IOException {
        String fileName = "employee.csv";
        MockMultipartFile multipartFile =
                new MockMultipartFile("csv", fileName, "text/csv", csv.getBytes());
      List x =  employeeService.saveEmployees(multipartFile);
      assertEquals(x.size(), 4);


    }
}
