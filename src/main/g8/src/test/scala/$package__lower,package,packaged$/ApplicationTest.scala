package $package;format="space,package"$

import com.leobenkel.zparkiotest.TestWithSpark
import org.scalatest.freespec.AnyFreeSpec
import zio.Exit.{Failure, Success}
import zio.{BootstrapRuntime, ZIO}

class ApplicationTest extends AnyFreeSpec with TestWithSpark {
  "Full application" - {
    "Wrong argument" in {
      TestApp.makeRuntime.unsafeRunSync(TestApp.runTest("--bar" :: "foo" :: Nil)) match {
        case Success(value) =>
          println(s"Read: \$value")
          assertResult(1)(value)
        case Failure(cause) => fail(cause.prettyPrint)
      }
    }

    "Help" in {
      TestApp.makeRuntime.unsafeRunSync(TestApp.runTest("--help" :: Nil)) match {
        case Success(value) =>
          println(s"Read: \$value")
          assertResult(0)(value)
        case Failure(cause) => fail(cause.prettyPrint)
      }
    }
  }
}

object TestApp extends Application {
  def runTest(args: List[String]): ZIO[zio.ZEnv, Throwable, Int] = {
    super.run(args)
  }

  lazy final override val makeRuntime: BootstrapRuntime = super.makeRuntime
}
