package br.com.desafio_itau.dto;

import lombok.Builder;

import java.time.OffsetDateTime;

/**
 * DTO para transitar dados da request de transacoes
 *
 * @param valor Double - Ex.: 123.45
 * @param dataHora OffsetDateTime - Ex.: 2020-08-07T12:34:56.789-03:00
 */
@Builder
public record TransacaoRequestDTO(Double valor, OffsetDateTime dataHora) {
}
