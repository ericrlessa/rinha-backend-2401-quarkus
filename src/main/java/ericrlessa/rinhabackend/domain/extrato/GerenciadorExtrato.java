package ericrlessa.rinhabackend.domain.extrato;

import ericrlessa.rinhabackend.domain.Cliente;
import ericrlessa.rinhabackend.domain.GerenciadorAbstract;
import ericrlessa.rinhabackend.domain.transacao.Transacao;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;

@ApplicationScoped
public class GerenciadorExtrato extends GerenciadorAbstract {

    public ExtratoResponse extrato(Integer id){
        validarClienteExistente(id);

        Cliente c = Cliente.findById(id);

        PanacheQuery<Transacao> transacoes = Transacao.find("clienteId", Sort.descending("realizada_em"), id);
        transacoes.page(Page.ofSize(10));

        return new ExtratoResponse(new Saldo(c.saldo, Instant.now(), c.limite), transacoes.list());
    }
}
