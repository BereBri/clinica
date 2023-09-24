package med.voll.api.domain.consulta;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//LO PRIMERO QUE SE HAcE

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

}