
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repository;

	private Sponsorship						sponsorship;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		Sponsor sponsor;

		sponsor = (Sponsor) super.getRequest().getPrincipal().getActiveRealm();

		this.sponsorship = super.newObject(Sponsorship.class);
		this.sponsorship.setDraftMode(true);
		this.sponsorship.setSponsor(sponsor);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Sponsor.class);

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.sponsorship);

		boolean duplicatedTicker;
		boolean validPeriod;

		duplicatedTicker = this.repository.findSponsorshipByTicker(this.sponsorship.getTicker()) != null;
		super.state(!duplicatedTicker, "ticker", "sponsor.sponsorship.form.error.duplicated-ticker");

		validPeriod = this.sponsorship.getStartMoment() != null && this.sponsorship.getEndMoment() != null && this.sponsorship.getStartMoment().before(this.sponsorship.getEndMoment());
		super.state(validPeriod, "endMoment", "sponsor.sponsorship.form.error.invalid-period");

	}

	@Override
	public void execute() {
		this.repository.save(this.sponsorship);
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
