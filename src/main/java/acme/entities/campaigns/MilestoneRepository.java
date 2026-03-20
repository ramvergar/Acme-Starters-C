
package acme.entities.campaigns;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface MilestoneRepository extends AbstractRepository {

	@Query("select m from Milestone m where m.campaign.id = :campaignId order by m.id asc")
	Collection<Milestone> findMilestonesByCampaignId(int campaignId);

	@Query("select m from Milestone m where m.id = :id")
	Milestone findMilestoneById(int id);

	@Query("select m from Milestone m where m.id = :id and m.campaign.spokesperson.userAccount.id = :userAccountId")
	Milestone findMilestoneByIdAndSpokespersonId(int id, int userAccountId);

	@Query("select m from Milestone m where m.campaign.id = :campaignId and m.campaign.spokesperson.userAccount.id = :userAccountId")
	Collection<Milestone> findMilestonesByCampaignIdAndSpokespersonId(int campaignId, int userAccountId);

	@Query("select count(m) from Milestone m where m.campaign.id = :campaignId")
	Integer countMilestonesByCampaignId(int campaignId);

	@Query("select sum(m.effort) from Milestone m where m.campaign.id = :campaignId")
	Double sumEffortByCampaignId(int campaignId);
}
