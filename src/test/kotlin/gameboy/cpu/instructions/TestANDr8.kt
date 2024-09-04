package gameboy.cpu.instructions

import gameboy.cpu.instructions.arithmetic.ANDr8
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import kotlin.test.Test
import kotlin.test.assertEquals

class TestANDr8 {
    @Test
    fun `Test AND`() {
        val registers = Registers(
            a = 0xABu,
            b = 0xFFu
        ).apply {
            ANDr8(registers = this, target = R8.B).execute()
        }
        assertEquals(0xABu, registers.a)
    }

    @Test
    fun `Test Zero`() {
        val registers = Registers(
            a = 0xF0u,
            b = 0x0Fu
        ).apply {
            ANDr8(registers = this, target = R8.B).execute()
        }
        assertEquals(true, registers.f.zero)
    }

    @Test
    fun `Test Subtract Flag`() {
        val registers = Registers().apply {
            ANDr8(registers = this, target = R8.B).execute()
        }
        assertEquals(false, registers.f.subtract)
    }

    @Test
    fun `Test Half Carry`() {
        val registers = Registers().apply {
            ANDr8(registers = this, target = R8.B).execute()
        }
        assertEquals(true, registers.f.halfCarry)
    }

    @Test
    fun `Test Carry`() {
        val registers = Registers().apply {
            ANDr8(registers = this, target = R8.C).execute()
        }
        assertEquals(false, registers.f.carry)
    }
}