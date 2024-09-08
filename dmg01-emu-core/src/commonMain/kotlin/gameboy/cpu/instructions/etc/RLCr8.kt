package gameboy.cpu.instructions.etc

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.instructions.InstructionBitmasks
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import kotlin.reflect.KMutableProperty0

open class RLCr8(
    override val registers: Registers,
    internal val dest: R8,
) : Instruction {
    companion object : InstructionBitmasks {
        // This is an 0xCB prefixed instruction.
        override val mask: UByte = 0b11111000u
        override val opcode: UByte = 0b00000000u
    }

    private fun rotateLeftCarry(register: KMutableProperty0<UByte>) {
        // FIXME: There HAS to be a simpler way to write this.
        val value = register.get()
        val msb: UByte = value and 0b10000000u
        val msbIsSet = (msb.compareTo(0b10000000u) == 0)
        val newValue = ((value.toUInt() shl 1) or if (msbIsSet) 0b00000001u else 0u).toUByte()

        register.set(newValue)

        registers.f.apply {
            zero = (newValue.compareTo(0u) == 0)
            subtract = false
            halfCarry = false
            carry = (msb.compareTo(0b10000000u) == 0)
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

        rotateLeftCarry(register)
        registers.pc++
    }

    override fun toString() = "${this::class.simpleName}"
}
