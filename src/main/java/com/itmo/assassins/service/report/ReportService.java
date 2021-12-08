package com.itmo.assassins.service.report;

import com.itmo.assassins.model.report.Report;
import com.itmo.assassins.model.user.Executor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ReportService {
    void saveReport(Report report);
    Report getReportById(Long id);
    void createReport(MultipartFile file, Executor executor) throws IOException;
}
