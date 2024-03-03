package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.extrato.ExtratoResponse;
import org.acme.extrato.Saldo;
import org.acme.extrato.TransacaoForm;
import org.acme.model.Cliente;
import org.acme.model.Transacao;

import java.time.LocalDateTime;

@Path("/clientes/{id}")
public class ClienteRest {

    @Path("/transacoes")
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response creditoDebito(@PathParam("id") Integer id, TransacaoForm transacaoForm){

        if (clientIsNotValid(id)) return Response.status(404).build();

        if(transacaoForm.valor() - transacaoForm.valor().intValue() != 0){
            return Response.status(422).build();
        }

        if(transacaoForm.descricao() == null || transacaoForm.descricao().isBlank() || transacaoForm.descricao().length() > 10){
            return Response.status(422).build();
        }

        if(transacaoForm.tipo().equals("d")){
            return debito(id, transacaoForm.parse(id));
        }else if(transacaoForm.tipo().equals("c")){
            return credito(id, transacaoForm.parse(id));
        }else{
            return Response.status(422).build();
        }
    }

    private boolean clientIsNotValid(Integer id) {
        return id < 1 || id > 5;
    }

    private Response debito(Integer id, Transacao transacao){
        Cliente cliente = Cliente.findById(id, LockModeType.PESSIMISTIC_WRITE);
        if((cliente.saldo - transacao.valor) < cliente.limite * -1){
            return Response.status(422).build();
        }else{
            cliente.saldo -= transacao.valor.intValue();
        }

        transacao.persist();

        return Response.ok(new TransacaoResponse(cliente.limite, cliente.saldo)).build();
    }

    private Response credito(Integer id, Transacao transacao){
        Cliente cliente = Cliente.findById(id, LockModeType.PESSIMISTIC_WRITE);
        cliente.saldo += transacao.valor.intValue();

        transacao.persist();

        return Response.ok(new TransacaoResponse(cliente.limite, cliente.saldo)).build();
    }


    @Path("/extrato")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response extrato(@PathParam("id") Integer id, TransacaoResponse transacaoResponse){
        if (clientIsNotValid(id)) return Response.status(404).build();

        Cliente c = Cliente.findById(id, LockModeType.PESSIMISTIC_READ);

        PanacheQuery<Transacao> transacoes = Transacao.find("clienteId", Sort.descending("id"), id);
        //transacoes.page(Page.ofSize(10));
        transacoes.range(0, 10);

        return Response.ok(new ExtratoResponse(new Saldo(c.saldo, LocalDateTime.now(), c.limite), transacoes.list())).build();
    }

}
