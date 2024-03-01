package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
public class Transacao extends PanacheEntity {
    public Integer valor;

    public String tipo;

    public String descricao;

    public LocalDateTime realizada_em = LocalDateTime.now(ZoneOffset.UTC);
}
