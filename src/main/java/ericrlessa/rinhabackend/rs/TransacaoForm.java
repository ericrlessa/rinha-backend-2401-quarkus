package ericrlessa.rinhabackend.rs;

import ericrlessa.rinhabackend.domain.GerenciadorAbstract;
import io.quarkus.runtime.annotations.RegisterForReflection;
import ericrlessa.rinhabackend.domain.transacao.Transacao;
import jakarta.ws.rs.WebApplicationException;

@RegisterForReflection
public record TransacaoForm(Double valor, String tipo, String descricao) {

    enum Tipo {
        DEBITO,
        CREDITO
    }

    public Tipo getTipo(){
        if(tipo() == null){
            return null;
        }

        if(tipo().equals("d")){
            return Tipo.DEBITO;
        }else if(tipo().equals("c")){
            return Tipo.CREDITO;
        }else{
            return null;
        }
    }

    public Transacao parse(Integer clienteId){
       return new Transacao(clienteId, valor.intValue(), tipo, descricao);
    }
}
