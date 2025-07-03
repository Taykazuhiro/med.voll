package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements IValidadorDeAgendamentoConsulta{
    @Autowired
    private MedicoRepository medicoRepository;
    public void validar(DadosAgendamentoConsulta dados){
        if (dados.idMedico() == null){
            return;
        }
        var medico = medicoRepository.findByIdAndAtivoIsTrue(dados.idMedico());
        if (medico == null){
            throw new ValidacaoException("Médico inativo ou inexistente!");
        }
    }
}
