package ericrlessa.rinhabackend.domain.transacao;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record TransacaoResponse(Long limite, Long saldo){}
