package br.com.desafio_itau.dto;

import lombok.Builder;

/**
 * DTO para transitar dados para response de Estatisticas
 *
 * @param count Long - Ex.: 10
 * @param sum Double - Ex.: 150.0
 * @param avg Double - Ex.: 15.0
 * @param min Double - Ex.: 5.0
 * @param max Double - Ex.: 10.0
 */
@Builder
public record EstatisticaResponseDTO(Long count, Double sum, Double avg, Double min, Double max) {
}
