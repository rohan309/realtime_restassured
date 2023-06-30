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
      | name     | archived | description                | endPoint  | statusCode |
      | Random   | false    | This is sample description | customers | 200        |
      | B        | false    | This is sample description | customers | 200        |
      | 1234     | false    | This is sample description | customers | 200        |
      | Abc@1234 | false    | This is sample description | customers | 200        |
      | !@#$%    | false    | This is sample description | customers | 200        |

