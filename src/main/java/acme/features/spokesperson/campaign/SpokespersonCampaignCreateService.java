
package acme.features.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignCreateService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;


	@Override
	public void load() {
		int userAccountId;
		Spokesperson spokesperson;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		spokesperson = this.repository.findSpokespersonByUserAccountId(userAccountId);

		this.campaign = new Campaign();
		this.campaign.setDraftMode(true);
		this.campaign.setSpokesperson(spokesperson);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "effort");
	}

	@Override
	public void validate() {
		Campaign existingCampaign;

		existingCampaign = this.repository.findCampaignByTicker(this.campaign.getTicker());
		if (existingCampaign != null)
			super.state(false, "ticker", "spokesperson.campaign.form.error.duplicated-ticker");

		if (this.campaign.getStartMoment() != null && this.campaign.getEndMoment() != null)
			super.state(this.campaign.getStartMoment().before(this.campaign.getEndMoment()), "endMoment", "spokesperson.campaign.form.error.invalid-period");

		super.validateObject(this.campaign);
	}

	@Override
	public void execute() {
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "effort");
	}
}
