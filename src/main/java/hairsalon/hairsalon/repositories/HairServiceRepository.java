package hairsalon.hairsalon.repositories;

import hairsalon.hairsalon.model.HairService;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HairServiceRepository extends JpaRepository<HairService, Long> {

}