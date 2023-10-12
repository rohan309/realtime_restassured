Feature: Verify project feature


  @CreateProject
  Scenario Outline: Verify project is created with valid details
    Given I create project with valid credentials
      | customerId  | <customerId>  |
      | name        | <name>        |
      | archived    | <archived>    |
      | description | <description> |
    Then I verify project created with status code
      | statusCode | <statusCode> |
      | errorMsg   | <errorMsg>   |
    Examples:
      | customerId | name   | archived | description       | statusCode | errorMsg |
      | 32         | Random | false    | SampleDescription | 200        |          |
      | 32         | Random | true     | SampleDescription | 200        |          |
      | 32         | Random | false    | Empty             | 200        |          |
#      | 11         | Empty  | false    | SampleDescription | 400        |          |
#      | Empty      | Random | false    | SampleDescription | 400        |          |

#"Lindgren, Lowe and Jacobson"
  @GetAllProjects
  Scenario: Verify customers in get all
    Given I get all projects
      | endPoint   | projects |
    Then I verify all projects with status code 200



