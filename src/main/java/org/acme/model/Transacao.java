package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
public class Transacao extends PanacheEntity {

    public Integer clienteId;

    public Integer valor;

    public String tipo;

    public String descricao;

    public LocalDateTime realizada_em;

    Transacao(){}

    public Transacao(Integer clienteId, Integer valor, String tipo, String descricao) {
        this.clienteId = clienteId;
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizada_em = LocalDateTime.now(ZoneOffset.UTC);
    }
}
