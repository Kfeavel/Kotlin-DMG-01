package gameboy.cpu.instructions.load

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.instructions.InstructionBitmasks
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus

class LDr8imm8(
    override val registers: Registers,
    internal val bus: MemoryBus,
    internal val target: R8,
) : Instruction {
    companion object : InstructionBitmasks {
        override val mask: UByte = 0b11000111u
        override val opcode: UByte = 0b00000110u
    }

    /**
     * Overflowing add
     */
    private fun load(registers: Registers, bus: MemoryBus, set: (UByte) -> Unit) {
        set(bus[registers.pc++])
    }

    override fun execute() {
        when (target) {
            R8.A -> load(registers, bus, registers::a::set)
            R8.B -> load(registers, bus, registers::b::set)
            R8.C -> load(registers, bus, registers::c::set)
            R8.D -> load(registers, bus, registers::d::set)
            R8.E -> load(registers, bus, registers::e::set)
            R8.H -> load(registers, bus, registers::h::set)
            R8.L -> load(registers, bus, registers::l::set)
            else -> throw IllegalStateException("Invalid R8 register for '${this::class.simpleName}'")
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $target"
}
