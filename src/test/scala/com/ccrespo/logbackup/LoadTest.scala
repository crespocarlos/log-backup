package test.scala

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTest extends Simulation {

  val scn = scenario("Log endpoint load test")
    .exec(
      http("log api")
        .post("http://localhost:8080/log")
        .header("Content-type", "application/json")
        .body(StringBody("""{ "content": "load test" }"""))
        .check(status.is(200))
    )
    .pause(10 milliseconds)

  setUp(scn
    .inject(constantUsersPerSec(100).during(30 seconds))
    .throttle(
      reachRps(60) in (10 seconds),
      holdFor(1 minute)
    )
  )
}