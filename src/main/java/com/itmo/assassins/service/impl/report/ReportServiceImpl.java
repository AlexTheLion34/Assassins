package com.itmo.assassins.service.impl.report;

import com.itmo.assassins.model.report.Report;
import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestStatus;
import com.itmo.assassins.model.user.Executor;
import com.itmo.assassins.model.user.User;
import com.itmo.assassins.repository.report.ReportRepository;
import com.itmo.assassins.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report getReportById(Long id) {
        return reportRepository.getById(id);
    }

    @Override
    public void saveReport(Report report) {
        reportRepository.save(report);
    }

    @Override
    public void createReport(MultipartFile file, Executor executor) throws IOException {

        byte[] bytes = file.getBytes();

        Path path = Paths.get("storage/" + file.getOriginalFilename());
        Files.write(path, bytes);

        Request request = executor.getCurrentTask();

        Report report = new Report();

        report.setPath(path.toString());
        report.setRequest(request);

        request.setReport(report);
        request.getRequestInfo().setStatus(RequestStatus.CONFIRMING);

        saveReport(report);
    }
}
