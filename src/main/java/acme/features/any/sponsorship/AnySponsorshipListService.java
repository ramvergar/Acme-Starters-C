
package acme.features.any.sponsorship;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;

@Service
public class AnySponsorshipListService extends AbstractService<Any, Sponsorship> {

	@Autowired
	private AnySponsorshipRepository	repository;

	private List<Sponsorship>			sponsorships;


	@Override
	public void load() {

		this.sponsorships = this.repository.findByDraftModeFalse();
	}

	@Override
	public void authorise() {
		boolean status = true;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.sponsorships, "ticker", "name", "startMoment", "endMoment", "moreInfo", "sponsor.identity.fullName");
	}

}
