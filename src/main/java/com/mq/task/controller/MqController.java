package com.mq.task.controller;

import com.mq.task.dto.ErrorResponse;
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

import static util.CsvUtil.hasCSVFormat;

@RestController()
@RequestMapping("/mq")
@RequiredArgsConstructor
public class MqController {

    final EmployeeService employeeService;

    @CrossOrigin
    @PostMapping("/import")
    public ResponseEntity<?> importCsv(@RequestParam("file") MultipartFile reapExcelDataFile
    )  {
        if (hasCSVFormat(reapExcelDataFile)) {
            List employeeLIst = employeeService.saveEmployees(reapExcelDataFile);
            ResponseEntity<List<Employee>> listResponseEntity = new ResponseEntity<>(employeeLIst, HttpStatus.OK);
            return listResponseEntity;
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Please upload a csv file!"));
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
