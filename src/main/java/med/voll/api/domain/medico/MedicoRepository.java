package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);

    //el método no va a funcionar, ya que para que funcione
    // tener que cumplir este patrón, tener que escribirse en
    // inglés y luego de que se escriba findBy, luego del By tiene
    // que indicarse el atributo que existe en este caso dentro de la
    // clase médico.

    //este es el primer cambio en intellij
    @Query("""
       select m from Medico m
       where m.activo= true 
       and
       m.especialidad=:especialidad
       and
       m.id not in( 
           select c.medico.id from Consulta c
           where
           c.fecha=:fecha
       )
       order by rand()
       limit 1
       """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);
}
