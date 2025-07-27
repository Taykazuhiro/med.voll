package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements IValidadorDeAgendamentoConsulta {

    public void validar(DadosAgendamentoConsulta dados){
         var dataConsulta = dados.data();

         var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
         var antesAbertura = dataConsulta.getHour() < 7;
         var depoisFechamento = dataConsulta.getHour() > 18;

         if (domingo || antesAbertura || depoisFechamento){
             throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
         }
    }
}
