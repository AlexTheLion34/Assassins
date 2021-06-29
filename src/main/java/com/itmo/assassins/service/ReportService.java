package com.itmo.assassins.service;

import com.itmo.assassins.model.Report;
import com.itmo.assassins.model.Request;

public interface ReportService {

    void saveReport(Report report);

    Report getRequestById(Long id);
}
