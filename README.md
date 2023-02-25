# bet-quarkus-graphql
[![<PiotrMichalowski96>](https://circleci.com/gh/PiotrMichalowski96/bet-quarkus-graphql.svg?style=svg)](https://circleci.com/gh/PiotrMichalowski96/bet-quarkus-graphql)

This is a sample project uses Quarkus Framework with GraphQL

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```
## Try out example query
Go to the GraphQL UI [page](http://localhost:8080/q/graphql-ui)

Run example query - to find all bets
```shell script
{
  bets {
    id
    homeTeamGoalsPred
    awayTeamGoalsPred
    correct
    betAmount
    match {
      id
      homeTeam
      awayTeam
    }
  }
}
```
