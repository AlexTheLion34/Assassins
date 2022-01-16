package com.itmo.assassins.controller.report;

import com.itmo.assassins.model.report.Report;
import com.itmo.assassins.model.user.Executor;
import com.itmo.assassins.service.impl.user.UserServiceImpl;
import com.itmo.assassins.service.report.ReportService;
import com.itmo.assassins.service.user.UserSecurityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(ReportController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReportControllerTest {

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private UserSecurityService securityService;

    @MockBean
    private ReportService reportService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAddReport() throws Exception {
        mockMvc.perform(get("/add-report"))
                .andExpect(status().is(200))
                .andExpect(view().name("add-report"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/add-report.jsp"))
                .andExpect(model().attribute("report", is(any(Report.class))));
    }

    @Test
    void testPostViewReport() throws Exception {

        Mockito.when(securityService.getLoggedInUserName())
                .thenReturn("username");

        Executor executor = new Executor();

        Mockito.when(userService.findUserByUserName("username"))
                .thenReturn(executor);

        MockMultipartFile file = new MockMultipartFile("file", new byte[1]);

        mockMvc.perform(multipart("/add-report")
                        .file(file))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/profile"))
                .andExpect(redirectedUrl("/profile"));

        Mockito.verify(reportService, Mockito.times(1))
                .createReport(file, executor);
    }

    @Test
    void testPostViewReportWithException() throws Exception {

        Mockito.when(securityService.getLoggedInUserName())
                .thenReturn("username");

        Executor executor = new Executor();

        Mockito.when(userService.findUserByUserName("username"))
                .thenReturn(executor);

        MockMultipartFile file = new MockMultipartFile("file", new byte[1]);

        Mockito.doThrow(IOException.class)
                .when(reportService)
                .createReport(file, executor);

        mockMvc.perform(multipart("/add-report")
                        .file(file))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/file-error"))
                .andExpect(redirectedUrl("/file-error"));
    }

    @Test
    void testGetViewReport() throws Exception {

        Report report = new Report();
        report.setPath("src/test/resources/test.pdf");

        Mockito.when(reportService.getReportById(1L))
                .thenReturn(report);

        Path path = Paths.get(report.getPath());

        Mockito.when(reportService.getReportFile(report))
                .thenReturn(path);

        mockMvc.perform(get("/view-report?id=1"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.parseMediaType("application/octet-stream")))
                .andExpect(content().bytes(Files.readAllBytes(path)));
    }
}