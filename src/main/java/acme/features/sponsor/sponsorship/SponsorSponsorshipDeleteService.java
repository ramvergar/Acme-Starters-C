
package acme.features.sponsor.sponsorship;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipDeleteService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repository;

	private Sponsorship						sponsorship;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
	}

	@Override
	public void authorise() {
		boolean status = false;

		if (this.sponsorship != null) {
			boolean createdByThePrincipal;
			createdByThePrincipal = this.sponsorship.getSponsor().getId() == super.getRequest().getPrincipal().getActiveRealm().getId();

			status = createdByThePrincipal && this.sponsorship.getDraftMode();
		}

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.sponsorship);
	}

	@Override
	public void execute() {
		List<Donation> donations;
		int id;

		id = super.getRequest().getData("id", int.class);

		donations = this.repository.findDonationsBySponsorshipId(id);
		this.repository.deleteAll(donations);
		this.repository.delete(this.sponsorship);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		tuple.put("monthsActive", this.sponsorship.monthsActive());
		tuple.put("totalMoney", this.sponsorship.totalMoney());
		super.unbindGlobal("sponsorId", this.sponsorship.getSponsor().getId());
	}
}
