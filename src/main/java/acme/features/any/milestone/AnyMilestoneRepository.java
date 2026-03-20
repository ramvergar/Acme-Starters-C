
package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Milestone;

@Repository
public interface AnyMilestoneRepository extends AbstractRepository {

	@Query("select m from Milestone m where m.campaign.id = :campaignId and m.campaign.draftMode = false order by m.id asc")
	Collection<Milestone> findPublishedMilestonesByCampaignId(int campaignId);

	@Query("select m from Milestone m where m.id = :id and m.campaign.draftMode = false")
	Milestone findPublishedMilestoneById(int id);

}
