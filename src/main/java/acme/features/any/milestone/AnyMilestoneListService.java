
package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
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
		this.milestones = this.repository.findMilestonesByCampaignId(campaignId);
	}

	@Override
	public void authorise() {
		boolean status;
		int campaignId;
		Campaign campaign;

		campaignId = super.getRequest().getData("masterId", int.class);
		campaign = this.repository.findPublishedCampaignById(campaignId);

		status = campaign != null;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.milestones, "title", "effort", "kind");
	}
}
