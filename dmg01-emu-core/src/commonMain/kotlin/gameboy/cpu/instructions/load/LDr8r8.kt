package gameboy.cpu.instructions.load

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.instructions.InstructionBitmasks
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

class LDr8r8(
    override val registers: Registers,
    internal val target: R8,
    internal val source: R8,
) : Instruction {
    companion object : InstructionBitmasks {
        override val mask: UByte = 0b11000000u
        override val opcode: UByte = 0b01000000u
        override val register: UByte = 0b00111111u
        val registerSource: UByte = 0b00000111u
        val registerTarget: UByte = 0b00111000u
    }

    /**
     * Overflowing add
     */
    private fun load(get: () -> UByte, set: (UByte) -> Unit) {
        set(get())
    }

    override fun execute() {
        val get = when (source) {
            R8.A -> registers::a::get
            R8.B -> registers::b::get
            R8.C -> registers::c::get
            R8.D -> registers::d::get
            R8.E -> registers::e::get
            R8.H -> registers::h::get
            R8.L -> registers::l::get
            else -> throw IllegalStateException("Invalid R8 register for '${this::class.simpleName}'")
        }

        val set = when (target) {
            R8.A -> registers::a::set
            R8.B -> registers::b::set
            R8.C -> registers::c::set
            R8.D -> registers::d::set
            R8.E -> registers::e::set
            R8.H -> registers::h::set
            R8.L -> registers::l::set
            else -> throw IllegalStateException("Invalid R8 register for '${this::class.simpleName}'")
        }

        load(get, set)
        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $target"
}
