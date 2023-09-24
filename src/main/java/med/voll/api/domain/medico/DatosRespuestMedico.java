package med.voll.api.domain.medico;

import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRespuestMedico( Long id, String nombre, String email, String telefono, String documento,
                                   DatosDireccion direccion) {
}
