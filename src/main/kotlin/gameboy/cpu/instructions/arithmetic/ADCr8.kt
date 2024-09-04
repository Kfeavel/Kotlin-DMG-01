package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

class ADCr8(
    override val registers: Registers,
    internal val target: R8,
) : Instruction {
    /**
     * Overflowing add with carry
     */
    private fun addWithCarry(registers: Registers, get: () -> UByte) {
        val term = (get() + (if (registers.f.carry) 1u else 0u)).toUByte()
        val value = registers.a
        val newValue = registers.a
            .plus(term)
            .toUByte()

        registers.a = newValue
        registers.f.apply {
            zero = (newValue.toUInt() == 0u)
            subtract = false
            halfCarry = isAdditionHalfCarry(value, term)
            carry = (newValue < term)
        }
    }

    override fun execute() {
        when (target) {
            R8.A -> addWithCarry(registers, registers::a::get)
            R8.B -> addWithCarry(registers, registers::b::get)
            R8.C -> addWithCarry(registers, registers::c::get)
            R8.D -> addWithCarry(registers, registers::d::get)
            R8.E -> addWithCarry(registers, registers::e::get)
            R8.H -> addWithCarry(registers, registers::h::get)
            R8.L -> addWithCarry(registers, registers::l::get)
            else -> throw IllegalStateException("Invalid R8 register for '${this::class.simpleName}'")
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $target"
}
