package br.com.desafio_itau.controller;

import br.com.desafio_itau.dto.EstatisticaResponseDTO;
import br.com.desafio_itau.service.EstatisticaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EstatisticaController.class)
class EstatisticaControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    EstatisticaService estatisticaService;

    @MockitoBean
    CacheManager cacheManager;

    private final String rangeHeader = "X-Range-In-Minutes";


    @Test
    @DisplayName("Send X-Range-In-Minutes as 60")
    void getEstatistica() throws Exception {
        Integer X_Range_In_Minutes = 60;

        when(estatisticaService.getEstatisticasInATime(any(), any(), any()))
                .thenReturn(
                        ResponseEntity
                                .status(HttpStatus.OK)
                                .body(EstatisticaResponseDTO
                                    .builder()
                                    .count(10L)
                                    .max(10.0)
                                    .min(10.0)
                                    .sum(100.0)
                                    .avg(10.0)
                                    .build()));

        mvc
                .perform(get("/estatistica")
                        .header(rangeHeader, X_Range_In_Minutes))
                .andExpect(status().is2xxSuccessful());

    }
}