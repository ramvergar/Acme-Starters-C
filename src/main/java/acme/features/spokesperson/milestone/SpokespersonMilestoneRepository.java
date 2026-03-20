
package acme.features.spokesperson.milestone;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;

@Repository
public interface SpokespersonMilestoneRepository extends AbstractRepository {

	@Query("select m from Milestone m where m.campaign.id = :campaignId order by m.id asc")
	Collection<Milestone> findMilestonesByCampaignId(int campaignId);

	@Query("select m from Milestone m where m.id = :id")
	Milestone findMilestoneById(int id);

	@Query("select m from Milestone m where m.id = :id and m.campaign.spokesperson.userAccount.id = :userAccountId")
	Milestone findMilestoneByIdAndSpokespersonId(int id, int userAccountId);

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select c from Campaign c where c.id = :id and c.spokesperson.userAccount.id = :userAccountId")
	Campaign findCampaignByIdAndSpokespersonId(int id, int userAccountId);

}
