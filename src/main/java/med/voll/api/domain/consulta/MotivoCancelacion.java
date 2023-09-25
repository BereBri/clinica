package med.voll.api.domain.consulta;

public enum MotivoCancelacion {

    //se debe hacer el motivo de cancelacio, los datos de cancelamiento,
    // luego la validacion del horario de antecedencia (advertencia......no se puede tal objetivo, no es hora adecuada...)
    //y luego como este metodo se puede repetir en varias clases se hace una interface
    //luego vamos al de AgendaDeConsultas de servicio porque allí, se hacen los metodos con los algoritmos que deseamos implementar, en este caso es el cancelamiento de una consulta
    // luego se debe hacer el metodo cancelacion en el metodo Consulta, y se crea un atributo que vamos a llamar
    //se hace un constructor en la clase consulta para este llamado var consulta = new Consulta(medico, paciente, datos.fecha());
    //que esta en la clase AgendaDeConsultaService

    // y ahora implementamos el metodo en el controller y tambien ponemos las asignaciones .....@DeleteMapping

    PACIENTE_DESISTIO,
    MEDICO_CANCELO,
    OTROS;

}