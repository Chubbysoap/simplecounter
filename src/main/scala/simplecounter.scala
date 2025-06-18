package simplecounter

import chisel3._
import freechips.rocketchip.config._
import freechips.rocketchip.subsystem._
import freechips.rocketchip.tilelink._

// Simple 8-bit counter module
class SimpleCounter extends Module {
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

// Configuration for the counter
class SimpleCounterConfig extends Config(
  new WithNBigCores(1) ++ // Use a single Rocket core
    new freechips.rocketchip.config.Config((site, here, up) => {
      case BuildTop => (p: Parameters) => new SimpleCounterTop()(p)
    })
)

// Top-level module integrating SimpleCounter with Rocket Chip
class SimpleCounterTop(implicit p: Parameters) extends RocketSubsystem {
  override lazy val module = new SimpleCounterTopModule(this)
}

class SimpleCounterTopModule(outer: SimpleCounterTop) extends RocketSubsystemModuleImp(outer) {
  val counter = Module(new SimpleCounter)
  // Connect counter inputs and outputs (example: tie enable to a constant or external input)
  counter.io.en := true.B // Example: always enabled; modify as needed
  // Expose count to external IO or connect to Rocket Chip's debug interface
  val io = IO(new Bundle {
    val count = Output(UInt(8.W))
  })
  io.count := counter.io.count
}