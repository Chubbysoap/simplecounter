package simplecounter

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import org.chipsalliance.cde.config._

class SimpleCounterTest extends AnyFlatSpec with ChiselScalatestTester {
  implicit val p: Parameters = Parameters.empty

  behavior of "SimpleCounter"

  it should "increment when enabled" in {
    test(new SimpleCounter) { c =>
      c.io.en.poke(true.B)
      c.clock.step(1)
      c.io.count.expect(1.U)
      c.clock.step(1)
      c.io.count.expect(2.U)
      c.io.en.poke(false.B)
      c.clock.step(1)
      c.io.count.expect(2.U)
    }
  }
}