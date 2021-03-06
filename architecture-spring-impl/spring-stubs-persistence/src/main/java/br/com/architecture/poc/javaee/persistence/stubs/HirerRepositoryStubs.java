package br.com.architecture.poc.javaee.persistence.stubs;

import br.com.architecture.poc.api.common.SSN;
import br.com.architecture.poc.api.loan.domain.Hirer;
import br.com.architecture.poc.api.loan.domain.HirerRepository;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rafael Torquato
 */
public class HirerRepositoryStubs implements HirerRepository {

    private Map<SSN, Hirer> hirerList = new HashMap<>();

    @PostConstruct
    public void createMocks() {
        try {
            SimpleDateFormat ddMMyyyy = new SimpleDateFormat("ddMMyyyy");
            Hirer hirer1 = new Hirer(
                    new SSN("198720163"),
                    ddMMyyyy.parse("07101982")
            );
            hirerList.put(hirer1.getSsn(), hirer1);

            Hirer hirer2 = new Hirer(
                    new SSN("99476239042"),
                    ddMMyyyy.parse("01012000")
            );
            hirer2.setDateOfDeath(ddMMyyyy.parse("01012018"));
            hirerList.put(hirer2.getSsn(), hirer2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Hirer bySocialSecurityNumber(SSN ssn) {
        return hirerList.get(ssn);
    }
}
