
package acme.entities.sponsorships;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DonationRepository extends AbstractRepository {

	@Query("select count(d) from Donation d where d.sponsorship.id = :id")
	long countBySponsorshipId(@Param("id") int id);

}
