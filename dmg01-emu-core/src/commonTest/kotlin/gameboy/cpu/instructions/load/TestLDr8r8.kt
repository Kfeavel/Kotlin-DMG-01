package gameboy.cpu.instructions.load

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestLDr8r8 {
    @Test
    fun `Decode Instruction`() {
        val ldr8r8 = Instruction.fromByte(
            opcode = 0b01111000u,
            registers = Registers(),
            bus = MemoryBus(),
        )
        assertTrue(ldr8r8 is LDr8r8)
        assertEquals(R8.A, ldr8r8.dest)
        assertEquals(R8.B, ldr8r8.source)
    }

    @Test
    fun `Test u8 Register Load`() {
        val registers = Registers(
            b = 0xBBu,
        )
        LDr8r8(registers, R8.A, R8.B).execute()
        assertEquals(registers.a, 0xBBu)
    }
}
