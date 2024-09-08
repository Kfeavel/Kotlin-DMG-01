package gameboy.cpu.instructions.load

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestLDr8imm8 {
    @Test
    fun `Decode Instruction`() {
        val ldr8imm8 = Instruction.fromByte(
            opcode = 0b00010110u,
            registers = Registers(),
            bus = MemoryBus(),
        )
        assertTrue(ldr8imm8 is LDr8imm8)
        assertEquals(R8.D, ldr8imm8.target)
    }

    @Test
    fun `Test u8 Immediate Load`() {
        val registers = Registers(
            pc = 0x07u,
        )
        val bus = MemoryBus().apply {
            set(0x07u, 0xAAu)
        }
        LDr8imm8(registers, bus, R8.B).execute()
        assertEquals(registers.b, 0xAAu)
    }
}
