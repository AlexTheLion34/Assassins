package com.itmo.assassins.service.impl.report;

import com.itmo.assassins.model.report.Report;
import com.itmo.assassins.repository.report.ReportRepository;
import com.itmo.assassins.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public void saveReport(Report report) {
        reportRepository.save(report);
    }

    @Override
    public Report getRequestById(Long id) {
        return reportRepository.getById(id);
    }
}
