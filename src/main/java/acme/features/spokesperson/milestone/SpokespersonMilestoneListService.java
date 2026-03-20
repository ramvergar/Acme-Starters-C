
package acme.features.spokesperson.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneListService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	private SpokespersonMilestoneRepository	repository;

	private Collection<Milestone>			milestones;


	@Override
	public void load() {

		int campaignId;

		campaignId = super.getRequest().getData("masterId", int.class);
		this.milestones = this.repository.findMilestonesByCampaignId(campaignId);
	}

	@Override
	public void authorise() {

		int campaignId;
		int userAccountId;
		boolean status;

		campaignId = super.getRequest().getData("masterId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();

		status = this.repository.findCampaignByIdAndSpokespersonId(campaignId, userAccountId) != null;

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

		for (Milestone milestone : this.milestones)
			super.unbindObject(milestone, "title", "effort", "kind");

		int campaignId;
		campaignId = super.getRequest().getData("masterId", int.class);
		super.getResponse().addGlobal("masterId", campaignId);
	}
}
