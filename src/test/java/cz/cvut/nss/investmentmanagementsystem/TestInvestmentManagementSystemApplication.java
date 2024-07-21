package cz.cvut.nss.investmentmanagementsystem;

import org.springframework.boot.SpringApplication;

public class TestInvestmentManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(InvestmentManagementSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
