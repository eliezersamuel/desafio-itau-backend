package br.com.desafio_itau.controller;

import br.com.desafio_itau.dto.TransacaoRequestDTO;
import br.com.desafio_itau.service.TransacaoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> criarTransacao(@Valid @RequestBody TransacaoRequestDTO transacao) {
        UUID requestID = UUID.randomUUID();
        if(transacao.dataHora().isAfter(OffsetDateTime.now()) || transacao.valor() <= 0) {
            log.warn("""
                    id: {},
                    msg: Transacao com valores errados,
                    valor: {},
                    DataHora: {}
                    """, requestID, transacao.valor(), transacao.dataHora());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        return transacaoService.createTransacao(transacao, requestID);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletarTransacao() {
        UUID requestID = UUID.randomUUID();
        log.warn("""
                id: {},
                msg: Deletando Transacoes,
                """, requestID);

        return transacaoService.deleteAllTransacoes(requestID);
    }

}
