package med.voll.api.domain.consulta;

import java.time.LocalDateTime;


public record DatosDetallesConsulta(Long id, Long idPaciente, Long idMedico, LocalDateTime fecha) {
//LO PRIMERO QUE SE HAcE
    public DatosDetallesConsulta(Consulta consulta) {
        this(
                consulta.getId(),
                consulta.getPaciente().getId(),
                consulta.getMedico().getId(),
                consulta.getFecha());
    }
}