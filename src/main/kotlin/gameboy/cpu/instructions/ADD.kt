package gameboy.cpu.instructions

import gameboy.cpu.Registers

data class ADD(val target: ArithmeticTarget) : Instruction<ArithmeticTarget>() {
    private fun overflowingAdd(registers: Registers, value: UByte): UByte {
        return registers.a.plus(value).toUByte().also { newValue ->
            val didOverflow = (newValue < value)
            // TODO: Handle overflow
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
