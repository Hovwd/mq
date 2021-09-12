package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import com.mq.task.entity.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CsvUtil {

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
      return   TYPE.equals(file.getContentType());
    }

    public static List<Employee> csvToList(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<CSVRecord> csvRecords = csvParser.getRecords();
            List<Employee> employeeList = csvRecords.stream()
                    .map(CsvUtil::mapToEmployee)
                    .collect(Collectors.toList());

            return employeeList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private static Employee mapToEmployee(CSVRecord csvRecord) {
        Employee employee = new Employee();
        employee.setName(csvRecord.get("Name"));
        employee.setMail(csvRecord.get("Email"));
        employee.setAge(Integer.valueOf(csvRecord.get("Age")));
        employee.setDivision(csvRecord.get("Division"));
        employee.setUtcOffset(Integer.valueOf(csvRecord.get("TimeZone")));
        return employee;
    }

}
