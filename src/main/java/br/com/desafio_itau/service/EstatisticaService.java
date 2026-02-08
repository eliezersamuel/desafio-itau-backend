package br.com.desafio_itau.service;

import br.com.desafio_itau.dto.EstatisticaResponseDTO;
import br.com.desafio_itau.model.TransacaoEntity;
import br.com.desafio_itau.repository.TransacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class EstatisticaService {
    private final TransacaoRepository transacaoRepository;

    public EstatisticaService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @Cacheable(key = "statsCache")
    public ResponseEntity<EstatisticaResponseDTO> getEstatisticasInATime(OffsetDateTime  inicio, OffsetDateTime fim, UUID requestID) {
        log.info("""
                RequestID: {},
                Inicio: {},
                Fim: {}
                """, requestID, inicio, fim);

        List<TransacaoEntity> transacaoEntityList = transacaoRepository.findTransacoesByDataHora(inicio, fim);
        if(transacaoEntityList.isEmpty()){
            log.info("""
                    RequestID: {},
                    msg: Transacao Empty
                    """, requestID);


            return ResponseEntity.status(HttpStatus.OK).body(new EstatisticaResponseDTO(0L, 0.0, 0.0, 0.0, 0.0));
        }

        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();

        transacaoEntityList
                .stream()
                .forEach(transacao -> stats.accept(transacao.getValor()));

        return ResponseEntity.status(HttpStatus.OK).body(new EstatisticaResponseDTO(stats.getCount(), stats.getSum(), stats.getAverage(), stats.getMin(), stats.getMax()));
    }
}
