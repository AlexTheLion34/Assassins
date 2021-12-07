package com.itmo.assassins.repository.report;

import com.itmo.assassins.model.report.Report;
import com.itmo.assassins.model.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> { }
