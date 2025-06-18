package simplecounter

import chisel3._
import org.chipsalliance.cde.config._
import freechips.rocketchip.tile._
import freechips.rocketchip.util._

// Simple 8-bit counter module
class SimpleCounter(implicit p: Parameters) extends CoreModule()(p) {
  val io = IO(new Bundle {
    val en = Input(Bool())
    val count = Output(UInt(8.W))
  })

  val countReg = RegInit(0.U(8.W))
  when(io.en) {
    countReg := countReg + 1.U
  }
  io.count := countReg
}

// Top-level module integrating SimpleCounter with Rocket Chip
class SimpleCounterTop(implicit p: Parameters) extends LazyModule {
  lazy val module = new SimpleCounterTopModule(this)
}

class SimpleCounterTopModule(outer: SimpleCounterTop)(implicit p: Parameters) extends LazyModuleImp(outer) {
  val counter = Module(new SimpleCounter)

  // Example: Connect enable to a constant or external input
  counter.io.en := true.B // Modify as needed for your use case

  // Expose count to external IO for simulation or integration
  val io = IO(new Bundle {
    val count = Output(UInt(8.W))
  })
  io.count := counter.io.count
}