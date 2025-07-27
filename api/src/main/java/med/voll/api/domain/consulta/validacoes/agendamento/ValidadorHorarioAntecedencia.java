package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements IValidadorDeAgendamentoConsulta{
    public void validar(DadosAgendamentoConsulta dados){
        var horarioConsulta = dados.data();
        var agora = LocalDateTime.now();

        var tempoAntecedencia = Duration.between(agora, horarioConsulta).toMinutes();
        if (tempoAntecedencia < 30){
            throw new ValidacaoException("Antecedência mínima para agendamentos deve ser de 30 minutos!");
        }

    }
}
