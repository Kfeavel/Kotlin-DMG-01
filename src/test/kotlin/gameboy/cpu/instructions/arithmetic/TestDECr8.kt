package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import kotlin.test.Test
import kotlin.test.assertEquals

class TestDECr8 {
    @Test
    fun `Test Decrement`() {
        val registers = Registers(
            b = 0xFFu
        ).apply {
            DECr8(registers = this, target = R8.B).execute()
        }
        assertEquals(0xFEu, registers.b)
    }

    @Test
    fun `Test Zero`() {
        val registers = Registers(
            b = 0x01u
        ).apply {
            DECr8(registers = this, target = R8.B).execute()
        }
        assertEquals(true, registers.f.zero)
    }

    @Test
    fun `Test Subtract Flag`() {
        val registers = Registers(
            b = 0x01u
        ).apply {
            DECr8(registers = this, target = R8.B).execute()
        }
        assertEquals(true, registers.f.subtract)
    }

    @Test
    fun `Test Half Carry`() {
        val registers = Registers(
            b = 0xF0u
        ).apply {
            DECr8(registers = this, target = R8.B).execute()
        }
        assertEquals(0x0EFu, registers.b)
        assertEquals(true, registers.f.halfCarry)
    }
}
