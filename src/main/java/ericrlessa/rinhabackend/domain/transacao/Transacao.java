package ericrlessa.rinhabackend.domain.transacao;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
public class Transacao extends PanacheEntity {

    public Integer clienteId;

    public Integer valor;

    public String tipo;

    public String descricao;

    public Instant realizada_em;

    Transacao(){}

    public Transacao(Integer clienteId, Integer valor, String tipo, String descricao) {
        this.clienteId = clienteId;
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizada_em = Instant.now().truncatedTo(ChronoUnit.MICROS);
    }
}
