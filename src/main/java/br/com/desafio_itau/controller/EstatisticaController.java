package br.com.desafio_itau.controller;

import br.com.desafio_itau.dto.EstatisticaResponseDTO;
import br.com.desafio_itau.service.EstatisticaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {

    private final EstatisticaService estatisticaService;

    public EstatisticaController(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @GetMapping
    public ResponseEntity<EstatisticaResponseDTO> getEstatistica(@RequestHeader(required = false, value = "X-Range-In-Minutes") Integer numeroMinutos) {
        UUID requestID = UUID.randomUUID();
        OffsetDateTime fim = OffsetDateTime.now();
        OffsetDateTime inicio = OffsetDateTime.now().minusMinutes(Objects.isNull(numeroMinutos)? 60 : numeroMinutos);

        log.warn("""
                \nid: {},
                msg: Pegando estatistica,
                Numero-minutos: {},
                """, requestID, numeroMinutos);

        return estatisticaService.getEstatisticasInATime(inicio, fim, requestID);
    }
}
