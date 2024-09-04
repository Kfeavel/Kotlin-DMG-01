package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

class XORr8(
    override val registers: Registers,
    internal val target: R8,
) : Instruction {
    private fun xor(registers: Registers, get: () -> UByte) {
        val newValue = registers.a xor get()

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
            R8.A -> xor(registers, registers::a::get)
            R8.B -> xor(registers, registers::b::get)
            R8.C -> xor(registers, registers::c::get)
            R8.D -> xor(registers, registers::d::get)
            R8.E -> xor(registers, registers::e::get)
            R8.H -> xor(registers, registers::h::get)
            R8.L -> xor(registers, registers::l::get)
            else -> throw IllegalStateException("Invalid R8 register for '${this::class.simpleName}'")
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $target"
}
