package com.bancodbworkshop.transferencias.controller;

import com.bancodbworkshop.transferencias.dto.NovaTransferenciaRequest;
import com.bancodbworkshop.transferencias.dto.TransferenciaResponse;
import com.bancodbworkshop.transferencias.service.TransferenciaService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<TransferenciaResponse> transferir(@RequestBody NovaTransferenciaRequest request) {
        return ResponseEntity.ok(service.transferir(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferenciaResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
