// package com.ecfranalyzer.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.ecfranalyzer.service.ECFRService;

// @RestController
// @RequestMapping("/historical-changes")
// public class HistoricalChangesController {
//     //historical changes over time

//     private final ECFRService ecfrService;

//     @Autowired
//     public HistoricalChangesController(ECFRService ecfrService) {
//         this.ecfrService = ecfrService;
//     }

//     @GetMapping(path = "/historical-changes", produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<List<AgencyResponse>> getAllAgencies() {
//         return ResponseEntity.ok(ecfrService.getAllAgencies());
//     }
// }
