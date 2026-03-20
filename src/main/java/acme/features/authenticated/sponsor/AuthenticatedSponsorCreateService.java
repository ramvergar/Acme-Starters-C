
package acme.features.authenticated.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Sponsor;

@Service
public class AuthenticatedSponsorCreateService extends AbstractService<Authenticated, Sponsor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedSponsorRepository	repository;

	private Sponsor							sponsor;

	// AbstractService inteface -----------------------------------------------


	@Override
	public void load() {
		int userAccountId;
		UserAccount userAccount;

		userAccountId = this.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);

		this.sponsor = super.newObject(Sponsor.class);
		this.sponsor.setUserAccount(userAccount);
	}

	@Override
	public void authorise() {
		boolean status;

		status = !this.getRequest().getPrincipal().hasRealmOfType(Sponsor.class);

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsor, "address", "im", "gold");
	}

	@Override
	public void validate() {
		super.validateObject(this.sponsor);
	}

	@Override
	public void execute() {
		this.repository.save(this.sponsor);
	}

	@Override
	public void unbind() {
		SelectChoices goldChoices;

		goldChoices = this.getGoldChoices(this.sponsor.getGold());
		super.unbindObject(this.sponsor, "address", "im", "gold");
		super.getResponse().addGlobal("goldChoices", goldChoices);
	}

	private SelectChoices getGoldChoices(final Boolean selected) {
		SelectChoices result;

		result = new SelectChoices();
		result.add("false", "authenticated.sponsor.form.value.false", Boolean.FALSE.equals(selected));
		result.add("true", "authenticated.sponsor.form.value.true", Boolean.TRUE.equals(selected));

		return result;
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
