package ericrlessa.rinhabackend.domain.transacao;

import ericrlessa.rinhabackend.domain.Cliente;
import ericrlessa.rinhabackend.domain.GerenciadorAbstract;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class GerenciadorTransacao extends GerenciadorAbstract {

    public TransacaoResponse debito(Transacao transacao){
        validarClienteExistente(transacao.clienteId);
        validarDescricao(transacao.descricao);

        Cliente cliente = Cliente.findById(transacao.clienteId, LockModeType.PESSIMISTIC_WRITE);
        if((cliente.saldo - transacao.valor) < cliente.limite * -1){
            throw new WebApplicationException("Cliente não possui saldo suficiente!", UNPROCESSABLE_ENTITY);
        }else{
            cliente.saldo -= transacao.valor;
        }

        transacao.persist();

        return new TransacaoResponse(cliente.limite, cliente.saldo);
    }

    public TransacaoResponse credito(Transacao transacao){
        validarClienteExistente(transacao.clienteId);
        validarDescricao(transacao.descricao);

        Cliente cliente = Cliente.findById(transacao.clienteId, LockModeType.PESSIMISTIC_WRITE);
        cliente.saldo += transacao.valor;

        transacao.persist();

        return new TransacaoResponse(cliente.limite, cliente.saldo);
    }

    private void validarDescricao(String descricao){
        if(descricao == null || descricao.isBlank() || descricao.length() > 10){
            throw new WebApplicationException("Unprocessable Entity", UNPROCESSABLE_ENTITY);
        }
    }

}
