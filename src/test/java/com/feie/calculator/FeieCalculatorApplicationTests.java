package com.feie.calculator;

import com.feie.calculator.models.TaxResults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FeieCalculatorApplicationTests {

	double FEIEAmount = 126_500; // Maximum exclusion amount for 2024
	double standardDeduction =  14_600;  // for single people TODO: add input for married etc.
	double[][] federalTaxBrackets = {
//                {0.10, 0},
			{0.10, 11600},
			{0.12, 47150},
			{0.22, 100525},
			{0.24, 191950},
			{0.32, 243725},
			{0.35, 609350},
			{0.37, Double.MAX_VALUE}
	};

	@Autowired
	private FeieCalculatorService feieCalculatorService;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldGetTaxInFirstBracket() {
		// due to standard deduction, no tax is paid.
		TaxResults taxResults = feieCalculatorService.calculateIncomeAfterTax(String.valueOf(5000));

		assertThat(taxResults.getFederalTax()).isEqualTo(500);
		assertThat(taxResults.getIncomeAfterTax()).isEqualTo(5000);
		assertThat(taxResults.getIncomeAfterTaxWithFEIE()).isEqualTo(5000);

	}
	@Test
	void shouldGetTaxInSecondBracket() {
		double income = 30_000 - standardDeduction;
		TaxResults taxResults = feieCalculatorService.calculateIncomeAfterTax(String.valueOf(income));

		double remainder = income - federalTaxBrackets[0][1];
		double expectedFederalTax = federalTaxBrackets[0][0] * federalTaxBrackets[0][1]
				+ federalTaxBrackets[1][0] * remainder;
		assertThat(taxResults.getFederalTax()).isEqualTo(expectedFederalTax);
		assertThat(taxResults.getIncomeAfterTax()).isEqualTo(income - expectedFederalTax);
		assertThat(taxResults.getIncomeAfterTaxWithFEIE()).isEqualTo(income);
	}

	@Test
	void shouldGetTaxInFourthBracket() {
		double income = 150_000 - standardDeduction;
		double remainder = income - federalTaxBrackets[2][1];
		double expectedFederalTax = federalTaxBrackets[0][0] * federalTaxBrackets[0][1]
						+ federalTaxBrackets[1][0] * (income - federalTaxBrackets[0][1])
						+ federalTaxBrackets[2][0] * (income - federalTaxBrackets[1][1])
						+ federalTaxBrackets[3][0] * remainder;

		TaxResults taxResults = feieCalculatorService.calculateIncomeAfterTax(String.valueOf(income));

		assertThat(taxResults.getFederalTax()).isEqualTo(expectedFederalTax);
		assertThat(taxResults.getIncomeAfterTax()).isEqualTo(income - expectedFederalTax);

		// This is the max tax back you can get from the FEIE
		double feieRebate = federalTaxBrackets[0][0] * federalTaxBrackets[0][1]
				+ federalTaxBrackets[1][0] * (federalTaxBrackets[1][1] - federalTaxBrackets[0][1])
				+ federalTaxBrackets[2][0] * (federalTaxBrackets[2][1] - federalTaxBrackets[1][1])
				+ federalTaxBrackets[3][0] * (FEIEAmount - federalTaxBrackets[2][1]);

		double totalTaxPayableInThisBracketWithFEIE =
				federalTaxBrackets[3][0] * (income - FEIEAmount);

		assertThat(taxResults.getIncomeAfterTaxWithFEIE()).isEqualTo(
				income - totalTaxPayableInThisBracketWithFEIE
				);

	}

}
