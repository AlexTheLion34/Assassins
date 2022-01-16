package com.itmo.assassins.service.impl.report;

import com.itmo.assassins.model.report.Report;
import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.user.Executor;
import com.itmo.assassins.repository.report.ReportRepository;
import com.itmo.assassins.service.report.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@Import(ReportServiceImpl.class)
class ReportServiceImplTest {

    @MockBean
    private ReportRepository reportRepository;

    @Autowired
    private ReportService reportService;

    @Test
    void testGetReportById() {
        reportService.getReportById(any());

        Mockito.verify(reportRepository, Mockito.times(1))
                .getById(any());
    }

    @Test
    void testSaveReport() {
        reportService.saveReport(any());

        Mockito.verify(reportRepository, Mockito.times(1))
                .save(any());
    }

    @Test
    void testCreateReport() throws IOException {

        Executor executor = new Executor();

        Request request = new Request();
        request.setRequestInfo(new RequestInfo());

        executor.setCurrentTask(request);

        MockMultipartFile file = new MockMultipartFile(
                "test",
                "test",
                "text/plain",
                new byte[]{});

        reportService.createReport(file, executor);

        Mockito.verify(reportRepository, Mockito.times(1))
                .save(any());
    }

    @Test
    void getReportFile() {

        Report report = new Report();
        report.setPath("src/test/resources/test.pdf");

        assertEquals(Paths.get(report.getPath()), reportService.getReportFile(report));
    }
}