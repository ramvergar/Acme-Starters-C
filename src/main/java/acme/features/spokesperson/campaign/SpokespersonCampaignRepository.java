
package acme.features.spokesperson.campaign;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.realms.Spokesperson;

@Repository
public interface SpokespersonCampaignRepository extends AbstractRepository {

	@Query("select s from Spokesperson s where s.userAccount.id = :id")
	Spokesperson findSpokespersonByUserAccountId(int id);

	@Query("select c from Campaign c where c.ticker = :ticker")
	Campaign findCampaignByTicker(String ticker);

	@Query("select c from Campaign c where c.spokesperson.userAccount.id = :id order by c.startMoment desc")
	Collection<Campaign> findCampaignsBySpokespersonUserAccountId(int id);

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select c from Campaign c where c.id = :id and c.spokesperson.userAccount.id = :userAccountId")
	Campaign findCampaignByIdAndSpokespersonUserAccountId(int id, int userAccountId);

	@Query("select count(m) from Milestone m where m.campaign.id = :campaignId")
	Integer countMilestonesByCampaignId(int campaignId);

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> findMilestonesByCampaignId(int campaignId);

	@Modifying
	@Transactional
	@Query("delete from Milestone m where m.campaign.id = :campaignId")
	void deleteMilestonesByCampaignId(int campaignId);
}
