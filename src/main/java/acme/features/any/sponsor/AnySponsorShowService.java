
package acme.features.any.sponsor;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Sponsor;

@Service
public class AnySponsorShowService extends AbstractService<Any, Sponsor> {

	@Autowired
	private AnySponsorRepository	repository;

	@Autowired
	private MessageSource			messageSource;

	private Sponsor					sponsor;


	@Override
	public void load() {

		int id;
		id = super.getRequest().getData("id", int.class);
		this.sponsor = this.repository.findSponsorById(id);
	}

	@Override
	public void authorise() {
		boolean status = true;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.sponsor, "address", "im", "userAccount.username");

		Locale locale = LocaleContextHolder.getLocale();
		String goldLabel = this.messageSource.getMessage(this.sponsor.getGold() ? "sponsor.gold.true" : "sponsor.gold.false", null, locale);

		tuple.put("gold", goldLabel);

		super.getResponse().addData(tuple);
	}

}
