
package acme.features.spokesperson.campaign;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
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

		{
			Date start;
			Date end;
			boolean validInterval;

			start = this.campaign.getStartMoment();
			end = this.campaign.getEndMoment();
			validInterval = start != null && end != null && MomentHelper.isAfter(end, start);
			super.state(validInterval, "startMoment", "acme.validation.campaign.dates.error-publish");
		}

		{
			Date now;
			Date start;
			Date end;
			boolean startInFuture;
			boolean endInFuture;

			now = MomentHelper.getCurrentMoment();
			start = this.campaign.getStartMoment();
			end = this.campaign.getEndMoment();

			startInFuture = start != null && MomentHelper.isAfter(start, now);
			super.state(startInFuture, "startMoment", "acme.validation.campaign.startMoment.future");

			endInFuture = end != null && MomentHelper.isAfter(end, now);
			super.state(endInFuture, "endMoment", "acme.validation.campaign.endMoment.future");
		}
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
