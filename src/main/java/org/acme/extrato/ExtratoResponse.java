package org.acme.extrato;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.acme.model.Transacao;

import java.util.List;

@RegisterForReflection
public record ExtratoResponse (
Saldo saldo,
List<Transacao> ultimas_transacoes

){}
