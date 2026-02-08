package br.com.desafio_itau.service;

import br.com.desafio_itau.dto.TransacaoRequestDTO;
import br.com.desafio_itau.model.TransacaoEntity;
import br.com.desafio_itau.repository.TransacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.lang.String.format;

@Slf4j
@Service
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @ConcurrencyLimit(10)
    @CacheEvict(key = "statsCache")
    public ResponseEntity<Void> createTransacao(TransacaoRequestDTO transacaoRequestDTO, UUID requestID) {
        log.info("""
                RequestID: {},
                Valor: {},
                Data e Hora: {}
                """, requestID, transacaoRequestDTO.valor(), transacaoRequestDTO.dataHora());

        TransacaoEntity transacaoEntity = new TransacaoEntity(transacaoRequestDTO.valor(), transacaoRequestDTO.dataHora());
        transacaoRepository.addTransacao(transacaoEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @CacheEvict(allEntries = true)
    public ResponseEntity<Void> deleteAllTransacoes(UUID requestID){
        log.info("""
                RequestID: {}
                """, requestID);

        transacaoRepository.deleteAllTransacoes();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
