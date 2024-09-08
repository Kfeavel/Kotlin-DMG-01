package gameboy.cpu.instructions.etc

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.instructions.InstructionBitmasks
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import kotlin.reflect.KMutableProperty0

open class RLr8(
    override val registers: Registers,
    internal val dest: R8,
) : Instruction {
    companion object : InstructionBitmasks {
        // This is an 0xCB prefixed instruction.
        override val mask: UByte = 0b11111000u
        override val opcode: UByte = 0b00010000u
    }

    private fun rotateLeft(register: KMutableProperty0<UByte>) {
        val value = register.get()
        val msb = (value and 0b10000000u).toUInt() shr 7
        val newValue = (value.toUInt() shl 1).toUByte()

        register.set(newValue)

        registers.f.apply {
            zero = (newValue.compareTo(0u) == 0)
            subtract = false
            halfCarry = false
            carry = (msb == 1u)
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

        rotateLeft(register)
        registers.pc++
    }

    override fun toString() = "${this::class.simpleName}"
}
