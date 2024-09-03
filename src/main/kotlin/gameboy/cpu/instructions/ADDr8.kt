package gameboy.cpu.instructions

import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

data class ADDr8(
    override val registers: Registers,
    val target: R8,
) : Instruction<R8>() {
    private fun overflowingAdd(registers: Registers, value: UByte): UByte {
        return registers.a.plus(value).toUByte().also { newValue ->
            registers.f.apply {
                zero = (newValue.toUInt() == 0u)
                subtract = false
                // Half Carry is set if adding the lower nibbles of the value and register A
                // together result in a value bigger than 0xF. If the result is larger than 0xF
                // than the addition caused a carry from the lower nibble to the upper nibble.
                halfCarry = ((registers.a and 0xFu) + (value and 0xFu) > 0xFu)
                carry = (newValue < value)
            }
        }
    }

    private fun add(registers: Registers, get: () -> UByte) {
        registers.a = overflowingAdd(registers, get())
    }

    override fun execute() {
        when (target) {
            R8.A -> add(registers, registers::a::get)
            R8.B -> add(registers, registers::b::get)
            R8.C -> add(registers, registers::c::get)
            R8.D -> add(registers, registers::d::get)
            R8.E -> add(registers, registers::e::get)
            R8.H -> add(registers, registers::h::get)
            R8.L -> add(registers, registers::l::get)
            else -> throw IllegalStateException("Invalid R8 register for '${this::class.simpleName}'")
        }

        registers.pc.inc()
    }
}
