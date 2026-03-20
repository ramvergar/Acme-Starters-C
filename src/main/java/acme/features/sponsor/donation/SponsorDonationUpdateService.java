
package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.DonationKind;
import acme.entities.sponsorships.Donation;
import acme.realms.Sponsor;

@Service
public class SponsorDonationUpdateService extends AbstractService<Sponsor, Donation> {

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
		boolean status;
		if (this.donation != null) {

			boolean createdByPrincipal;
			createdByPrincipal = this.donation.getSponsorship().getSponsor().getId() == super.getRequest().getPrincipal().getActiveRealm().getId();

			status = createdByPrincipal && this.donation.getSponsorship().getDraftMode();
		} else
			status = false;

		super.setAuthorised(status);

	}

	@Override
	public void bind() {
		super.bindObject(this.donation, "name", "notes", "money", "kind");
	}

	@Override
	public void validate() {

		super.validateObject(this.donation);

		boolean validCurrency;

		if (this.donation.getMoney() == null)
			validCurrency = true;
		else if (this.donation.getMoney().getCurrency() == null)
			validCurrency = true;
		else
			validCurrency = this.donation.getMoney().getCurrency().equalsIgnoreCase("EUR");

		super.state(validCurrency, "money", "sponsor.donation.form.error.currency");

	}

	@Override
	public void execute() {
		this.repository.save(this.donation);
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
