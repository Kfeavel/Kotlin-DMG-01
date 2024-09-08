package gameboy.cpu.instructions.load

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R16
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestLDr16imm16 {
    @Test
    fun `Decode Instruction`() {
        val ldr16imm16 = Instruction.fromByte(
            opcode = 0b00010001u,
            registers = Registers(),
            bus = MemoryBus(),
        )
        assertTrue(ldr16imm16 is LDr16imm16)
        assertEquals(R16.DE, ldr16imm16.dest)
    }

    @Test
    fun `Test u16 Immediate Load`() {
        val registers = Registers()
        val bus = MemoryBus().apply {
            set(0x00u, 0xAAu)
            set(0x01u, 0xBBu)
        }
        LDr16imm16(registers, bus, R16.BC).execute()
        assertEquals(registers.bc, 0xBBAAu)
    }
}
