package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

class ORr8(
    override val registers: Registers,
    internal val target: R8,
) : Instruction {
    /**
     * Overflowing add
     */
    private fun or(registers: Registers, get: () -> UByte) {
        val newValue = registers.a or get()

        registers.a = newValue
        registers.f.apply {
            zero = (newValue.toUInt() == 0u)
            subtract = false
            halfCarry = false
            carry = false
        }
    }

    override fun execute() {
        when (target) {
            R8.A -> or(registers, registers::a::get)
            R8.B -> or(registers, registers::b::get)
            R8.C -> or(registers, registers::c::get)
            R8.D -> or(registers, registers::d::get)
            R8.E -> or(registers, registers::e::get)
            R8.H -> or(registers, registers::h::get)
            R8.L -> or(registers, registers::l::get)
            else -> throw IllegalStateException("Invalid R8 register for '${this::class.simpleName}'")
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $target"
}
