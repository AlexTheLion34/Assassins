package com.itmo.assassins.service.report;

import com.itmo.assassins.model.report.Report;

public interface ReportService {
    void saveReport(Report report);
    Report getRequestById(Long id);
}
