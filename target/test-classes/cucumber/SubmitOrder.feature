@tag
Feature:  Purchasing an item and placing the order

	Background:
	Given I landed on Landing Page
	
	@tag2
	
	Scenario Outline: Positive test of Submitting the order
	Given Logged in with <email> and <password>
	When I add <product> in cart
	And checkout <product> from Cart
	And fill <country> details from Pre Order page and place order
	Then Order will be placed
	
	Examples:
	|email                    |password                |product             |country  |
	|contact@arijitsinha.com  |Asdf@1234               |ADIDAS ORIGINAL     |India    |
	|kriti.sanon@gmail.com    |Asdf@2345               |ZARA COAT 3         |India    |
	
	
	@tag3
	Scenario Outline: Positive test of Verifying the order
	Given Logged in with <email> and <password>
	When I landed on Orders Page
	Then we will find our <product> there
	
	Examples:
	|email                    |password                |product             |
	|contact@arijitsinha.com  |Asdf@1234               |ADIDAS ORIGINAL     |
	|kriti.sanon@gmail.com    |Asdf@2345               |ZARA COAT 3         |
	
	 
	