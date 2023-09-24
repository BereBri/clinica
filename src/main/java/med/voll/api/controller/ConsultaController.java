package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetallesConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

//LO PRIMERO QUE SE HAcE
@RestController
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {
    //no se usa la palabra new, es decir instanciar xq spring instancia utomati

    @Autowired
    private AgendaDeConsultaService servicio;

    @PostMapping
    @Transactional
    //primero el controlloler
    //se hace este metodo prrimero, se crea el record DatosAgendarConsulta, esto es un de DTO para agenda de consulta
    public ResponseEntity agendar (@RequestBody @Valid DatosAgendarConsulta datos ){
        //1er se crea DatosDetallesConsulta
        servicio.agendar(datos);
        return ResponseEntity.ok(new DatosDetallesConsulta(null, null, null, null));
    }
}
