package br.com.architecture.poc.api.domain

import br.com.architecture.poc.api.common.Moeda
import spock.lang.Specification

/**
 * @author Rafael Torquato
 */
class ContratarEmprestimoTest extends Specification {

    def MAIORIDADE_PENAL = 18

    def "deve falhar ao contratar emprestimo com contratante inexistente"() {
        given:
        def contratanteRepository = Mock(ContratanteRepository)
        contratanteRepository.porCPF(_) >> { null }

        def emprestimoRepository = Mock(EmprestimoRepository)
        emprestimoRepository.armazenar(_) >> {}

        def solicitacao = new ContratarEmprestimo.Solicitacao()
        solicitacao.cpf = "99198720163"
        solicitacao.valor = 1000.00D
        solicitacao.moeda = Moeda.USD.toString()
        solicitacao.quantidadeParcelas = 10

        def contratarEmprestimo = new ContratarEmprestimo(
                contratanteRepository,
                emprestimoRepository,
                MAIORIDADE_PENAL
        )

        when:
        contratarEmprestimo.executar(solicitacao)

        then:
        def e = thrown(EmprestimoException.class)
        e.mensagemErro == MensagemErro.CONTRATANTE_INEXISTENTE
        1 * contratanteRepository.porCPF(_)
        0 * emprestimoRepository.armazenar(_)
    }

}
