package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.report.GetReportRecruitmentRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.report.GetReportsInfoResponse;
import org.niewidoczniakademicy.rezerwacje.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "report")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetReportsInfoResponse getAvailableReports() {

        return reportService.getReportsInfoResponse();
    }

    @GetMapping(path = "recruitment/{id}",
            produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public InputStreamResource getRecruitmentStatisticsPdf(@PathVariable Long id) {
        return reportService.getRecruitmentPdf(id);
    }

    @PostMapping(path = "recruitment/{id}",
            produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public InputStreamResource postRecruitmentStaticsPd(@PathVariable Long id,
                                                        @RequestBody GetReportRecruitmentRequest request) {
        return reportService.getDetailedRecruitmentPdf(id, request);
    }

}
