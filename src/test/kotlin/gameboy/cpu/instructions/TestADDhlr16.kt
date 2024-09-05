package gameboy.cpu.instructions

import gameboy.cpu.instructions.arithmetic.ADDhlr16
import gameboy.cpu.registers.R16
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestADDhlr16 {
    @Test
    fun `Decode Instruction`() {
        val addr16hl = Instruction.fromByte(
            opcode = 0b00011001u,
            registers = Registers(),
            bus = MemoryBus(),
        )
        assertTrue(addr16hl is ADDhlr16)
        assertEquals(R16.DE, addr16hl.target)
    }

    @Test
    fun `Test Add`() {
        val registers = Registers().apply {
            bc = 0xBBCCu
            ADDhlr16(registers = this, target = R16.BC).execute()
        }
        assertEquals(0xBBCCu, registers.hl)
    }

    @Test
    fun `Test Subtract Flag`() {
        val registers = Registers().apply {
            ADDhlr16(registers = this, target = R16.BC).execute()
        }
        assertEquals(false, registers.f.subtract)
    }

    @Test
    fun `Test Half Carry`() {
        val registers = Registers().apply {
            de = 0x0001u
            hl = 0x00FFu
            ADDhlr16(registers = this, target = R16.DE).execute()
        }
        assertEquals(true, registers.f.halfCarry)
    }

    @Test
    fun `Test Overflow`() {
        val registers = Registers().apply {
            de = 0xFFFFu
            hl = 0x0001u
            ADDhlr16(registers = this, target = R16.DE).execute()
        }
        assertEquals(true, registers.f.carry)
    }
}
