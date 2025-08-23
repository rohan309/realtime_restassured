Feature: Verify Task feature

  @CreateTask
  Scenario: Verify create task feature
    Given I create task
      | name          | Random             |
      | description   | Sample description |
      | status        | open               |
      | projectId     | 24                 |
      | typeOfWorkId  | 2                  |
      | estimatedTime | 120                |
      | endPoint      | tasks              |
    Then I verify task created with statusCode 200