Feature: Verify customer feature

  @CreateCustomer
  Scenario Outline: Verify create customer feature with valid credentials
    Given I create customer
      | name        | <name>        |
      | archived    | <archived>    |
      | description | <description> |
      | endPoint    | <endPoint>    |
    Then I verify customer created
      | statusCode | <statusCode> |
    Examples:
      | name     | archived | description | endPoint  | statusCode |
      | Random   | false    | Random      | customers | 200        |
      | A1        | false    | Random      | customers | 200        |
      | B1        | false    | Random      | customers | 200        |
#      | R       | true     | Random      | customers | 200        |
      | 12345     | false    | Random      | customers | 200        |
      | Abc@123 | false    | Random      | customers | 200        |
      | !@#$%%    | false    | Random      | customers | 200        |
#    This is sample description


  @CreateDuplicateCustomer
  Scenario Outline: Verify create duplicate customer with same information
    Given I create customer
      | name        | <name>        |
      | archived    | <archived>    |
      | description | <description> |
      | endPoint    | <endPoint>    |
    Then I verify customer created
      | statusCode | <statusCode> |
    And I create duplicate customer
      | endPoint | <endPoint> |
    Then I verify error message for duplicate customer with status code 400
      | errorMsg | <errorMsg> |
    Examples:
      | name   | archived | description | endPoint  | statusCode | errorMsg                                    |
      | Random | false    | Random      | customers | 200        | Customer with specified name already exists |


  @ErrorMsg
  Scenario Outline: Verify  error message for invalid information
    Given I create customer
      | name        | <name>        |
      | archived    | <archived>    |
      | description | <description> |
      | endPoint    | <endPoint>    |

    Then I verify error message for customer with status code 400
      | errorMsg | <errorMsg> |
    Examples:
      | name                                                                                                                                                                                                                                                                                       | archived | description | endPoint  | errorMsg                                  |
      | Empty                                                                                                                                                                                                                                                                                      | false    | Random      | customers | String length must be between 1 and 255   |
      | Empty                                                                                                                                                                                                                                                                                      | true     | Random      | customers | String length must be between 1 and 255   |
      | RandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandom | false    | Random      | customers | String length must be between 1 and 255   |
      | RandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandom | true     | Random      | customers | String length must be between 1 and 255   |
# Continue with error message for description

  @GetAllCustomer
  Scenario Outline: Verify created customer in get all api
    Given I create customer
      | name        | <name>        |
      | archived    | <archived>    |
      | description | <description> |
      | endPoint    | <endPoint>    |
    Then I verify customer created in all customers
      | statusCode | <statusCode> |
    And I get all customer and verify created customer
    Examples:
      | name   | archived | description | endPoint  | statusCode |
      | Random | false    | Random      | customers | 200        |
