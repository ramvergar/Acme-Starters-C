
package acme.entities.sponsorships;

import org.springframework.data.jpa.repository.Query;

public interface SponsorshipRepository {

	@Query("select sum(d.money.amount) from Donation d where d.sponsorship.id = :id and d.money.currency = 'EUR'")
	Double computeTotalMoneyAmount(int id);

	@Query("select s from sponsorship where s.ticker = :ticker")
	Sponsorship findSponsorshipByTicker(String ticker);

}
