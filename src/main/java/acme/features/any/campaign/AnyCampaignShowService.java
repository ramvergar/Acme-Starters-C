
package acme.features.any.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;

@Service
public class AnyCampaignShowService extends AbstractService<Any, Campaign> {

	@Autowired
	private AnyCampaignRepository	repository;

	private Campaign				campaign;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findPublishedCampaignById(id);

	}

	@Override
	public void authorise() {
		boolean status;
		int id;

		id = super.getRequest().getData("id", int.class);
		status = this.repository.findPublishedCampaignById(id) != null;

		super.setAuthorised(status);
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
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

		super.getResponse().addGlobal("campaignId", this.campaign.getId());
		super.getResponse().addGlobal("spokespersonId", this.campaign.getSpokesperson().getId());
	}
}
