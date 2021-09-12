package com.mq.task.controller;

import com.mq.task.dto.MatchCombinationsResult;
import com.mq.task.entity.Employee;
import com.mq.task.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("/mq")
@RequiredArgsConstructor
public class MqController {

    final EmployeeService employeeService;

    @CrossOrigin
    @PostMapping("/import")
    public ResponseEntity<List<Employee>> importCsv(@RequestParam("file") MultipartFile reapExcelDataFile
    ) throws IOException {
        List employeeLIst  = employeeService.saveEmployees(reapExcelDataFile);
        ResponseEntity<List<Employee>> listResponseEntity = new ResponseEntity<>(employeeLIst, HttpStatus.OK);
        return listResponseEntity;
    }

    @CrossOrigin
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        List employeeLIst  = employeeService.getAllEmployees();
        ResponseEntity<List<Employee>> listResponseEntity = new ResponseEntity<>(employeeLIst, HttpStatus.OK);
        return listResponseEntity;
    }

    @CrossOrigin
    @GetMapping("/matchResult")
    public ResponseEntity<MatchCombinationsResult> getMatchResult() {
        MatchCombinationsResult matchCombinationsResult  = employeeService.getMatchResult();
        ResponseEntity<MatchCombinationsResult> listResponseEntity = new ResponseEntity<>(matchCombinationsResult, HttpStatus.OK);
        return listResponseEntity;
    }
}
