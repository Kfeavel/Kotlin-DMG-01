package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

class INCr8(
    override val registers: Registers,
    internal val target: R8,
) : Instruction {
    private fun inc(
        registers: Registers,
        get: () -> UByte,
        set: (UByte) -> Unit
    ) {
        val value = get()
        val sum = value.inc()
        set(sum)
        registers.f.apply {
            zero = (sum.toUInt() == 0u)
            subtract = false
            halfCarry = isAdditionHalfCarry(value, 1u)
        }
    }

    override fun execute() {
        when (target) {
            R8.A -> inc(registers, registers::a::get, registers::a::set)
            R8.B -> inc(registers, registers::b::get, registers::b::set)
            R8.C -> inc(registers, registers::c::get, registers::c::set)
            R8.D -> inc(registers, registers::d::get, registers::d::set)
            R8.E -> inc(registers, registers::e::get, registers::e::set)
            R8.H -> inc(registers, registers::h::get, registers::h::set)
            R8.L -> inc(registers, registers::l::get, registers::l::set)
            else -> throw IllegalStateException("Invalid R8 register for '${this::class.simpleName}'")
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $target"
}
