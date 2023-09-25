package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosCancelamientoConsultas (
        @NotNull Long idConsulta,
        @NotNull
        MotivoCancelacion motivo) {
}
