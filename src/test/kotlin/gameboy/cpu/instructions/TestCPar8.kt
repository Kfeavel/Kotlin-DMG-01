package gameboy.cpu.instructions

import gameboy.cpu.instructions.arithmetic.CPar8
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import kotlin.test.Test
import kotlin.test.assertEquals

class TestCPar8 {
    @Test
    fun `Test Compare`() {
        val registers = Registers(
            a = 0xA0u,
            b = 0x0Bu
        ).apply {
            CPar8(registers = this, target = R8.B).execute()
        }
        assertEquals(0xA0u, registers.a)
    }

    @Test
    fun `Test Zero`() {
        val registers = Registers(
            a = 0x00u,
            b = 0x00u
        ).apply {
            CPar8(registers = this, target = R8.B).execute()
        }
        assertEquals(0x00u, registers.a)
        assertEquals(true, registers.f.zero)
    }

    @Test
    fun `Test Subtract Flag`() {
        val registers = Registers(
            a = 0x00u,
            b = 0x00u
        ).apply {
            CPar8(registers = this, target = R8.B).execute()
        }
        assertEquals(0x00u, registers.a)
        assertEquals(true, registers.f.subtract)
    }

    @Test
    fun `Test Half Carry`() {
        val registers = Registers(
            a = 0x10u,
            b = 0x01u
        ).apply {
            CPar8(registers = this, target = R8.B).execute()
        }
        assertEquals(0x10u, registers.a)
        assertEquals(true, registers.f.halfCarry)
    }

    @Test
    fun `Test Overflow`() {
        val registers = Registers(
            a = 0x00u,
            c = 0x01u
        ).apply {
            CPar8(registers = this, target = R8.C).execute()
        }
        assertEquals(0x00u, registers.a)
        assertEquals(true, registers.f.carry)
    }
}
