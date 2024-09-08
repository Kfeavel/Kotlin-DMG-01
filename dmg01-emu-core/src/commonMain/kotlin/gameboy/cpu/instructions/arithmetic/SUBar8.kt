package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

class SUBar8(
    override val registers: Registers,
    internal val dest: R8,
) : Instruction {
    /**
     * Underflowing subtraction
     */
    private fun sub(registers: Registers, get: () -> UByte) {
        val term = get()
        val value = registers.a
        val newValue = registers.a.minus(term).toUByte()

        registers.a = newValue
        registers.f.apply {
            zero = (newValue.toUInt() == 0u)
            subtract = true
            halfCarry = isSubtractionHalfCarry(value, term)
            carry = (newValue > term)
        }
    }

    override fun execute() {
        when (dest) {
            R8.A -> sub(registers, registers::a::get)
            R8.B -> sub(registers, registers::b::get)
            R8.C -> sub(registers, registers::c::get)
            R8.D -> sub(registers, registers::d::get)
            R8.E -> sub(registers, registers::e::get)
            R8.H -> sub(registers, registers::h::get)
            R8.L -> sub(registers, registers::l::get)
            else -> throw IllegalStateException("Invalid R8 register for '${this::class.simpleName}'")
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $dest"
}
