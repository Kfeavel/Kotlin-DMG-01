package gameboy.cpu.instructions

import gameboy.cpu.registers.R16
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus

class LDr16imm16(
    override val registers: Registers,
    internal val bus: MemoryBus,
    internal val target: R16,
) : Instruction {
    companion object : InstructionBitmasks {
        override val mask: UByte = 0b11001111u
        override val opcode: UByte = 0b00000001u
    }

    /**
     * Overflowing add
     */
    private fun load(registers: Registers, bus: MemoryBus, set: (UShort) -> Unit) {
        val lsb = bus[registers.pc++].toUInt()
        val msb = bus[registers.pc++].toUInt()
        set(((msb shl 8) or lsb).toUShort())
    }

    override fun execute() {
        when (target) {
            R16.BC -> load(registers, bus, registers::bc::set)
            R16.DE -> load(registers, bus, registers::de::set)
            R16.HL -> load(registers, bus, registers::hl::set)
            R16.SP -> load(registers, bus, registers::sp::set)
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $target"
}
