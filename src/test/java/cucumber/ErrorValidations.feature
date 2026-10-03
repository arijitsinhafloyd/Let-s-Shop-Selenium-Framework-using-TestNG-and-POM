@tag
	Feature: Error Validdations of fields
	
	Background: 
	Given I landed on Landing Page
	
	@tag2
	
	Scenario: Error Validation of Login functionality
	When Logging in with invalid credentials "mrekm@gmail.com" and "ghjg@35"
	Then received a toast message "Incorrect email or password."
	
	@tag3
	Scenario Outline: Product Error Validtion
	Given Logged in with <email> and <password>
	When I add <product> in cart
	And go to cart Page 
	Then product will not be found if you search for <product2>
	
	Examples:
	|email                    |password                |product             |product2       |
	|contact@arijitsinha.com  |Asdf@1234               |ADIDAS ORIGINAL     |NIKE           |
	|kriti.sanon@gmail.com    |Asdf@2345               |ZARA COAT 3         |LOUIS STATIC   |

	
	
	
	
	
	