
package acme.features.any.sponsorship;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface AnySponsorshipRepository extends AbstractRepository {

	List<Sponsorship> findByDraftModeFalse();

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findSponsorshipById(int id);
}
