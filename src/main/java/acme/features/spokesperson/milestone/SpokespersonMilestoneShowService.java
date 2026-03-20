
package acme.features.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.MilestoneKind;
import acme.entities.campaigns.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneShowService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	private SpokespersonMilestoneRepository	repository;

	private Milestone						milestone;


	@Override
	public void load() {

		int id;

		id = super.getRequest().getData("id", int.class);
		this.milestone = this.repository.findMilestoneById(id);
	}

	@Override
	public void authorise() {

		int id;
		int userAccountId;
		boolean status;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();

		status = this.repository.findMilestoneByIdAndSpokespersonId(id, userAccountId) != null;

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

		super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
		SelectChoices choices;
		choices = SelectChoices.from(MilestoneKind.class, this.milestone.getKind());

		super.getResponse().addGlobal("kinds", choices);

		super.getResponse().addGlobal("draftMode", this.milestone.getCampaign().getDraftMode());
	}
}
