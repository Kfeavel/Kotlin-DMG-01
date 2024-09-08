package gameboy.cpu.instructions.etc

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestRLCr8 {
    @Test
    fun `Decode Instruction`() {
        val rlcr8 = Instruction.fromByteWithPrefix(
            opcode = 0b00000010u,
            registers = Registers(),
        )
        assertTrue(rlcr8 is RLCr8)
        assertEquals(R8.D, rlcr8.dest)
    }

    @Test
    fun `Test Rotate Left Carry`() {
        val registers = Registers(
            b = 0b10101010u,
        )
        RLCr8(registers, R8.B).execute()
        assertEquals(registers.b, 0b01010100u)
        assertEquals(false, registers.f.zero)
        assertEquals(false, registers.f.subtract)
        assertEquals(false, registers.f.halfCarry)
        assertEquals(true, registers.f.carry)
    }

    @Test
    fun `Test Rotate Left Carry Zero Flag`() {
        val registers = Registers(
            b = 0b10000000u,
        )
        RLCr8(registers, R8.B).execute()
        assertEquals(registers.b, 0b00000000u)
        assertEquals(true, registers.f.zero)
        assertEquals(false, registers.f.subtract)
        assertEquals(false, registers.f.halfCarry)
        assertEquals(true, registers.f.carry)
    }
}
