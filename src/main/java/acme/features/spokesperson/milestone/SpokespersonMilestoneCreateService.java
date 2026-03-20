
package acme.features.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.MilestoneKind;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneCreateService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	private SpokespersonMilestoneRepository	repository;

	private Milestone						milestone;


	@Override
	public void load() {

		int campaignId;
		Campaign campaign;

		campaignId = super.getRequest().getData("masterId", int.class);
		campaign = this.repository.findCampaignById(campaignId);

		this.milestone = new Milestone();
		this.milestone.setCampaign(campaign);
	}

	@Override
	public void authorise() {

		int campaignId;
		int userAccountId;
		Campaign campaign;
		boolean status;

		campaignId = super.getRequest().getData("masterId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();

		campaign = this.repository.findCampaignByIdAndSpokespersonId(campaignId, userAccountId);

		status = campaign != null && campaign.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {

		super.bindObject(this.milestone, "title", "achievements", "effort", "kind");
	}

	@Override
	public void validate() {

		super.validateObject(this.milestone);
	}

	@Override
	public void execute() {

		this.repository.save(this.milestone);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
		choices = SelectChoices.from(MilestoneKind.class, this.milestone.getKind());

		super.getResponse().addGlobal("kinds", choices);
		super.getResponse().addGlobal("draftMode", this.milestone.getCampaign().getDraftMode());
		int campaignId;
		campaignId = super.getRequest().getData("masterId", int.class);
		super.getResponse().addGlobal("masterId", campaignId);
	}
}
