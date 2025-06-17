package simplecounter

import chisel3._
import chipyard._
import chipyard.config._

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
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++ // Use a single Rocket core
    new chipyard.config.AbstractConfig
) {
  override def generator = (p, s) => new SimpleCounter()(p, s)
}

// Generator to elaborate the design
class SimpleCounterGenerator extends chipyard.Generator {
  def elaborate(p: freechips.rocketchip.config.Parameters): chisel3.Module = {
    new SimpleCounter()(p)
  }
}