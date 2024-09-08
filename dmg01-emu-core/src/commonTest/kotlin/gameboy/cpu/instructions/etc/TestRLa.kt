package gameboy.cpu.instructions.etc

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestRLa {
    @Test
    fun `Decode Instruction`() {
        val rla = Instruction.fromByte(
            opcode = 0b00010111u,
            registers = Registers(),
            bus = MemoryBus(),
        )
        assertTrue(rla is RLa)
        assertEquals(R8.A, rla.dest)
    }

    @Test
    fun `Test Rotate Left`() {
        val registers = Registers(
            a = 0b10101010u,
        )
        RLa(registers).execute()
        assertEquals(registers.a, 0b01010100u)
        assertEquals(false, registers.f.zero)
        assertEquals(false, registers.f.subtract)
        assertEquals(false, registers.f.halfCarry)
        assertEquals(true, registers.f.carry)
    }

    @Test
    fun `Test Rotate Left Zero Flag`() {
        val registers = Registers(
            a = 0b10000000u,
        )
        RLa(registers).execute()
        assertEquals(registers.a, 0b00000000u)
        assertEquals(true, registers.f.zero)
        assertEquals(false, registers.f.subtract)
        assertEquals(false, registers.f.halfCarry)
        assertEquals(true, registers.f.carry)
    }
}
