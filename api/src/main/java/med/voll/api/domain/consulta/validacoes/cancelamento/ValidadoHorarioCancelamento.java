package med.voll.api.domain.consulta.validacoes.cancelamento;


import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadoHorarioCancelamento implements IValidadorDeCancelamentosConsultas{
    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var agora = LocalDateTime.now();
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        var tempoAntecedencia = Duration.between(agora, consulta.getData()).toHours();
        if (tempoAntecedencia < 24){
            throw new ValidacaoException("Consulta somente pode ser cancelada com 24h de antecedência.");
        }
    }
}
