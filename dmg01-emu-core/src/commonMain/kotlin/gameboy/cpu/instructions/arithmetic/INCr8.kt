package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

class INCr8(
    override val registers: Registers,
    internal val dest: R8,
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
        val register = when (dest) {
            R8.A -> registers::a
            R8.B -> registers::b
            R8.C -> registers::c
            R8.D -> registers::d
            R8.E -> registers::e
            R8.H -> registers::h
            R8.L -> registers::l
            else -> throw IllegalStateException("Invalid R8 register for '${this::class.simpleName}'")
        }

        inc(registers, register::get, register::set)
        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $dest"
}
