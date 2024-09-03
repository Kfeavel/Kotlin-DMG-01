package gameboy.cpu

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus

class CPU {
    private val registers = Registers()
    private val bus = MemoryBus()

    internal fun step() {
        val opcode = bus.readByte(registers.pc)
        Instruction.fromByte(opcode, registers, bus).execute()
    }
}
