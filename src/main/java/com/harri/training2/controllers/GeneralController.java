package com.harri.training2.controllers;

import com.harri.training2.models.ExportType;
import com.harri.training2.services.GeneralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@CrossOrigin
@RequestMapping("/fifa")
@RequiredArgsConstructor
public class GeneralController {

    private final GeneralService service;


    @GetMapping("/one-chunk-csv")
    public ResponseEntity<?> exportCsvChunk(){
        Object result = service.exportCsv(ExportType.CHUNK);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/stream-csv")
    public ResponseEntity<SseEmitter> exportCsvStream(){
        SseEmitter result = (SseEmitter) service.exportCsv(ExportType.STREAM);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/s3-csv")
    public ResponseEntity<?> exportCsvS3(){

        Object result = service.exportCsv(ExportType.S3);

        return ResponseEntity.ok(result);
    }


}
