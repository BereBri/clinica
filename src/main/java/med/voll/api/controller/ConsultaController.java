package med.voll.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosCancelamientoConsultas;
import med.voll.api.domain.consulta.DatosDetallesConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

//LO PRIMERO QUE SE HAcE
@RestController
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
    //no se usa la palabra new, es decir instanciar xq spring instancia utomati

    @Autowired
    private AgendaDeConsultaService servicio;

    @PostMapping
    @Transactional
    //primero el controlloler
    //se hace este metodo prrimero, se crea el record DatosAgendarConsulta, esto es un de DTO para agenda de consulta
    @Operation(
            summary ="registra una consulta en la base de datos",
            description = "",
            tags = {"consulta", "post"})
    public ResponseEntity agendar (@RequestBody @Valid DatosAgendarConsulta datos ) throws ValidacionDeIntegridad {
        //1er se crea DatosDetallesConsulta
        //respuesta = response
        var response = servicio.agendar(datos);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Transactional
    @Operation(
            summary = "cancela una consulta de la agenda",
            description = "requiere motivo",
            tags = {"consulta", "delete"})
    public ResponseEntity cancelar (@RequestBody @Valid DatosCancelamientoConsultas datos){
        servicio.cancelar(datos);
        return ResponseEntity.noContent().build();
    }
}
