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
    public Response creditoDebito(@PathParam("id") Integer id, Transacao transacao){

        if(id > 5 || id < 1){
            throw new NotFoundException();
        }

        if(transacao.tipo.equals("d")){
            return debito(id, transacao);
        }else{
            return credito(id, transacao);
        }
    }

    private Response debito(Integer id, Transacao transacao){
        Cliente cliente = Cliente.findById(id, LockModeType.PESSIMISTIC_READ);
        if((cliente.saldo - transacao.valor) < cliente.limite * -1){
            return Response.status(422).build();
        }else{
            cliente.saldo -= transacao.valor;
        }

        transacao.persist();

        return Response.ok(new TransacaoResponse(cliente.limite, cliente.saldo)).build();
    }

    private Response credito(Integer id, Transacao transacao){
        Cliente cliente = Cliente.findById(id, LockModeType.PESSIMISTIC_READ);
        cliente.saldo += transacao.valor;

        transacao.persist();

        return Response.ok(new TransacaoResponse(cliente.limite, cliente.saldo)).build();
    }

    @Path("/extrato")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response extrato(@PathParam("id") Integer id, TransacaoResponse transacaoResponse){
        PanacheQuery<Transacao> transacoes = Transacao.findAll(Sort.descending("id"));
        transacoes.page(Page.ofSize(10));

        Cliente c = Cliente.findById(id);

        return Response.ok(new ExtratoResponse(new Saldo(c.saldo, LocalDateTime.now(), c.limite), transacoes.list())).build();
    }

}
