package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

class DECr8(
    override val registers: Registers,
    internal val target: R8,
) : Instruction {
    private fun dec(
        registers: Registers,
        get: () -> UByte,
        set: (UByte) -> Unit
    ) {
        val value = get()
        val difference = value.dec()
        set(difference)
        registers.f.apply {
            zero = (difference.toUInt() == 0u)
            subtract = true
            halfCarry = isSubtractionHalfCarry(value, 1u)
        }
    }

    override fun execute() {
        when (target) {
            R8.A -> dec(registers, registers::a::get, registers::a::set)
            R8.B -> dec(registers, registers::b::get, registers::b::set)
            R8.C -> dec(registers, registers::c::get, registers::c::set)
            R8.D -> dec(registers, registers::d::get, registers::d::set)
            R8.E -> dec(registers, registers::e::get, registers::e::set)
            R8.H -> dec(registers, registers::h::get, registers::h::set)
            R8.L -> dec(registers, registers::l::get, registers::l::set)
            else -> throw IllegalStateException("Invalid R8 register for '${this::class.simpleName}'")
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $target"
}
