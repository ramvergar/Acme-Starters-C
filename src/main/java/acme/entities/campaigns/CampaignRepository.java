
package acme.entities.campaigns;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface CampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.ticker = :ticker")
	Campaign findCampaignByTicker(String ticker);

	@Query("select c from Campaign c where c.draftMode = false order by c.startMoment desc")
	Collection<Campaign> findPublishedCampaigns();

	@Query("select c from Campaign c where c.id = :id and c.draftMode = false")
	Campaign findPublishedCampaignById(int id);

	@Query("select c from Campaign c where c.spokesperson.userAccount.id = :userAccountId order by c.startMoment desc")
	Collection<Campaign> findCampaignsBySpokespersonId(int userAccountId);

	@Query("select c from Campaign c where c.id = :id and c.spokesperson.userAccount.id = :userAccountId")
	Campaign findCampaignByIdAndSpokespersonId(int id, int userAccountId);
	@Query("select sum(m.effort) from Milestone m where m.campaign.id = :id")
	Double calculateTotalEffortByCampaignId(int id);
}
