package org.acme.extrato;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.acme.model.Transacao;

@RegisterForReflection
public record TransacaoForm(Double valor, String tipo, String descricao) {

    public Transacao parse(Integer clienteId){
       return new Transacao(clienteId, valor.intValue(), tipo, descricao);
    }
}
