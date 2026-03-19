
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.sponsorships.Donation;

@Validator
public class DonationValidator extends AbstractValidator<ValidDonation, Donation> {

	// Internal state ---------------------------------------------------------

	// ConstraintValidator interface ------------------------------------------

	@Override
	protected void initialise(final ValidDonation annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Donation donation, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (donation == null)
			result = true;
		else {
			{
				boolean validCurrency;
				validCurrency = donation.getMoney() == null || donation.getMoney().getCurrency() == null || donation.getMoney().getCurrency().equals("EUR");

				super.state(context, validCurrency, "money", "acme.validation.donation.money.currency.message");

			}
			return !super.hasErrors(context);

		}
		return result;
	}

}
