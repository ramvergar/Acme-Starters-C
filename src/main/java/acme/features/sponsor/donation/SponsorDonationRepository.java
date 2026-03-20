
package acme.features.sponsor.donation;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface SponsorDonationRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("select d from Donation d where d.sponsorship.id = :sponsorshipId")
	List<Donation> findDonationsBySponsorshipId(int sponsorshipId);

	@Query("select d from Donation d where d.id = :id")
	Donation findDonationById(int id);
}
