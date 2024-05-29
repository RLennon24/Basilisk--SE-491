package basilisk.user.servlet.repository;
import basilisk.user.servlet.model.BasiliskUser;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BasiliskUserRepository extends JpaRepository<BasiliskUser, String> {
	BasiliskUser findByUsername(String username);
}
