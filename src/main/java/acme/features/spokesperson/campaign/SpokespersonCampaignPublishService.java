
package acme.features.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignPublishService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		int userAccountId;
		Campaign campaign;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		campaign = this.repository.findCampaignByIdAndSpokespersonUserAccountId(id, userAccountId);
		status = campaign != null && campaign.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
	}

	@Override
	public void validate() {
		Integer numberOfMilestones;

		numberOfMilestones = this.repository.countMilestonesByCampaignId(this.campaign.getId());
		super.state(numberOfMilestones != null && numberOfMilestones > 0, "*", "spokesperson.campaign.form.error.no-milestones");

		if (this.campaign.getStartMoment() != null && this.campaign.getEndMoment() != null)
			super.state(this.campaign.getStartMoment().before(this.campaign.getEndMoment()), "endMoment", "spokesperson.campaign.form.error.invalid-period");
	}

	@Override
	public void execute() {
		this.campaign.setDraftMode(false);
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "effort");
	}
}
