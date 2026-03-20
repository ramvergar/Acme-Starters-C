
package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Milestone;

@Service
public class AnyMilestoneListService extends AbstractService<Any, Milestone> {

	@Autowired
	private AnyMilestoneRepository	repository;

	private Collection<Milestone>	milestones;


	@Override
	public void load() {
		int campaignId;

		campaignId = super.getRequest().getData("masterId", int.class);
		this.milestones = this.repository.findPublishedMilestonesByCampaignId(campaignId);
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
		for (Milestone milestone : this.milestones)
			super.unbindObject(milestone, "title", "effort", "kind");
	}
}
