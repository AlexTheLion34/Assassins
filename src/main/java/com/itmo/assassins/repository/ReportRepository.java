package com.itmo.assassins.repository;

import com.itmo.assassins.model.Report;
import com.itmo.assassins.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Report getReportByRequest(Request request);
}
