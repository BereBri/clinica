package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.desafio.ValidadorCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.HorarioDeAnticipacion;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    //investiga mejor esto, que TE CONSTE BERENICE BASTIDAS
    @Autowired
    List<ValidadorDeConsultas> validadores;

    private List<ValidadorCancelamientoDeConsulta> validadorCancelamiento;

    //esete metodo guarda la info q llega de la api externa
    //este servicioi se debe inyectar dentro del controlador
    //se debe verificar que esten activos el paciente y el medico
    public DatosDetallesConsulta agendar (DatosAgendarConsulta datos){
        //se debe comunicar con la base de datos con el pa
        if (!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            //crear una excepcion
            throw new ValidacionDeIntegridad("ESTE ID PARA EL PACIENTE NO FUE ENCONTRADO");
        }

        if (datos.idMedico()!= null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("ESTE ID PARA EL MEDICO NO FUE ENCONTRADO");
        }

        validadores.forEach(v ->v.validar(datos) );

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var medico = seleccionarMedico(datos);

        if (medico == null){
            //crear una excepcion
            throw new ValidacionDeIntegridad("NO EXISTEN MEDICOS DISPONIBLES PARA ESTE HORARIO Y ESPECIALIDAD");
        }

        var consulta = new Consulta(medico, paciente, datos.fecha());

        consultaRepository.save(consulta);

        //se hace el retorno
        return new DatosDetallesConsulta(consulta);
    }

    //el buscar medico es privado
    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if (datos.idMedico()!= null){
            //busca en el medico repository, que este se conecta a la base de datos
            return medicoRepository.getReferenceById(datos.idMedico());        }
        if(datos.especialidad()==null){
            throw new ValidacionDeIntegridad("DEBE SELECCIONARSE UNA ESPECIALIDAD PARA EL MEDICO");
        }
        //realizar un algoritmo para escoger un medico de forma aleatoria, se crea un metodo medico repository
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fecha());
    }

    public void cancelar (DatosCancelamientoConsultas datos) {
        if (!consultaRepository.existsById(datos.idConsulta())){
            throw new ValidacionDeIntegridad("ID DE LA CONSULTA INGRESADA NO EXISTE!");
        }

        validadorCancelamiento.forEach(v -> v.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());

    }

}