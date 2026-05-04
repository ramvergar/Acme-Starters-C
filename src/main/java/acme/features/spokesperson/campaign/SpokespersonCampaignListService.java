
package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
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
		for (Campaign campaign : this.campaigns) {
			Tuple tuple;
			boolean isSpanish;
			isSpanish = super.getRequest().getLocale().getLanguage().equals("es");
			tuple = super.unbindObject(campaign, "ticker", "name", "startMoment", "endMoment");
			tuple.put("draftMode", isSpanish ? campaign.getDraftMode() ? "Sí" : "No" : campaign.getDraftMode() ? "Yes" : "No");
			super.getResponse().addData(tuple);
		}
	}
}
