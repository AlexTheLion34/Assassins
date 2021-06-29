package com.itmo.assassins.controller;

import com.itmo.assassins.model.Report;
import com.itmo.assassins.model.Request;
import com.itmo.assassins.model.User;
import com.itmo.assassins.service.ReportService;
import com.itmo.assassins.service.RequestService;
import com.itmo.assassins.service.impl.UserServiceImpl;
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
public class ReportController extends BaseController {

    private final UserServiceImpl userService;

    private final RequestService requestService;

    private final ReportService reportService;

    @Autowired
    public ReportController(UserServiceImpl userService, RequestService requestService, ReportService reportService) {
        this.userService = userService;
        this.requestService = requestService;
        this.reportService = reportService;
    }

    @RequestMapping(value = "/view-report", method = RequestMethod.GET)
    public ResponseEntity<Resource> viewReport(@RequestParam String id) throws IOException {

        Report report = reportService.getRequestById(Long.parseLong(id));

        Path pathToFile = Paths.get(report.getPath());

        String fileName = "report." + pathToFile.toString().split("\\.")[1];

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        Resource resource = new ByteArrayResource(Files.readAllBytes(pathToFile));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(Paths.get(report.getPath()).toFile().length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @RequestMapping(value = "/add-report", method = RequestMethod.GET)
    public String addReport(ModelMap model) {
        model.addAttribute("report", new Report());
        return "add-report";
    }

    @RequestMapping(value = "/add-report", method = RequestMethod.POST)
    public String addReport(@RequestParam("file") MultipartFile file) {

        try {

            byte[] bytes = file.getBytes();
            Path path = Paths.get("storage/" + file.getOriginalFilename());
            Files.write(path, bytes);

            String userName = getLoggedInUserName();

            User user = userService.findUserByUserName(userName);

            Request request = requestService.getRequestByExecutor(user);

            Report report = new Report();

            report.setPath(path.toString());
            report.setRequest(request);

            request.setReport(report);
            request.setStatus("Confirmation");

            reportService.saveReport(report);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/user";
    }
}
