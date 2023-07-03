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
      | name    | archived | description                | endPoint  | statusCode |
      | Random  | false    | This is sample description | customers | 200        |
      | D       | false    | This is sample description | customers | 200        |
      | A       | true     | This is sample description | customers | 200        |
      | 12345   | false    | This is sample description | customers | 200        |
      | Abc@123 | false    | This is sample description | customers | 200        |
      | !@#$%_  | false    | This is sample description | customers | 200        |


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
      | name   | archived | description                | endPoint  | statusCode | errorMsg                                    |
      | Random | false    | This is sample description | customers | 200        | Customer with specified name already exists |


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
      | name                                                                                                                                                                                                                                                                                       | archived | description                | endPoint  | errorMsg                                |
      | Empty                                                                                                                                                                                                                                                                                      | false    | This is sample description | customers | String length must be between 1 and 255 |
      | Empty                                                                                                                                                                                                                                                                                      | true     | This is sample description | customers | String length must be between 1 and 255 |
      | RandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandom | false    | This is sample description | customers | String length must be between 1 and 255 |
      | RandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandomRandom | true     | This is sample description | customers | String length must be between 1 and 255 |

