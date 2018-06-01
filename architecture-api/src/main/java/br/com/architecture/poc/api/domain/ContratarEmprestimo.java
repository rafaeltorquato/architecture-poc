package br.com.architecture.poc.api.domain;

import br.com.architecture.poc.api.common.CPF;
import br.com.architecture.poc.api.common.CasoUso;
import br.com.architecture.poc.api.common.Moeda;
import br.com.architecture.poc.api.common.Valor;
import lombok.*;

import java.io.Serializable;

/**
 * @author Rafael Torquato
 */
@RequiredArgsConstructor
public class ContratarEmprestimo implements CasoUso<ContratarEmprestimo.Solicitacao, ContratarEmprestimo.Resposta> {

    private final ContratanteRepository contratanteRepository;
    private final EmprestimoRepository emprestimoRepository;
    private final Integer maioridadePenal;

    @Override
    public Resposta executar(Solicitacao req) throws EmprestimoException {
        Contratante contratante = contratanteRepository.porCPF(new CPF(req.cpf));
        erroSeContratanteInexistente(contratante);
        erroSeContratanteInimputavel(contratante);

        Emprestimo emprestimo = new Emprestimo(
                new Valor(req.valor, Moeda.valueOf(req.moeda)),
                new Parcelas(req.quantidadeParcelas),
                contratante
        );
        emprestimoRepository.armazenar(emprestimo);

        return new Resposta(
                emprestimo.getIdentificador().toString()
        );
    }

    private void erroSeContratanteInexistente(Contratante contratante) throws EmprestimoException {
        if (contratante == null)
            throw new EmprestimoException(MensagemErro.CONTRATANTE_INEXISTENTE);
    }

    private void erroSeContratanteInimputavel(Contratante contratante) throws EmprestimoException {
        if (!contratante.imputavel(maioridadePenal))
            throw new EmprestimoException(MensagemErro.CONTRATANTE_INIMPUTAVEL);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Solicitacao implements Serializable {
        private String cpf;
        private Double valor;
        private String moeda;
        private Integer quantidadeParcelas;
    }

    @Data
    public class Resposta implements Serializable {
        private final String identificador;
    }
}
