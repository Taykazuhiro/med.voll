package med.voll.api.domain.consulta;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.validacoes.IValidadorDeAgendamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<IValidadorDeAgendamentoConsulta> validadores;

    public DadosDetalhamentoConsulta agendar(@Valid DadosAgendamentoConsulta dados){
        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do Paciente informado não existe!");
        }
        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do médico informado não existe!");
        }
        validadores.forEach(v -> v.validar(dados));

        var medico = escolherMedico(dados);
        if (medico == null){
            throw new ValidacaoException("Não há médicos dessa especialidade disponíveis!");
        }
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
    if (dados.idMedico() != null){
        return medicoRepository.getReferenceById(dados.idMedico());
    }
    if (dados.especialidade() == null){
        throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido!");
    }

    return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(),dados.data());

    }


    public void cancelar(@Valid DadosCancelamentoConsulta dados) {
        if (dados.idConsulta() == null || !consultaRepository.existsById(dados.idConsulta())){
            throw new ValidacaoException("Consulta não estava agendada!");
        }
        if (dados.motivo() == null){
            throw new ValidacaoException("Para cancelar a idConsulta é preciso informar o motivo");
        }

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
