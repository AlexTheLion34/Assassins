package com.itmo.assassins.controller.report;

import com.itmo.assassins.model.report.Report;
import com.itmo.assassins.model.user.Executor;
import com.itmo.assassins.service.report.ReportService;
import com.itmo.assassins.service.user.UserSecurityService;
import com.itmo.assassins.service.impl.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ReportController {

    private final UserServiceImpl userService;

    private final UserSecurityService securityService;

    private final ReportService reportService;

    @Autowired
    public ReportController(UserServiceImpl userService,
                            UserSecurityService securityService,
                            ReportService reportService) {

        this.userService = userService;
        this.securityService = securityService;
        this.reportService = reportService;
    }

    @RequestMapping(value = "/view-report", method = RequestMethod.GET)
    public ResponseEntity<Resource> viewReport(@RequestParam String id) throws IOException {

        Report report = reportService.getReportById(Long.parseLong(id));

        Path pathToFile = reportService.getReportFile(report);

        String fileName = "report." + pathToFile.toString()
                .split("\\.")[1];

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(Paths.get(report.getPath())
                        .toFile()
                        .length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(Files.readAllBytes(pathToFile)));
    }

    @RequestMapping(value = "/add-report", method = RequestMethod.GET)
    public String addReport(ModelMap model) {
        model.addAttribute("report", new Report());
        return "add-report";
    }

    @RequestMapping(value = "/add-report", method = RequestMethod.POST)
    public String addReport(@RequestParam("file") MultipartFile file) {

        try {

            String userName = securityService.getLoggedInUserName();

            Executor executor = (Executor) userService.findUserByUserName(userName);

            reportService.createReport(file, executor);

        } catch (IOException e) {
            return "redirect:/file-error";
        }

        return "redirect:/profile";
    }
}
