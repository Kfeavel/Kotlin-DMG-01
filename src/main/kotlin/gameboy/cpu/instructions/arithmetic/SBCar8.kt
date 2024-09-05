package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

class SBCar8(
    override val registers: Registers,
    internal val target: R8,
) : Instruction {
    /**
     * Underflowing subtraction with carry
     */
    private fun subWithCarry(registers: Registers, get: () -> UByte) {
        val term = (get() + if (registers.f.carry) 1u else 0u).toUByte()
        val value = registers.a
        val newValue = registers.a
            .minus(term)
            .toUByte()

        registers.a = newValue
        registers.f.apply {
            zero = (newValue.toUInt() == 0u)
            subtract = true
            halfCarry = isSubtractionHalfCarry(value, term)
            carry = (newValue > term)
        }
    }

    override fun execute() {
        when (target) {
            R8.A -> subWithCarry(registers, registers::a::get)
            R8.B -> subWithCarry(registers, registers::b::get)
            R8.C -> subWithCarry(registers, registers::c::get)
            R8.D -> subWithCarry(registers, registers::d::get)
            R8.E -> subWithCarry(registers, registers::e::get)
            R8.H -> subWithCarry(registers, registers::h::get)
            R8.L -> subWithCarry(registers, registers::l::get)
            else -> throw IllegalStateException("Invalid R8 register for '${this::class.simpleName}'")
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $target"
}
