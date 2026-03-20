
package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.DonationKind;
import acme.entities.sponsorships.Donation;
import acme.realms.Sponsor;

@Service
public class SponsorDonationDeleteService extends AbstractService<Sponsor, Donation> {

	@Autowired
	private SponsorDonationRepository	repository;

	private Donation					donation;


	@Override
	public void load() {
		int id;
		id = this.getRequest().getData("id", int.class);
		this.donation = this.repository.findDonationById(id);

	}

	@Override
	public void authorise() {
		boolean status = false;

		if (this.donation != null) {
			boolean createdByPrincipal;
			createdByPrincipal = this.donation.getSponsorship().getSponsor().getId() == super.getRequest().getPrincipal().getActiveRealm().getId();
			status = createdByPrincipal && this.donation.getSponsorship().getDraftMode();
		}

		super.setAuthorised(status);

	}

	@Override
	public void bind() {
		super.bindObject(this.donation, "name", "notes", "money", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.donation);
	}

	@Override
	public void execute() {
		this.repository.delete(this.donation);
	}

	@Override
	public void unbind() {
		SelectChoices kinds;

		kinds = SelectChoices.from(DonationKind.class, this.donation.getKind());

		super.unbindObject(this.donation, "name", "notes", "money", "kind");
		super.unbindGlobal("draftMode", this.donation.getSponsorship().getDraftMode());
		super.unbindGlobal("sponsorshipId", this.donation.getSponsorship().getId());
		super.unbindGlobal("kinds", kinds);

	}
}
