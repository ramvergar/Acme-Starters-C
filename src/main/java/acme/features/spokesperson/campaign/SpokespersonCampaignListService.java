
package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignListService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Collection<Campaign>			campaigns;


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.campaigns = this.repository.findCampaignsBySpokespersonUserAccountId(userAccountId);
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
			super.unbindObject(campaign, "ticker", "name", "startMoment", "endMoment", "draftMode");
	}
}
