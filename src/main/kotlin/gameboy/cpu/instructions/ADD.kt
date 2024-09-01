package gameboy.cpu.instructions

import gameboy.cpu.Registers

data class ADD(val target: ArithmeticTarget) : Instruction<ArithmeticTarget>() {
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

    override fun execute(registers: Registers) {
        when (target) {
            ArithmeticTarget.A -> add(registers, registers::a::get)
            ArithmeticTarget.B -> add(registers, registers::b::get)
            ArithmeticTarget.C -> add(registers, registers::c::get)
            ArithmeticTarget.D -> add(registers, registers::d::get)
            ArithmeticTarget.E -> add(registers, registers::e::get)
            ArithmeticTarget.H -> add(registers, registers::h::get)
            ArithmeticTarget.L -> add(registers, registers::l::get)
        }
    }
}
