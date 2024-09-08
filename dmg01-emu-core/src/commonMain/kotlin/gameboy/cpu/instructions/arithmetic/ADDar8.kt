package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

class ADDar8(
    override val registers: Registers,
    internal val dest: R8,
) : Instruction {
    /**
     * Overflowing add
     */
    private fun add(registers: Registers, get: () -> UByte) {
        val term = get()
        val value = registers.a
        val newValue = registers.a.plus(term).toUByte()

        registers.a = newValue
        registers.f.apply {
            zero = (newValue.toUInt() == 0u)
            subtract = false
            halfCarry = isAdditionHalfCarry(value, term)
            carry = (newValue < term)
        }
    }

    override fun execute() {
        when (dest) {
            R8.A -> add(registers, registers::a::get)
            R8.B -> add(registers, registers::b::get)
            R8.C -> add(registers, registers::c::get)
            R8.D -> add(registers, registers::d::get)
            R8.E -> add(registers, registers::e::get)
            R8.H -> add(registers, registers::h::get)
            R8.L -> add(registers, registers::l::get)
            else -> throw IllegalStateException("Invalid R8 register for '${this::class.simpleName}'")
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $dest"
}
