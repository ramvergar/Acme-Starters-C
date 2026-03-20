
package acme.features.any.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;

@Repository
public interface AnyCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.draftMode = false order by c.startMoment desc")
	Collection<Campaign> findPublishedCampaigns();

	@Query("select c from Campaign c where c.id = :id and c.draftMode = false")
	Campaign findPublishedCampaignById(int id);
}
