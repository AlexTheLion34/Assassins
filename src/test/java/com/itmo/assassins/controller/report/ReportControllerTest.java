package com.itmo.assassins.controller.report;

import com.itmo.assassins.model.report.Report;
import com.itmo.assassins.model.request.RequestArsenal;
import com.itmo.assassins.service.impl.user.UserServiceImpl;
import com.itmo.assassins.service.report.ReportService;
import com.itmo.assassins.service.user.UserSecurityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}