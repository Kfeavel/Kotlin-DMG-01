package gameboy.cpu.instructions.etc

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestRLCa {
    @Test
    fun `Decode Instruction`() {
        val rlca = Instruction.fromByte(
            opcode = 0b00000111u,
            registers = Registers(),
            bus = MemoryBus(),
        )
        assertTrue(rlca is RLCa)
    }

    @Test
    fun `Test Rotate Left Carry`() {
        val registers = Registers(
            a = 0b10101010u,
        )
        RLCa(registers).execute()
        assertEquals(registers.a, 0b01010101u)
        assertEquals(registers.f.zero, false)
        assertEquals(registers.f.subtract, false)
        assertEquals(registers.f.halfCarry, false)
        assertEquals(registers.f.carry, true)
    }

    @Test
    fun `Test Rotate Left Carry Zero Flag`() {
        val registers = Registers(
            a = 0b00000000u,
        )
        RLCa(registers).execute()
        assertEquals(registers.a, 0b00000000u)
        assertEquals(registers.f.zero, true)
        assertEquals(registers.f.subtract, false)
        assertEquals(registers.f.halfCarry, false)
        assertEquals(registers.f.carry, false)
    }
}
