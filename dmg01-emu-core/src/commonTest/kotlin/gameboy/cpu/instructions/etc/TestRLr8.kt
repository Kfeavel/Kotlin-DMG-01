package gameboy.cpu.instructions.etc

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestRLr8 {
    @Test
    fun `Decode Instruction`() {
        val rlr8 = Instruction.fromByteWithPrefix(
            opcode = 0b00010010u,
            registers = Registers(),
        )
        assertTrue(rlr8 is RLr8)
        assertEquals(R8.D, rlr8.dest)
    }

    @Test
    fun `Test Rotate Left`() {
        val registers = Registers(
            b = 0b10101010u,
        )
        RLr8(registers, R8.B).execute()
        assertEquals(registers.b, 0b01010100u)
        assertEquals(false, registers.f.zero)
        assertEquals(false, registers.f.subtract)
        assertEquals(false, registers.f.halfCarry)
        assertEquals(true, registers.f.carry)
    }

    @Test
    fun `Test Rotate Left Zero Flag`() {
        val registers = Registers(
            b = 0b10000000u,
        )
        RLr8(registers, R8.B).execute()
        assertEquals(registers.b, 0b00000000u)
        assertEquals(true, registers.f.zero)
        assertEquals(false, registers.f.subtract)
        assertEquals(false, registers.f.halfCarry)
        assertEquals(true, registers.f.carry)
    }
}
