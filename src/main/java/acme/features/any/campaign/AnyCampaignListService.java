
package acme.features.any.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;

@Service
public class AnyCampaignListService extends AbstractService<Any, Campaign> {

	@Autowired
	private AnyCampaignRepository	repository;

	private Collection<Campaign>	campaigns;


	@Override
	public void load() {
		this.campaigns = this.repository.findPublishedCampaigns();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
	}

	@Override
	public void validate() {
	}

	@Override
	public void execute() {
	}

	@Override
	public void unbind() {
		for (Campaign campaign : this.campaigns)
			super.unbindObject(campaign, "ticker", "name", "startMoment", "endMoment", "moreInfo");
	}
}
