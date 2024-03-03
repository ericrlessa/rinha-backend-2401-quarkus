package ericrlessa.rinhabackend.rs;

import ericrlessa.rinhabackend.domain.GerenciadorAbstract;
import ericrlessa.rinhabackend.domain.extrato.GerenciadorExtrato;
import ericrlessa.rinhabackend.domain.transacao.GerenciadorTransacao;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/clientes/{id}")
public class ClienteRest {

    @Inject
    GerenciadorTransacao gerenciadorTransacao;

    @Inject
    GerenciadorExtrato gerenciadorExtrato;

    @Path("/transacoes")
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response creditoDebito(@PathParam("id") Integer clienteId, TransacaoForm transacaoForm){

        if(transacaoForm == null || isDecimal(transacaoForm.valor())){
            return Response.status(GerenciadorAbstract.UNPROCESSABLE_ENTITY).build();
        }

        return switch (transacaoForm.getTipo()) {
            case null -> throw new WebApplicationException("Tipo de operação não existente!", GerenciadorAbstract.UNPROCESSABLE_ENTITY);
            case TransacaoForm.Tipo.DEBITO ->
                    Response.ok(gerenciadorTransacao.debito(transacaoForm.parse(clienteId))).build();
            case TransacaoForm.Tipo.CREDITO ->
                    Response.ok(gerenciadorTransacao.credito(transacaoForm.parse(clienteId))).build();
        };
    }

    private boolean isDecimal(Double valor) {
        return valor - valor.intValue() != 0;
    }

    @Path("/extrato")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response extrato(@PathParam("id") Integer clienteId){
        return Response.ok(gerenciadorExtrato.extrato(clienteId)).build();
    }

}
