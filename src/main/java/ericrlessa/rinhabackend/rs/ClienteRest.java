package ericrlessa.rinhabackend.rs;

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

        if(isDecimal(transacaoForm.valor())){
            return Response.status(422).build();
        }

        if(transacaoForm.tipo().equals("d")){
            return Response.ok(gerenciadorTransacao.debito(transacaoForm.parse(clienteId))).build();
        }else if(transacaoForm.tipo().equals("c")){
            return Response.ok(gerenciadorTransacao.credito(transacaoForm.parse(clienteId))).build();
        }else{
            return Response.status(422).build();
        }
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
