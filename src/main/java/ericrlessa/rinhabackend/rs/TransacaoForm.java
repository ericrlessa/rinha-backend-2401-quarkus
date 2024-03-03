package ericrlessa.rinhabackend.rs;

import io.quarkus.runtime.annotations.RegisterForReflection;
import ericrlessa.rinhabackend.domain.transacao.Transacao;

@RegisterForReflection
public record TransacaoForm(Double valor, String tipo, String descricao) {

    public Transacao parse(Integer clienteId){
       return new Transacao(clienteId, valor.intValue(), tipo, descricao);
    }
}
