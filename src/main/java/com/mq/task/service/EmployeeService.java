package com.mq.task.service;

import com.mq.task.dto.MatchCombinationsResult;
import com.mq.task.entity.Employee;
import com.mq.task.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import util.CsvUtil;
import util.MatcherUtil;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public List<Employee> saveEmployees(MultipartFile reapExcelDataFile)  {
        List<Employee> employeeList;
        try {
            employeeList = CsvUtil.csvToList(reapExcelDataFile.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
        for (Employee employee : employeeList) {
            employeeRepository.save(employee);
        }
        log.info("csv file successfully imported");
        return employeeList;
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public MatchCombinationsResult getMatchResult() {
       return MatcherUtil.constructCombinationsResult(getAllEmployees());
    }


}
