
package acme.features.any.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.MilestoneKind;
import acme.entities.campaigns.Milestone;

@Service
public class AnyMilestoneShowService extends AbstractService<Any, Milestone> {

	@Autowired
	private AnyMilestoneRepository	repository;

	private Milestone				milestone;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.milestone = this.repository.findPublishedMilestoneById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;

		id = super.getRequest().getData("id", int.class);
		status = this.repository.findPublishedMilestoneById(id) != null;

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

	}
}
