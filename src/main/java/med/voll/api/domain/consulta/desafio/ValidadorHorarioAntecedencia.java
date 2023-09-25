package med.voll.api.domain.consulta.desafio;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelamientoConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamientoDeConsulta {

    @Autowired
    private ConsultaRepository repository;
    @Override
    public void validar(DatosCancelamientoConsultas datos) {
        var consulta = repository.getReferenceById(datos.idConsulta());
        var ahora = LocalDateTime.now();
        var diferenciaDe24Horas = Duration.between(ahora, consulta.getFecha()).toHours();


        if(diferenciaDe24Horas < 24){
            throw new ValidationException("Consulta solamente puede ser cancelada con antecedencia mínima de 24h!");
        }
    }
}
