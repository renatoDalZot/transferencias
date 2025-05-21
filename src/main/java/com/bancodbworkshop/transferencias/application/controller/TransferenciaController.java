package com.bancodbworkshop.transferencias.application.controller;

import com.bancodbworkshop.transferencias.application.dto.TransferenciaRequest;
import com.bancodbworkshop.transferencias.application.dto.TransferenciaResponse;
import com.bancodbworkshop.transferencias.application.service.TransferenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/transferencias")
public class TransferenciaController {

    private final TransferenciaService service;

    public TransferenciaController(TransferenciaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransferenciaResponse> transferir(@RequestBody TransferenciaRequest request) {
        return ResponseEntity.ok(service.transferir(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferenciaResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
