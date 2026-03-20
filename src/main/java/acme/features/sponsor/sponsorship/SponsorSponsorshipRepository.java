
package acme.features.sponsor.sponsorship;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select sum(d.money.amount) from Donation d where d.sponsorship.id = :id and d.money.currency = 'EUR'")
	Double computeTotalMoneyAmount(int id);

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("select s from Sponsorship s where s.sponsor.id = :id")
	List<Sponsorship> findSponsorshipsBySponsorId(int id);

	@Query("select s from Sponsorship s where s.ticker = :ticker")
	Sponsorship findSponsorshipByTicker(String ticker);

	@Query("select count(d) from Donation d where d.sponsorship.id = :id")
	int countDonationsBySponsorshipId(int id);

	@Query("select d from Donation d where d.sponsorship.id = :id")
	List<Donation> findDonationsBySponsorshipId(int id);

	@Query("select s from Sponsor s where s.id = :id")
	Sponsor findSponsorById(int id);
}
