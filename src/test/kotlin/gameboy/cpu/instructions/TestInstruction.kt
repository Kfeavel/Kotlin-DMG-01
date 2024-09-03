package gameboy.cpu.instructions

import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestInstruction {
    private lateinit var registers: Registers

    @BeforeTest
    fun `Reset Registers`() {
        registers = Registers()
    }

    @Test
    fun `Opcode Decoding`() {
        val nop = Instruction.fromByte(
            opcode = 0x00u,
            prefixed = false,
            registers = registers,
        )
        assertTrue(nop is NOP)

        val addr8a = Instruction.fromByte(
            opcode = (0x04u or (R8.A.register.toUInt() shl 4)).toUByte(),
            prefixed = false,
            registers = registers,
        )
        assertTrue(addr8a is ADDr8)
        assertEquals(addr8a.target, R8.A)
    }
}