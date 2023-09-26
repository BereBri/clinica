package med.voll.api.controller;


import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetallesConsulta;
import med.voll.api.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;



import java.time.LocalDateTime;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest //nos deja trabajar con repositorios, servicios y controladores
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc avc;

    @Autowired
    private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester;

    @Autowired
    private JacksonTester<DatosDetallesConsulta> detallesConsultaJacksonTester;

    @MockBean
    private AgendaDeConsultaService agendaDeConsultaService;

    @Test
    @DisplayName("DEBERIA RETORNAR ESTADO 400 HTTP CUANDO LOS DATOS SEAN INVALIDOS")
    @WithMockUser
    void agendarEscenario1() throws Exception{
        //ruta tipo post y debe retornar
        var response = avc.perform(post("/consultas")).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    /*
    @Test
    @DisplayName("DEBERIA RETORNAR ESTADO 200 HTTP CUANDO LOS DATOS SON VALIDOS")
    @WithMockUser
    void agendarEscenario2() throws Exception{
        //given
        var fecha = LocalDateTime.now().plusHours(1);
        var especialidad = Especialidad.CARDIOLOGIA;
        var datos = new DatosDetallesConsulta(null,2l,5l,fecha);

        //when
        when(agendaDeConsultaService.agendar(any())).thenReturn(datos);

        //ruta tipo post y debe retornar
        var response = avc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                //esto se debe convertir algo de tipo java a tipo json, para ese vamos a inyectar atributos
                //json taster
                .content(agendarConsultaJacksonTester.write(new DatosAgendarConsulta(2l,5l, fecha,especialidad)).getJson())
        ).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = detallesConsultaJacksonTester.write(datos);
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
    */
    @Test
    @DisplayName("deberia retornar estado http 200 cuando los datos ingresados son validos")
    @WithMockUser
    void agendarEscenario2() throws Exception {
        //given
        var fecha = LocalDateTime.now().plusHours(1);
        var especialidad = Especialidad.CARDIOLOGIA;
        var datos = new DatosDetallesConsulta(null,2l,5l,fecha);

        // when

        when(agendaDeConsultaService.agendar(any())).thenReturn(datos);

        var response = avc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agendarConsultaJacksonTester.write(new DatosAgendarConsulta(2l,5l,fecha, especialidad)).getJson())
        ).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = detallesConsultaJacksonTester.write(datos).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }
}