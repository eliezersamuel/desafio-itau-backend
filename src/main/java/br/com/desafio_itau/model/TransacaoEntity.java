package br.com.desafio_itau.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
@Builder
public class TransacaoEntity {
    private Double valor;
    private OffsetDateTime dataHora;
}
