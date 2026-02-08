package br.com.desafio_itau.repository;

import br.com.desafio_itau.model.TransacaoEntity;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class TransacaoRepository {
    private final ConcurrentLinkedQueue<TransacaoEntity> listaTransacao = new ConcurrentLinkedQueue<>();

    public void addTransacao(TransacaoEntity transacao) {
        listaTransacao.add(transacao);
    }

    public void deleteAllTransacoes(){
        listaTransacao.clear();
    }

    public List<TransacaoEntity> findTransacoesByDataHora(OffsetDateTime inicio, OffsetDateTime fim) {
        return listaTransacao.stream().filter(transacao -> transacao.getDataHora().isAfter(inicio) && transacao.getDataHora().isBefore(fim)).toList();
    }
}
