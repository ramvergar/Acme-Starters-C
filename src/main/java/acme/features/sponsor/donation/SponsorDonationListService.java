
package acme.features.sponsor.donation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.features.sponsor.sponsorship.SponsorSponsorshipRepository;
import acme.realms.Sponsor;

@Service
public class SponsorDonationListService extends AbstractService<Sponsor, Donation> {

	@Autowired
	private SponsorDonationRepository		repository;

	@Autowired
	private SponsorSponsorshipRepository	sponsorshipRepository;

	private List<Donation>					donations;

	private Sponsorship						sponsorship;


	@Override
	public void load() {
		int id;
		id = this.getRequest().getData("sponsorshipId", int.class);

		this.donations = this.repository.findDonationsBySponsorshipId(id);
		this.sponsorship = this.sponsorshipRepository.findSponsorshipById(id);
	}

	@Override
	public void authorise() {
		boolean status = this.sponsorship != null && this.sponsorship.getSponsor().getId() == super.getRequest().getPrincipal().getActiveRealm().getId();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		boolean showCreate;
		boolean createdByPrincipal;
		createdByPrincipal = this.sponsorship.getSponsor().getId() == super.getRequest().getPrincipal().getActiveRealm().getId();
		showCreate = this.sponsorship.getDraftMode() && createdByPrincipal;
		super.unbindObjects(this.donations, "name", "notes", "money", "kind");
		super.unbindGlobal("draftMode", this.sponsorship.getDraftMode());
		super.unbindGlobal("sponsorshipId", this.sponsorship.getId());
		super.unbindGlobal("showCreate", showCreate);

	}

}
