package ericrlessa.rinhabackend.domain.extrato;

import io.quarkus.runtime.annotations.RegisterForReflection;
import ericrlessa.rinhabackend.domain.transacao.Transacao;

import java.util.List;

@RegisterForReflection
public record ExtratoResponse (
Saldo saldo,
List<Transacao> ultimas_transacoes

){}
