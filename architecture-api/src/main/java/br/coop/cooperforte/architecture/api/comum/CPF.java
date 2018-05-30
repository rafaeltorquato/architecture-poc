package br.coop.cooperforte.architecture.api.comum;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Rafael Torquato (Mirante Tecnologia)
 */
@ToString(of = "naoFormatado")
@EqualsAndHashCode(of = "naoFormatado")
public final class CPF implements Serializable {

    private final String naoFormatado;
    private String formatado;

    public CPF(String valor) {
        this.naoFormatado = valor.replaceAll("\\D", "");
        if(this.naoFormatado.length() != 11) throw new IllegalArgumentException("N\u00E3o \u00E9 um CPF valido.");
    }

    public String formatado() {
        if(formatado == null)
            formatado = naoFormatado.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        return formatado;
    }

}
