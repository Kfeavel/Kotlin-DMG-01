package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

class CPar8(
    override val registers: Registers,
    internal val dest: R8,
) : Instruction {
    /**
     * Subtracts from the 8-bit A register, the 8-bit register r, and updates flags based on the result.
     * This instruction is basically identical to SUB r, but does not update the A register.
     */
    private fun cp(registers: Registers, get: () -> UByte) {
        val term = get()
        val value = registers.a
        val newValue = registers.a.minus(term).toUByte()

        registers.f.apply {
            zero = (newValue.toUInt() == 0u)
            subtract = true
            halfCarry = isSubtractionHalfCarry(value, term)
            carry = (newValue > term)
        }
    }

    override fun execute() {
        when (dest) {
            R8.A -> cp(registers, registers::a::get)
            R8.B -> cp(registers, registers::b::get)
            R8.C -> cp(registers, registers::c::get)
            R8.D -> cp(registers, registers::d::get)
            R8.E -> cp(registers, registers::e::get)
            R8.H -> cp(registers, registers::h::get)
            R8.L -> cp(registers, registers::l::get)
            else -> throw IllegalStateException("Invalid R8 register for '${this::class.simpleName}'")
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $dest"
}
